package net.lordcambion.demoniacraft.worldgen.biome;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {

    public static final ResourceKey<Biome> DARK_BIOME = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "dark_biome"));

    public static final ResourceKey<Biome> DARK_OCEAN = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "dark_ocean"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(DARK_BIOME, darkBiome(context));
        context.register(DARK_OCEAN, darkOcean(context));
    }

    private static Biome darkBiome(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        // ✅ CAVERNE: Aggiungi carver e features sicure
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder); // Formazioni cristalline
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder); // Dungeon spawner

        // Features base
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        BiomeDefaultFeatures.addInfestedStone(biomeBuilder); // Pietra infestata da silverfish

        // Vegetazione custom
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.WALNUT_TREE_PLACED_KEY);

        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .waterColor(0xe82e3b)
                .waterFogColor(0xbf1b26)
                .skyColor(0x000000)
                .grassColorOverride(0x423189)
                .foliageColorOverride(0x423189)
                .fogColor(0x000000)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_GROVE));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7f)
                .downfall(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(effects.build())
                .build();
    }

    private static Biome darkOcean(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        // Features oceaniche con caverne sottomarine
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);

        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .waterColor(0xe82e3b)
                .waterFogColor(0xbf1b26)
                .skyColor(0x000000)
                .grassColorOverride(0x423189)
                .foliageColorOverride(0x423189)
                .fogColor(0x000000)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(effects.build())
                .build();
    }
}