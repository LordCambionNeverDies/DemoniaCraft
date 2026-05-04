package net.lordcambion.demoniacraft.worldgen;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.entity.ModEntities;
import net.lordcambion.demoniacraft.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    // Arkadium ore (già esistenti)
    public static final ResourceKey<BiomeModifier> ADD_ARKADIUM_ORE = registerKey("add_arkadium_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_ARKADIUM_ORE = registerKey("add_nether_arkadium_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_PYRESTONE_ORE = registerKey("add_nether_pyrestone_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_ARKADIUM_ORE = registerKey("add_end_arkadium_ore");

    // ✅ NUOVI: Minerali vanilla nei biomi custom (DARK_BIOME e DARK_OCEAN)
    public static final ResourceKey<BiomeModifier> ADD_DARKSTONE_COAL_ORE = registerKey("add_darkstone_coal_ore");
    public static final ResourceKey<BiomeModifier> ADD_DARKSTONE_IRON_ORE = registerKey("add_darkstone_iron_ore");
    public static final ResourceKey<BiomeModifier> ADD_DARKSTONE_GOLD_ORE = registerKey("add_darkstone_gold_ore");
    public static final ResourceKey<BiomeModifier> ADD_DARKSTONE_DIAMOND_ORE = registerKey("add_darkstone_diamond_ore");

    // Vegetazione
    public static final ResourceKey<BiomeModifier> ADD_WALNUT_TREE = registerKey("add_walnut_tree");
    public static final ResourceKey<BiomeModifier> ADD_STRAWBERRY_BUSH = registerKey("add_strawberry_bush");

    // Spawn entità
    public static final ResourceKey<BiomeModifier> SPAWN_HEDGEHOG = registerKey("spawn_hedgehog");
    public static final ResourceKey<BiomeModifier> SPAWN_BONEREAPER = registerKey("spawn_bonereaper");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        // ✅ ARKADIUM ORE (overworld standard)
        context.register(ADD_ARKADIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.ARKADIUM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // Nether e End Arkadium
        context.register(ADD_NETHER_ARKADIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.NETHER_ARKADIUM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_END_ARKADIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.END_ARKADIUM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_NETHER_PYRESTONE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.NETHER_PYRESTONE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // ✅ MINERALI VANILLA NEI BIOMI CUSTOM (DARK_BIOME e DARK_OCEAN)
        // Carbone
        context.register(ADD_DARKSTONE_COAL_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.DARK_BIOME), biomes.getOrThrow(ModBiomes.DARK_OCEAN)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.DARKSTONE_COAL_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // Ferro
        context.register(ADD_DARKSTONE_IRON_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.DARK_BIOME), biomes.getOrThrow(ModBiomes.DARK_OCEAN)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.DARKSTONE_IRON_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // Oro
        context.register(ADD_DARKSTONE_GOLD_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.DARK_BIOME), biomes.getOrThrow(ModBiomes.DARK_OCEAN)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.DARKSTONE_GOLD_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // Diamanti
        context.register(ADD_DARKSTONE_DIAMOND_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.DARK_BIOME), biomes.getOrThrow(ModBiomes.DARK_OCEAN)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.DARKSTONE_DIAMOND_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // Vegetazione
        context.register(ADD_WALNUT_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.FOREST), biomes.getOrThrow(Biomes.SAVANNA)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.WALNUT_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_STRAWBERRY_BUSH, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.FOREST), biomes.getOrThrow(Biomes.FLOWER_FOREST)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.STRAWBERRY_BUSH_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        // Spawn entità
        context.register(SPAWN_HEDGEHOG, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.FLOWER_FOREST), biomes.getOrThrow(Biomes.PLAINS)),
                WeightedList.<MobSpawnSettings.SpawnerData>builder()
                        .add(new MobSpawnSettings.SpawnerData(ModEntities.HEDGEHOG.get(), 2, 4), 15)
                        .build()));

        context.register(SPAWN_BONEREAPER, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.NETHER_WASTES), biomes.getOrThrow(ModBiomes.DARK_BIOME)),
                WeightedList.<MobSpawnSettings.SpawnerData>builder()
                        .add(new MobSpawnSettings.SpawnerData(ModEntities.BONEREAPER.get(), 2, 4), 15)
                        .build()));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, name));
    }
}