package net.lordcambion.demoniacraft.worldgen.dimension;

import com.mojang.datafixers.util.Pair;
import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> DEMONVM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "demonvm"));

    public static final ResourceKey<Level> DEMONVM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "demonvm"));

    public static final ResourceKey<DimensionType> DEMONVM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "demonvm_type"));

    public static void bootstrapType(BootstrapContext<DimensionType> context) {
        context.register(DEMONVM_TYPE, new DimensionType(
                OptionalLong.empty(),
                false,  // has skylight
                false, // has ceiling
                false, // ultraWarm
                true,  // natural
                1.0,   // coordinate scale
                true,  // bed works
                false, // respawn anchor works
                -64,   // min Y
                384,   // height (come Overworld)
                384,   // logical height
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0.0f,  // ambient light
                Optional.empty(),
                new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 0), 0)
        ));
    }

    public static void bootstrapStem(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        // ✅ PARAMETRI CLIMATE PER OCEANI E TERRAFERMA
        NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(List.of(
                                // DARK_BIOME - Terreno pianeggiante con colline
                                Pair.of(
                                        Climate.parameters(
                                                0.3F,   // temperature (medio)
                                                0.4F,   // humidity (medio)
                                                0.1F,   // continentalness (costa/entroterra leggero)
                                                0.2F,   // erosion (poca erosione = colline dolci)
                                                0.0F,   // depth (superficie)
                                                0.3F,   // weirdness (variabilità per colline)
                                                0.0F    // offset
                                        ),
                                        biomeRegistry.getOrThrow(ModBiomes.DARK_BIOME)
                                ),
                                // DARK_BIOME - Variante più pianeggiante
                                Pair.of(
                                        Climate.parameters(
                                                0.3F,   // temperature
                                                0.4F,   // humidity
                                                0.2F,   // continentalness (più interno)
                                                0.0F,   // erosion (zero erosione = pianura)
                                                0.0F,   // depth
                                                0.0F,   // weirdness (nessuna variazione)
                                                0.0F    // offset
                                        ),
                                        biomeRegistry.getOrThrow(ModBiomes.DARK_BIOME)
                                ),
                                // DARK_OCEAN - Oceani profondi
                                Pair.of(
                                        Climate.parameters(
                                                0.2F,   // temperature (freddo)
                                                0.5F,   // humidity (umido)
                                                -0.7F,  // continentalness (negativo profondo = oceano)
                                                0.0F,   // erosion
                                                0.0F,   // depth
                                                0.0F,   // weirdness
                                                0.0F    // offset
                                        ),
                                        biomeRegistry.getOrThrow(ModBiomes.DARK_OCEAN)
                                ),
                                // DARK_OCEAN - Mare costiero/shallow
                                Pair.of(
                                        Climate.parameters(
                                                0.25F,  // temperature
                                                0.5F,   // humidity
                                                -0.3F,  // continentalness (negativo leggero = costa)
                                                0.0F,   // erosion
                                                0.0F,   // depth
                                                0.0F,   // weirdness
                                                0.0F    // offset
                                        ),
                                        biomeRegistry.getOrThrow(ModBiomes.DARK_OCEAN)
                                )
                        ))
                ),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD) // ✅ AMPLIFIED per le colline!
        );

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.DEMONVM_TYPE), noiseBasedChunkGenerator);
        context.register(DEMONVM_KEY, stem);
    }
}