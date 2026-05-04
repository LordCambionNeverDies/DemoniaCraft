package net.lordcambion.demoniacraft.item;


import net.lordcambion.demoniacraft.Demoniacraft;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class ModArmorMaterials {
    // Create the root registry key for equipment assets
    private static final ResourceKey<? extends Registry<EquipmentAsset>> EQUIPMENT_ASSET_REGISTRY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "equipment_asset"));

    // Example: Arkadium Armor
    public static final ArmorMaterial ARKADIUM = new ArmorMaterial(
            25, // durability multiplier (come il diamante)
            Map.of(                      // Valori di difesa corretti
                    ArmorType.HELMET, 3,
                    ArmorType.CHESTPLATE, 8,
                    ArmorType.LEGGINGS, 6,
                    ArmorType.BOOTS, 3
            ),
            15, // enchantability
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            2.0F, // toughness
            0.0F, // knockback resistance
            createRepairTag("arkadium_ingot"),
            createAssetKey("arkadium")
    );
    public static final ArmorMaterial ENDER = new ArmorMaterial(
            25, // durability multiplier (come il diamante)
            Map.of(                      // Valori di difesa corretti
                    ArmorType.HELMET, 4,
                    ArmorType.CHESTPLATE, 9,
                    ArmorType.LEGGINGS, 7,
                    ArmorType.BOOTS, 4
            ),
            15, // enchantability
            SoundEvents.ARMOR_EQUIP_ELYTRA,
            2.0F, // toughness
            0.0F, // knockback resistance
            createRepairTag("ender_ingot"),
            createAssetKey("ender")
    );

    private static Map<ArmorType, Integer> makeDefense(int boots, int leggings, int chestplate, int helmet, int body) {
        return Map.of(
                ArmorType.BOOTS, boots,
                ArmorType.LEGGINGS, leggings,
                ArmorType.CHESTPLATE, chestplate,
                ArmorType.HELMET, helmet,
                ArmorType.BODY, body
        );
    }

    private static TagKey<Item> createRepairTag(String tagName) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, tagName));
    }

    private static ResourceKey<EquipmentAsset> createAssetKey(String materialName) {
        // Usa la registry vanilla invece di crearne una personalizzata
        return ResourceKey.create(
                EquipmentAssets.ROOT_ID,
                ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, materialName)
        );
    }

}