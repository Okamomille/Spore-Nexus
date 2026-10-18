package net.okamiz.common.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.architectury.platform.Platform;
import net.minecraft.resources.Identifier;
import net.okamiz.SporeNexus;
import net.okamiz.common.records.AltarDefinition;
import net.okamiz.common.records.CustomMushroomDefinition;
import net.okamiz.common.records.MutationDefinition;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public final class CustomMushroomLoader {

    private static final Pattern VALID_ID = Pattern.compile("[a-z0-9_]+");
    private static final Pattern COLOR = Pattern.compile("#[0-9A-Fa-f]{6}");
    private static List<CustomMushroomDefinition> cache;

    private CustomMushroomLoader() {}

    public static List<CustomMushroomDefinition> load() {
        if (cache == null) {
            cache = read();
        }
        return cache;
    }

    private static List<CustomMushroomDefinition> read() {
        Path file = Platform.getConfigFolder().resolve("sporenexus").resolve("custom_mushrooms.json");
        try {
            if (Files.notExists(file)) {
                Files.createDirectories(file.getParent());
                Files.writeString(file, "{\n  \"mushrooms\": []\n}\n");
                return List.of();
            }
        } catch (IOException e) {
            SporeNexus.LOGGER.error("Could not create {}", file, e);
            return List.of();
        }



        List<CustomMushroomDefinition> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        try (Reader reader = Files.newBufferedReader(file)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonObject().getAsJsonArray("mushrooms");
            int entry = 0;
            for (JsonElement element : array) {
                entry++;
                CustomMushroomDefinition def;
                try {
                    def = parse(element.getAsJsonObject());
                } catch (RuntimeException e) {
                    SporeNexus.LOGGER.error("Custom mushroom entry #{} is malformed (wrong type or structure), ignored: {}",
                            entry, e.getMessage());
                    continue;
                }
                if (def == null) continue;
                if (!seen.add(def.id())) {
                    SporeNexus.LOGGER.error("Custom mushroom '{}' is defined twice, ignoring the second one", def.id());
                    continue;
                }
                result.add(def);
            }
        } catch (Exception e) {
            SporeNexus.LOGGER.error("Invalid {}: custom mushrooms were NOT loaded", file, e);
            return List.of();
        }
        SporeNexus.LOGGER.info("Loaded {} custom mushroom(s)", result.size());
        return result;
    }

    private static @Nullable CustomMushroomDefinition parse(JsonObject json) {
        String id = json.has("id") ? json.get("id").getAsString() : "";
        if (!VALID_ID.matcher(id).matches()) {
            SporeNexus.LOGGER.error("Custom mushroom has an invalid id '{}' (use a-z, 0-9 and _ only)", id);
            return null;
        }
        Identifier result = identifier(json, "fragment_result");
        if (result == null) {
            SporeNexus.LOGGER.error("Custom mushroom '{}': missing or invalid 'fragment_result'", id);
            return null;
        }
        int growth = json.has("growth_ticks") ? json.get("growth_ticks").getAsInt() : 1800;
        int count = json.has("fragment_count") ? json.get("fragment_count").getAsInt() : 1;
        if (growth < 20 || count < 1 || count > 64) {
            SporeNexus.LOGGER.error("Custom mushroom '{}': growth_ticks must be >= 20 and fragment_count between 1 and 64", id);
            return null;
        }

        String color = json.has("color") ? json.get("color").getAsString() : "#FFFFFF";
        if (!COLOR.matcher(color).matches()) {
            SporeNexus.LOGGER.error("Custom mushroom '{}': invalid color '{}' (expected #RRGGBB), using white", id, color);
            color = "#FFFFFF";
        }

        String name = json.has("name") && !json.get("name").isJsonNull() ? json.get("name").getAsString().trim() : null;
        if (name != null && (name.isEmpty() || name.contains("\"") || name.contains("\\") || name.chars().anyMatch(Character::isISOControl))) {
            SporeNexus.LOGGER.error("Custom mushroom '{}': 'name' must not be empty and must not contain quotes, backslashes or line breaks, using the default name", id);
            name = null;
        }

        AltarDefinition altar = null;
        if (json.has("altar") && json.get("altar").isJsonObject()) {
            JsonObject a = json.getAsJsonObject("altar");
            Identifier mushroom = identifier(a, "mushroom_input");
            List<Identifier> resources = identifierList(a, "resource_input");
            List<Identifier> secondary = identifierList(a, "secondary_input");
            if (mushroom == null || resources == null || secondary == null) {
                SporeNexus.LOGGER.error("Custom mushroom '{}': invalid 'altar' section (needs mushroom_input, "
                        + "resource_input and secondary_input; each is either one item or a list of 1 or 4 items), altar recipe ignored", id);
            } else {
                altar = new AltarDefinition(mushroom, resources, secondary);
            }
        }

        List<MutationDefinition> mutations = new ArrayList<>();
        if (json.has("mutations") && json.get("mutations").isJsonArray()) {
            int index = 0;
            for (JsonElement element : json.getAsJsonArray("mutations")) {
                index++;
                if (!element.isJsonObject()) {
                    continue;
                }
                JsonObject m = element.getAsJsonObject();
                Identifier a = mushroomReference(m, "parent_a");
                Identifier b = mushroomReference(m, "parent_b");
                double chance = m.has("chance") ? m.get("chance").getAsDouble() : 0.05;
                if (a == null || b == null || chance <= 0 || chance > 1) {
                    SporeNexus.LOGGER.error("Custom mushroom '{}': mutation #{} is invalid (needs parent_a, parent_b "
                            + "and a chance in ]0, 1]), ignored", id, index);
                    continue;
                }
                mutations.add(new MutationDefinition(a, b, chance));
            }
        }

        return new CustomMushroomDefinition(id, name, color, growth, result, count,
                identifier(json, "essence"), identifier(json, "extra_drop"), altar, mutations);
    }

    private static @Nullable Identifier mushroomReference(JsonObject json, String key) {
        if (!json.has(key) || json.get(key).isJsonNull()) {
            return null;
        }
        String value = json.get(key).getAsString().trim();
        if (value.isEmpty()) {
            return null;
        }
        if (value.contains(":")) {
            return Identifier.tryParse(value);
        }
        if (!VALID_ID.matcher(value).matches()) {
            return null;
        }
        return Identifier.tryParse(SporeNexus.MOD_ID + ":" + value + "_resource_mushroom");
    }

    private static @Nullable List<Identifier> identifierList(JsonObject json, String key) {
        if (!json.has(key)) {
            return null;
        }
        JsonElement element = json.get(key);
        List<Identifier> list = new ArrayList<>();
        if (element.isJsonPrimitive()) {
            Identifier single = Identifier.tryParse(element.getAsString());
            if (single == null) {
                return null;
            }
            for (int i = 0; i < 4; i++) list.add(single);
            return list;
        }
        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            if (array.size() != 1 && array.size() != 4) {
                return null;
            }
            for (JsonElement item : array) {
                Identifier parsed = Identifier.tryParse(item.getAsString());
                if (parsed == null) {
                    return null;
                }
                list.add(parsed);
            }
            while (list.size() < 4) {
                list.add(list.get(0));
            }
            return list;
        }
        return null;
    }

    private static @Nullable Identifier identifier(JsonObject json, String key) {
        if (!json.has(key) || json.get(key).isJsonNull()) return null;
        return Identifier.tryParse(json.get(key).getAsString());
    }
}
