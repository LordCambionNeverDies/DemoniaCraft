package net.lordcambion.demoniacraft.datagen;

import net.lordcambion.demoniacraft.Demoniacraft;

import net.lordcambion.demoniacraft.enchantment.ModEnchantments;
import net.lordcambion.demoniacraft.worldgen.*;
import net.lordcambion.demoniacraft.worldgen.biome.ModBiomes;
import net.lordcambion.demoniacraft.worldgen.dimension.ModDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackEntries extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER=new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType)
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(ForgeRegistries.Keys.BIOMES, ModBiomes::bootstrap)
           // .add(Registries.CONFIGURED_CARVER, ModCaveCarvers::bootstrap)
            .add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem);
           // .add(Registries.NOISE_SETTINGS, ModNoiseGeneratorSettings::bootstrap);

//            .add(Registries.TRIM_MATERIAL, ModTrimMaterials::bootstrap);


    public ModDatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,BUILDER, Set.of(Demoniacraft.MOD_ID));
    }
}
