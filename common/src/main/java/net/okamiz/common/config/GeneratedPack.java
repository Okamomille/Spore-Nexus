package net.okamiz.common.config;

import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.ItemsRegistry;
import net.okamiz.common.records.AltarDefinition;
import net.okamiz.common.records.CustomMushroomDefinition;
import net.okamiz.common.records.IngredientRef;
import net.okamiz.common.records.MutationDefinition;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class GeneratedPack {

    private GeneratedPack() {}


    private static final int RESOURCE_FORMAT = 34;
    private static final int DATA_FORMAT = 48;

    public static Path directory() {
        return Platform.getConfigFolder().resolve("sporenexus").resolve("generated_pack");
    }

    private static String mcmeta() {
        return "{ \"pack\": { \"description\": \"Spore Nexus generated\", \"pack_format\": " + DATA_FORMAT
                + ", \"supported_formats\": [" + RESOURCE_FORMAT + ", " + DATA_FORMAT + "] } }";
    }

    public static Path texturesDirectory() {
        return Platform.getConfigFolder().resolve("sporenexus").resolve("textures");
    }


    public static void generate() {
        Path root = directory();
        try {
            deleteRecursively(root);
            Files.createDirectories(texturesDirectory());
            Files.createDirectories(root);
            Files.writeString(root.resolve("pack.mcmeta"), mcmeta());

            StringBuilder lang = new StringBuilder("{\n");
            boolean first = true;
            for (CustomMushroomDefinition def : ItemsRegistry.CUSTOM_MUSHROOMS) {
                String name = def.name() != null ? def.name() : prettify(def.id());
                if (!first) lang.append(",\n");
                first = false;
                lang.append("  \"block.sporenexus.").append(def.blockName()).append("\": \"").append(name).append(" Resource Mushroom\",\n");
                lang.append("  \"item.sporenexus.").append(def.fragmentName()).append("\": \"").append(name).append(" Fragments\"");
            }
            lang.append("\n}");
            write(root.resolve("assets/sporenexus/lang/en_us.json"), lang.toString());

            for (CustomMushroomDefinition def : ItemsRegistry.CUSTOM_MUSHROOMS) {
                generateMushroomAssets(root, def);
                generateMushroomData(root, def);
            }



        } catch (IOException e) {
            SporeNexus.LOGGER.error("Could not generate the pack", e);
        }
    }
    private static String prettify(String id) {
        StringBuilder sb = new StringBuilder();
        for (String word : id.split("_")) {
            if (word.isEmpty()) continue;
            if (sb.length() > 0) sb.append(' ');
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return sb.toString();
    }

    private static void generateMushroomData(Path data, CustomMushroomDefinition def) throws IOException {
        Path recipes = data.resolve("data/sporenexus/recipe");


        write(recipes.resolve(def.id() + "_from_fragments.json"),
                "{ " + conditions(List.of(def.fragmentResult()))
                        + "\"type\": \"minecraft:crafting_shaped\", \"group\": \"fragments_to_resource\","

                        + " \"key\": { \"X\": { \"item\": \"sporenexus:" + def.fragmentName() + "\" } },"
                        + " \"pattern\": [\"XX\", \"XX\"],"
                        + " \"result\": { \"id\": \"" + def.fragmentResult() + "\", \"count\": " + def.fragmentCount() + " } }");

        AltarDefinition altar = def.altar();
        if (altar != null) {
            write(recipes.resolve(def.blockName() + "_from_altar.json"),
                    "{ " + conditions(altarItems(altar))
                            + "\"type\": \"sporenexus:spore_nexus_craft\","
                            + " \"mushroom_input\": \"" + altar.mushroomInput() + "\","
                            + " \"resource_input\": " + jsonList(altar.resourceInputs()) + ","
                            + " \"secondary_input\": " + jsonList(altar.secondaryInputs()) + ","
                            + " \"result\": { \"count\": 1, \"id\": \"sporenexus:" + def.blockName() + "\" } }");
        }
        int index = 0;
        for (MutationDefinition mutation : def.mutations()) {
            index++;
            write(recipes.resolve(def.id() + "_mutation_" + index + ".json"),
                    "{ " + conditions(List.of(mutation.parentA(), mutation.parentB()))
                            + "\"type\": \"sporenexus:mushroom_mutation\","
                            + " \"parent_a\": \"" + mutation.parentA() + "\","
                            + " \"parent_b\": \"" + mutation.parentB() + "\","
                            + " \"result\": { \"count\": 1, \"id\": \"sporenexus:" + def.blockName() + "\" },"
                            + " \"chance\": " + mutation.chance() + " }");
        }
    }

    /**
     * Load conditions for NeoForge AND Fabric (each loader ignores the other key): the recipe is only
     * loaded when every listed item exists. Mutation parents are checked as items too, because the
     * recipe matches them through their block item.
     * Formats for 1.21.1:
     *  - NeoForge: {"type": "neoforge:item_exists", "item": ...} (one per item)
     *  - Fabric:   {"condition": "fabric:registry_contains", "registry": "minecraft:item", "values": [...]}
     * Returns an empty string when there is nothing to check, otherwise both fields followed by a comma.
     */
    private static String conditions(Collection<ResourceLocation> ids) {
        Set<String> unique = new LinkedHashSet<>();
        for (ResourceLocation id : ids) {
            if (id != null) unique.add(id.toString());
        }
        if (unique.isEmpty()) return "";

        String neo = unique.stream()
                .map(id -> "{ \"type\": \"neoforge:item_exists\", \"item\": \"" + id + "\" }")
                .collect(Collectors.joining(", ", "[", "]"));
        String fabricValues = unique.stream()
                .map(id -> "\"" + id + "\"")
                .collect(Collectors.joining(", ", "[", "]"));

        return "\"neoforge:conditions\": " + neo + ", "
                + "\"fabric:load_conditions\": [{ \"condition\": \"fabric:registry_contains\", \"registry\": \"minecraft:item\", \"values\": " + fabricValues + " }], ";
    }

    private static List<ResourceLocation> altarItems(AltarDefinition altar) {
        List<ResourceLocation> items = new ArrayList<>();
        addIfItem(items, altar.mushroomInput());
        altar.resourceInputs().forEach(ref -> addIfItem(items, ref));
        altar.secondaryInputs().forEach(ref -> addIfItem(items, ref));
        return items;
    }

    private static void addIfItem(List<ResourceLocation> out, IngredientRef ref) {
        if (ref != null && !ref.tag()) out.add(ref.id());
    }

    private static String jsonList(List<IngredientRef> refs) {
        return refs.stream().map(ref -> "\"" + ref + "\"").collect(Collectors.joining(", ", "[", "]"));
    }

    private static void generateMushroomAssets(Path resources, CustomMushroomDefinition def) throws IOException {
        String id = def.id();
        String block = def.blockName();
        int color = parseColor(def.color());

        writeTexture(id + "_cap.png", 32, "/assets/sporenexus/textures/template/cap_base.png", color,
                resources.resolve("assets/sporenexus/textures/block/mushroom/" + id + "_cap.png"));
        writeTexture(def.fragmentName() + ".png", 16, "/assets/sporenexus/textures/template/fragments_base.png", color,
                resources.resolve("assets/sporenexus/textures/item/" + def.fragmentName() + ".png"));

        int[] stages = {0, 0, 0, 1, 1, 1, 1, 1, 2};
        StringBuilder variants = new StringBuilder();
        for (int age = 0; age < stages.length; age++) {
            if (age > 0) variants.append(",\n");
            variants.append("    \"age=").append(age).append("\": { \"model\": \"sporenexus:block/").append(block).append("_stage_").append(stages[age]).append("\" }");
        }
        write(resources.resolve("assets/sporenexus/blockstates/" + block + ".json"), "{ \"variants\": {\n" + variants + "\n} }");

        for (int stage = 0; stage < 3; stage++) {
            write(resources.resolve("assets/sporenexus/models/block/" + block + "_stage_" + stage + ".json"),
                    "{ \"parent\": \"sporenexus:block/template/mushroom_stage_" + stage + "\","
                            + " \"textures\": { \"cap\": \"sporenexus:block/mushroom/" + id + "_cap\","
                            + " \"stem\": \"sporenexus:block/mushroom/stem\","
                            + " \"particle\": \"sporenexus:block/mushroom/" + id + "_cap\" } }");
        }


        write(resources.resolve("assets/sporenexus/models/item/" + block + ".json"),
                "{ \"parent\": \"sporenexus:block/" + block + "_stage_1\" }");

        write(resources.resolve("assets/sporenexus/models/item/" + def.fragmentName() + ".json"),
                "{ \"parent\": \"minecraft:item/generated\"," + " \"textures\": { \"layer0\": \"sporenexus:item/" + def.fragmentName() + "\" } }");
    }

    private static int parseColor(String hex) {
        return Integer.parseInt(hex.substring(1), 16);
    }

    private static void writeTexture(String overrideName, int expectedSize, String baseResource, int color, Path target) throws IOException {
        Path override = texturesDirectory().resolve(overrideName);
        if (Files.isRegularFile(override)) {
            try {
                BufferedImage image = ImageIO.read(override.toFile());
                if (image == null) {
                    SporeNexus.LOGGER.error("Texture {} is not a valid PNG image, using the generated one", override);
                } else if (image.getWidth() != expectedSize || image.getHeight() != expectedSize) {
                    SporeNexus.LOGGER.error("Texture {} must be {}x{} pixels (it is {}x{}), using the generated one",
                            override, expectedSize, expectedSize, image.getWidth(), image.getHeight());
                } else {
                    Files.createDirectories(target.getParent());
                    ImageIO.write(image, "png", target.toFile());
                    return;
                }
            } catch (IOException e) {
                SporeNexus.LOGGER.error("Could not read texture {}, using the generated one", override, e);
            }
        }
        writeTinted(baseResource, color, target);
    }

    private static void writeTinted(String baseResource, int rgb, Path target) throws IOException {
        try (InputStream in = GeneratedPack.class.getResourceAsStream(baseResource)) {
            if (in == null) throw new IOException("Missing base texture " + baseResource);
            BufferedImage image = ImageIO.read(in);
            int tr = (rgb >> 16) & 0xFF, tg = (rgb >> 8) & 0xFF, tb = rgb & 0xFF;
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int argb = image.getRGB(x, y);
                    int a = (argb >>> 24) & 0xFF;
                    if (a == 0) continue;
                    int r = ((argb >> 16) & 0xFF) * tr / 255;
                    int g = ((argb >> 8) & 0xFF) * tg / 255;
                    int b = (argb & 0xFF) * tb / 255;
                    image.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
                }
            }
            Files.createDirectories(target.getParent());
            ImageIO.write(image, "png", target.toFile());
        }
    }

    private static void deleteRecursively(Path path) throws IOException {
        if (!Files.exists(path)) return;
        try (var walk = Files.walk(path)) {
            walk.sorted(java.util.Comparator.reverseOrder()).forEach(p -> {
                try { Files.delete(p); } catch (IOException ignored) {}
            });
        }
    }

    private static void write(Path file, String content) throws IOException {
        Files.createDirectories(file.getParent());
        Files.writeString(file, content);
    }
}