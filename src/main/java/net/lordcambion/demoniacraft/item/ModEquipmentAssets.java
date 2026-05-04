package net.lordcambion.demoniacraft.item;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class ModEquipmentAssets {
    public static final ResourceKey<EquipmentAsset> ARKADIUM = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "arkadium") // mod3rnmod:arkadium
    );
    public static final ResourceKey<EquipmentAsset> ENDER = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "ender") // mod3rnmod:ender
    );
}