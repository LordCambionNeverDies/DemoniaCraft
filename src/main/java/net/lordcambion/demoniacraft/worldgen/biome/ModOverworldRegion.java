package net.lordcambion.demoniacraft.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.lordcambion.demoniacraft.Demoniacraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class ModOverworldRegion extends Region {
    public ModOverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        // ✅ 添加自定义生物群系到主世界
        this.addBiome(mapper,
                Climate.parameters(
                        0.7F, // temperatura (匹配你的生物群系温度)
                        0.8F, // umidità (匹配你的生物群系降水量)
                        0.2F, // continentalità
                        0.0F, // erosione
                        0.0F, // profondità
                        0.0F, // weirdness
                        0.0F  // offset
                ),
                ModBiomes.DARK_BIOME);

        // 可选：添加黑暗海洋
        this.addBiome(mapper,
                Climate.parameters(
                        0.5F, // temperatura (匹配你的生物群系温度)
                        0.5F, // umidità (匹配你的生物群系降水量)
                        -0.6F, // continentalità
                        0.0F, // erosione
                        0.0F, // profondità
                        0.0F, // weirdness
                        0.0F  // offset
                ),
                ModBiomes.DARK_OCEAN);

    }
}