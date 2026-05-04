package net.lordcambion.demoniacraft.worldgen.biome.surface;

import net.lordcambion.demoniacraft.block.ModBlocks;
import net.lordcambion.demoniacraft.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource DARKSTONE = makeStateRule(ModBlocks.DARK_STONE_BLOCK.get());
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);

    public static SurfaceRules.RuleSource makeRules() {
        // Bedrock negli ultimi 5 blocchi sopra il fondo
        SurfaceRules.ConditionSource bedrockCondition = SurfaceRules.verticalGradient(
                "bedrock",
                VerticalAnchor.bottom(),
                VerticalAnchor.aboveBottom(5)
        );

        // Condizione per essere SOTTO il livello dell'acqua
        SurfaceRules.ConditionSource isBelowWaterLevel = SurfaceRules.not(
                SurfaceRules.waterBlockCheck(-1, 0)
        );

        return SurfaceRules.sequence(
                // DARK_BIOME
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.DARK_BIOME),
                        SurfaceRules.sequence(
                                // Bedrock al fondo
                                SurfaceRules.ifTrue(bedrockCondition, BEDROCK),

                                // SOLO SUPERFICIE REALE (non caverne): abovePreliminarySurface esclude le caverne
                                SurfaceRules.ifTrue(
                                        SurfaceRules.abovePreliminarySurface(),
                                        SurfaceRules.sequence(
                                                // Erba sulla superficie sopra l'acqua
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.ON_FLOOR,
                                                        SurfaceRules.ifTrue(
                                                                SurfaceRules.waterBlockCheck(-1, 0),
                                                                GRASS_BLOCK
                                                        )
                                                ),
                                                // Terra sotto l'erba (3-4 blocchi)
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                                                        DIRT
                                                ),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.stoneDepthCheck(1, false, 0, CaveSurface.FLOOR),
                                                        DIRT
                                                ),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.stoneDepthCheck(2, false, 0, CaveSurface.FLOOR),
                                                        DIRT
                                                ),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.stoneDepthCheck(3, false, 0, CaveSurface.FLOOR),
                                                        DIRT
                                                )
                                        )
                                ),

                                // Tutto il resto è DARKSTONE (incluse le caverne)
                                DARKSTONE
                        )
                ),

                // DARK_OCEAN
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.DARK_OCEAN),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(bedrockCondition, BEDROCK),

                                // Superficie del fondale oceanico
                                SurfaceRules.ifTrue(
                                        SurfaceRules.ON_FLOOR,
                                        SurfaceRules.ifTrue(isBelowWaterLevel,
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.GRAVEL, -0.1), SAND),
                                                        GRAVEL
                                                )
                                        )
                                ),

                                // Sotto il fondale
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                                        SurfaceRules.ifTrue(isBelowWaterLevel, SAND)
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(1, false, 0, CaveSurface.FLOOR),
                                        SurfaceRules.ifTrue(isBelowWaterLevel, SAND)
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(2, false, 0, CaveSurface.FLOOR),
                                        SurfaceRules.ifTrue(isBelowWaterLevel, GRAVEL)
                                ),

                                DARKSTONE
                        )
                ),

                // FALLBACK
                DARKSTONE
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}