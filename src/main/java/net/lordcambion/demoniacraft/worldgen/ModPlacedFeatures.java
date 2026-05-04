package net.lordcambion.demoniacraft.worldgen;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    // ✅ Arkadium placed keys (già esistenti)
    public static final ResourceKey<PlacedFeature> ARKADIUM_ORE_PLACED_KEY = registerKey("arkadium_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_ARKADIUM_ORE_PLACED_KEY = registerKey("nether_arkadium_ore_placed");
    public static final ResourceKey<PlacedFeature> END_ARKADIUM_ORE_PLACED_KEY = registerKey("end_arkadium_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_PYRESTONE_ORE_PLACED_KEY = registerKey("nether_pyrestone_ore_placed");

    // ✅ NUOVI: Minerali vanilla nel quarzo
    public static final ResourceKey<PlacedFeature> DARKSTONE_COAL_ORE_PLACED_KEY = registerKey("darkstone_coal_ore_placed");
    public static final ResourceKey<PlacedFeature> DARKSTONE_IRON_ORE_PLACED_KEY = registerKey("darkstone_iron_ore_placed");
    public static final ResourceKey<PlacedFeature> DARKSTONE_GOLD_ORE_PLACED_KEY = registerKey("darkstone_gold_ore_placed");
    public static final ResourceKey<PlacedFeature> DARKSTONE_DIAMOND_ORE_PLACED_KEY = registerKey("darkstone_diamond_ore_placed");

    // Altri placed features (alberi, cespugli, etc.)
    public static final ResourceKey<PlacedFeature> WALNUT_TREE_PLACED_KEY = registerKey("walnut_tree_placed");
    public static final ResourceKey<PlacedFeature> STRAWBERRY_BUSH_PLACED_KEY = registerKey("strawberry_bush_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // ✅ CARBONE nel quarzo - Y 0 to 136, molto comune
        register(context, DARKSTONE_COAL_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.QUARTZ_COAL_ORE_KEY),
                commonOrePlacement(20, // 20 veins per chunk
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(136))));

        // ✅ FERRO nel quarzo - Y -64 to 80, comune
        register(context, DARKSTONE_IRON_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.QUARTZ_IRON_ORE_KEY),
                commonOrePlacement(12, // 12 veins per chunk
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        // ✅ ORO nel quarzo - Y -64 to 32, raro
        register(context, DARKSTONE_GOLD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.QUARTZ_GOLD_ORE_KEY),
                commonOrePlacement(4, // 4 veins per chunk
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));

        // ✅ DIAMANTI nel quarzo - Y -64 to 16, molto raro
        register(context, DARKSTONE_DIAMOND_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.QUARTZ_DIAMOND_ORE_KEY),
                rareOrePlacement(7, // 7 tries per chunk (molto raro)
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(16))));

        // ✅ Arkadium placement (esempio - adatta ai tuoi bisogni)
        register(context,ARKADIUM_ORE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ARKADIUM_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(24))));
        register(context,NETHER_ARKADIUM_ORE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_ARKADIUM_ORE_KEY),
                ModOrePlacement.commonOrePlacement(7,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(40))));
        register(context,END_ARKADIUM_ORE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.END_ARKADIUM_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(80))));
        register(context,NETHER_PYRESTONE_ORE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_PYRESTONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(80))));

        register(context,WALNUT_TREE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.WALNUT_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.05f, 1),
                        ModBlocks.WALNUT_SAPLING.get()));

        register(context,STRAWBERRY_BUSH_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.STRAWBERRY_BUSH_KEY),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),PlacementUtils.HEIGHTMAP_WORLD_SURFACE,BiomeFilter.biome()));


    }


    // ✅ Helper method per ore comuni
    private static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
        return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
