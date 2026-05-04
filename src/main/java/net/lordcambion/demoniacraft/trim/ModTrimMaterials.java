package net.lordcambion.demoniacraft.trim;

import net.lordcambion.demoniacraft.Demoniacraft;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ProvidesTrimMaterial;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;

import java.util.Optional;

public class ModTrimMaterials {

    public static final ResourceKey<TrimMaterial> ARKADIUM = registryKey("arkadium");

    public static final MaterialAssetGroup ARKADIUM_ASSETS = MaterialAssetGroup.create("arkadium");



    public static void bootstrap(BootstrapContext<TrimMaterial> pContext) {
        register(pContext, ARKADIUM, Style.EMPTY.withColor(TextColor.parseColor("#031cfc").getOrThrow()), ARKADIUM_ASSETS);
    }

    public static Optional<Holder<TrimMaterial>> getFromIngredient(HolderLookup.Provider pRegistries, ItemStack pIngredient) {
        ProvidesTrimMaterial providestrimmaterial = pIngredient.get(DataComponents.PROVIDES_TRIM_MATERIAL);
        return providestrimmaterial != null ? providestrimmaterial.unwrap(pRegistries) : Optional.empty();
    }

    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Style style, MaterialAssetGroup assets) {
        Component component = Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style);
        context.register(trimKey, new TrimMaterial(assets, component));
    }

    private static ResourceKey<TrimMaterial> registryKey(String pName) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, pName));
    }
}