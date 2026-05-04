package net.lordcambion.demoniacraft.worldgen;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.block.ModBlocks;
import net.lordcambion.demoniacraft.block.custom.StrawBerryBushBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;


public class ModConfiguredFeatures {



    // ✅ NUOVI: Minerali vanilla che spawnano nel QUARZO
    public static final ResourceKey<ConfiguredFeature<?, ?>> QUARTZ_COAL_ORE_KEY = registerKey("quartz_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> QUARTZ_IRON_ORE_KEY = registerKey("quartz_iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> QUARTZ_GOLD_ORE_KEY = registerKey("quartz_gold_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> QUARTZ_DIAMOND_ORE_KEY = registerKey("quartz_diamond_ore");
    public  static  final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_ARKADIUM_ORE_KEY =registerKey("arkadium_ore");
    public  static  final ResourceKey<ConfiguredFeature<?,?>> NETHER_ARKADIUM_ORE_KEY =registerKey("nether_arkadium_ore");
    public  static  final ResourceKey<ConfiguredFeature<?,?>> END_ARKADIUM_ORE_KEY =registerKey("end_arkadium_ore");
    public  static  final ResourceKey<ConfiguredFeature<?,?>> NETHER_PYRESTONE_ORE_KEY =registerKey("nether_pyrestone_ore");
    public  static  final ResourceKey<ConfiguredFeature<?,?>> WALNUT_TREE_KEY =registerKey("walnut_tree");
    public  static  final ResourceKey<ConfiguredFeature<?,?>> STRAWBERRY_BUSH_KEY =registerKey("strayberry_bush");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endstoneReplaceables = new BlockMatchTest(Blocks.END_STONE);
        RuleTest quartzReplaceables = new BlockMatchTest(Blocks.QUARTZ_BLOCK);
        List<OreConfiguration.TargetBlockState>overworldArkadiumOres=List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ARKADIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables,ModBlocks.ARKADIUM_DEEPSLATE_ORE.get().defaultBlockState())
        );
        register(context,OVERWORLD_ARKADIUM_ORE_KEY,Feature.ORE,new OreConfiguration(overworldArkadiumOres,9));

        register(context,NETHER_ARKADIUM_ORE_KEY,Feature.ORE,new OreConfiguration(netherrackReplaceables,
                ModBlocks.ARKADIUM_NETHER_ORE.get().defaultBlockState(),9));

        register(context,END_ARKADIUM_ORE_KEY,Feature.ORE,new OreConfiguration(endstoneReplaceables,
                ModBlocks.ARKADIUM_END_ORE.get().defaultBlockState(),9));

        register(context,NETHER_PYRESTONE_ORE_KEY,Feature.ORE,new OreConfiguration(netherrackReplaceables,
                ModBlocks.PYRESTONE_ORE.get().defaultBlockState(),9));


        List<OreConfiguration.TargetBlockState> quartzCoalOres = List.of(
                OreConfiguration.target(quartzReplaceables, Blocks.COAL_ORE.defaultBlockState())
        );
        register(context, QUARTZ_COAL_ORE_KEY, Feature.ORE, new OreConfiguration(quartzCoalOres, 17)); // vein size 17

        // Ferro - spawna nel quarzo
        List<OreConfiguration.TargetBlockState> quartzIronOres = List.of(
                OreConfiguration.target(quartzReplaceables, Blocks.IRON_ORE.defaultBlockState())
        );
        register(context, QUARTZ_IRON_ORE_KEY, Feature.ORE, new OreConfiguration(quartzIronOres, 9)); // vein size 9

        // Oro - spawna nel quarzo
        List<OreConfiguration.TargetBlockState> quartzGoldOres = List.of(
                OreConfiguration.target(quartzReplaceables, Blocks.GOLD_ORE.defaultBlockState())
        );
        register(context, QUARTZ_GOLD_ORE_KEY, Feature.ORE, new OreConfiguration(quartzGoldOres, 9)); // vein size 9

        // Diamanti - spawna nel quarzo
        List<OreConfiguration.TargetBlockState> quartzDiamondOres = List.of(
                OreConfiguration.target(quartzReplaceables, Blocks.DIAMOND_ORE.defaultBlockState())
        );
        register(context, QUARTZ_DIAMOND_ORE_KEY, Feature.ORE, new OreConfiguration(quartzDiamondOres, 8)); // vein size 8


        register(context,WALNUT_TREE_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.WALNUT_LOG.get()),
                new ForkingTrunkPlacer(4,4,3),
                BlockStateProvider.simple(ModBlocks.WALNUT_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(3),3),
                new TwoLayersFeatureSize(1,0,2)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());

        register(context,STRAWBERRY_BUSH_KEY,Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,new SimpleBlockConfiguration(
                        BlockStateProvider.simple(ModBlocks.STRAWBERRY_BUSH.get().defaultBlockState().setValue(StrawBerryBushBlock.AGE,Integer.valueOf(3)))
                ),List.of(Blocks.GRASS_BLOCK)));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}



