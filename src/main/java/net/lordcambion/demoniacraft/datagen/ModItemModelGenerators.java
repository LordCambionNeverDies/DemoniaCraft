package net.lordcambion.demoniacraft.datagen;

import net.lordcambion.demoniacraft.item.ModEquipmentAssets;
import net.lordcambion.demoniacraft.item.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.Optional;

public class ModItemModelGenerators extends ItemModelGenerators {
    public ModItemModelGenerators(ItemModelOutput pItemModelOutput, BiConsumer<ResourceLocation, ModelInstance> pModelOutput) {
        super(pItemModelOutput, pModelOutput);
    }

    @Override
    public void run() {

        //ores
        generateFlatItem(ModItems.PYRESTONE.get(), ModelTemplates.FLAT_ITEM);

        //food
        generateFlatItem(ModItems.POOP.get(), ModelTemplates.FLAT_ITEM);

       //misc
        generateFlatItem(ModItems.GLUE_BOTTLE.get(), ModelTemplates.FLAT_ITEM);

        //tools
        //generateFlatItem(ModItems.CHISEL.get(), ModelTemplates.FLAT_ITEM);
        generateDamageableItem(ModItems.CHISEL.get(), 0.5f);
        //horse armor

        generateBow(ModItems.ENDER_BOW.get());
        generateFlatItem(ModItems.ARKADIUM_HORSE_ARMOR.get(),ModelTemplates.FLAT_ITEM);

        //arkadium
        generateFlatItem(ModItems.RAW_ARKADIUM.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.ARKADIUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.ARKADIUM_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.ARKADIUM_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.ARKADIUM_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.ARKADIUM_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.ARKADIUM_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateTrimmableItem(ModItems.ARKADIUM_HELMET.get(), ModEquipmentAssets.ARKADIUM, TRIM_PREFIX_HELMET, false);
        generateTrimmableItem(ModItems.ARKADIUM_CHESTPLATE.get(), ModEquipmentAssets.ARKADIUM, TRIM_PREFIX_HELMET, false);
        generateTrimmableItem(ModItems.ARKADIUM_LEGGINGS.get(), ModEquipmentAssets.ARKADIUM, TRIM_PREFIX_HELMET, false);
        generateTrimmableItem(ModItems.ARKADIUM_BOOTS.get(), ModEquipmentAssets.ARKADIUM, TRIM_PREFIX_HELMET, false);

        generateTrimmableItem(ModItems.ENDER_HELMET.get(), ModEquipmentAssets.ENDER, TRIM_PREFIX_HELMET, false);
        generateTrimmableItem(ModItems.ENDER_CHESTPLATE.get(), ModEquipmentAssets.ENDER, TRIM_PREFIX_HELMET, false);
        generateTrimmableItem(ModItems.ENDER_LEGGINGS.get(), ModEquipmentAssets.ENDER, TRIM_PREFIX_HELMET, false);
        generateTrimmableItem(ModItems.ENDER_BOOTS.get(), ModEquipmentAssets.ENDER, TRIM_PREFIX_HELMET, false);

        generateFlatItem(ModItems.ENDER_ARROW.get(),ModelTemplates.FLAT_ITEM);
        //hammers
        generateFlatItem(ModItems.ARKADIUM_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.IRON_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.GOLDEN_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.DIAMOND_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.NETHERITE_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        //discs
        generateFlatItem(ModItems.CRYSIS_DISC.get(), ModelTemplates.MUSIC_DISC);
        generateFlatItem(ModItems.DYNAMITE_DISC.get(), ModelTemplates.MUSIC_DISC);
        generateFlatItem(ModItems.PANICDOX_DISC.get(), ModelTemplates.MUSIC_DISC);
        generateFlatItem(ModItems.DIAMONDS_DISC.get(), ModelTemplates.MUSIC_DISC);


        //seeds
        //generateFlatItem(ModItems.TOMATO_SEEDS.get(),ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.TOMATO.get(),ModelTemplates.FLAT_ITEM);
        if (this.itemModelOutput instanceof ModModelProvider.ModItemInfoCollector collector)
            collector.generateDefaultBlockModels();



    }
        private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ,0.1f);
        trimMaterials.put(TrimMaterials.IRON,0.2f);
        trimMaterials.put(TrimMaterials.NETHERITE,0.3f);
        trimMaterials.put(TrimMaterials.REDSTONE,0.4f);
        trimMaterials.put(TrimMaterials.COPPER,0.5f);
        trimMaterials.put(TrimMaterials.GOLD,0.6f);
        trimMaterials.put(TrimMaterials.EMERALD,0.7f);
        trimMaterials.put(TrimMaterials.DIAMOND,0.8f);
        trimMaterials.put(TrimMaterials.LAPIS,0.9f);
        trimMaterials.put(TrimMaterials.AMETHYST,1.0f);
    }

    private void generateDamageableItem(Item item, float damageThresholdNormalized) {
        ResourceLocation itemId = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item);
        String itemPath = itemId.getPath();

        // === Definisce i percorsi texture ===
        ResourceLocation normalModelLoc = ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "item/" + itemPath);
        ResourceLocation damagedModelLoc = ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "item/" + itemPath + "_damaged");

        // === Crea i due modelli di base ===
        ModelTemplates.FLAT_ITEM.create(
                normalModelLoc,
                TextureMapping.layer0(normalModelLoc),
                this.modelOutput
        );

        ModelTemplates.FLAT_ITEM.create(
                damagedModelLoc,
                TextureMapping.layer0(damagedModelLoc),
                this.modelOutput
        );

        // === Proprietà numerica “damage” ===
        RangeSelectItemModelProperty damageProperty = new net.minecraft.client.renderer.item.properties.numeric.Damage(true);

        // === Crea le Entry per i diversi stati ===
        List<RangeSelectItemModel.Entry> entries = List.of(
                new RangeSelectItemModel.Entry(
                        0.0f, // da 0 fino alla soglia
                        new BlockModelWrapper.Unbaked(normalModelLoc, List.of())
                ),
                new RangeSelectItemModel.Entry(
                        damageThresholdNormalized, // da qui in poi: danneggiato
                        new BlockModelWrapper.Unbaked(damagedModelLoc, List.of())
                )
        );

        // === Crea il modello “range_dispatch” ===
        ItemModel.Unbaked dispatchModel = new RangeSelectItemModel.Unbaked(
                damageProperty, // proprietà da monitorare
                1.0f,           // scala di normalizzazione (0.0–1.0)
                entries,        // entry dei modelli
                Optional.of(new BlockModelWrapper.Unbaked(normalModelLoc, List.of())) // fallback
        );

        // === Registra il modello generato per l'item ===
        this.itemModelOutput.accept(item, dispatchModel);
    }

    protected void generateBow(Item pBowItem) {
        ResourceLocation itemLocation = BuiltInRegistries.ITEM.getKey(pBowItem);
        ResourceLocation modelLocation = ResourceLocation.fromNamespaceAndPath(
                itemLocation.getNamespace(),
                "item/" + itemLocation.getPath()
        );

        // 1. Crea il file del MODELLO in models/item/ender_bow.json
        ModelTemplates.BOW.create(
                modelLocation,
                TextureMapping.layer0(pBowItem),
                this.modelOutput
        );

        // 2. Genera i modelli di caricamento
        ItemModel.Unbaked itemmodel$unbaked = ItemModelUtils.plainModel(modelLocation);
        ItemModel.Unbaked itemmodel$unbaked1 = ItemModelUtils.plainModel(this.createFlatItemModel(pBowItem, "_pulling_0", ModelTemplates.BOW));
        ItemModel.Unbaked itemmodel$unbaked2 = ItemModelUtils.plainModel(this.createFlatItemModel(pBowItem, "_pulling_1", ModelTemplates.BOW));
        ItemModel.Unbaked itemmodel$unbaked3 = ItemModelUtils.plainModel(this.createFlatItemModel(pBowItem, "_pulling_2", ModelTemplates.BOW));

        // 3. Crea il file dell'ITEM in items/ender_bow.json con le condizioni
        this.itemModelOutput.accept(
                pBowItem,
                ItemModelUtils.conditional(
                        ItemModelUtils.isUsingItem(),
                        ItemModelUtils.rangeSelect(
                                new UseDuration(false),
                                0.05F,
                                itemmodel$unbaked1,
                                ItemModelUtils.override(itemmodel$unbaked2, 0.65F),
                                ItemModelUtils.override(itemmodel$unbaked3, 0.9F)
                        ),
                        itemmodel$unbaked
                )
        );
    }




}