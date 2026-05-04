package net.lordcambion.demoniacraft.datagen;


import net.lordcambion.demoniacraft.block.ModBlocks;
import net.lordcambion.demoniacraft.item.ModItems;
import net.lordcambion.demoniacraft.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VanillaItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.datafix.fixes.ItemCustomNameToComponentFix;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends VanillaItemTagsProvider {
    public ModItemTagProvider(PackOutput output,
                              CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags,
                              @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Tag per gli enchantment di mining (Fortune, Efficiency, etc.)
        tag(ItemTags.MINING_ENCHANTABLE)
                .add(ModItems.ARKADIUM_PICKAXE.get())
                .add(ModItems.ARKADIUM_AXE.get())
                .add(ModItems.ARKADIUM_SHOVEL.get())
                .add(ModItems.ARKADIUM_HAMMER.get())
                .add(ModItems.GOLDEN_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get())
                .add(ModItems.DIAMOND_HAMMER.get())
                .add(ModItems.NETHERITE_HAMMER.get());

        tag(ItemTags.ARMOR_ENCHANTABLE)
                .add(ModItems.ARKADIUM_HELMET.get())
                .add(ModItems.ARKADIUM_CHESTPLATE.get())
                .add(ModItems.ARKADIUM_LEGGINGS.get())
                .add(ModItems.ARKADIUM_BOOTS.get())
                .add(ModItems.ENDER_HELMET.get())
                .add(ModItems.ENDER_CHESTPLATE.get())
                .add(ModItems.ENDER_LEGGINGS.get())
                .add(ModItems.ENDER_BOOTS.get());

        tag(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(ModItems.ARKADIUM_HELMET.get())
                .add(ModItems.ENDER_HELMET.get());

        tag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(ModItems.ARKADIUM_CHESTPLATE.get())
                .add(ModItems.ENDER_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(ModItems.ARKADIUM_LEGGINGS.get())
                .add(ModItems.ENDER_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(ModItems.ARKADIUM_BOOTS.get());

        // Tag per gli enchantment di durability (Unbreaking, Mending)
        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.ARKADIUM_PICKAXE.get())
                .add(ModItems.ARKADIUM_AXE.get())
                .add(ModItems.ARKADIUM_SHOVEL.get())
                .add(ModItems.ARKADIUM_HOE.get())
                .add(ModItems.ARKADIUM_SWORD.get())

                .add(ModItems.ARKADIUM_HAMMER.get())
                .add(ModItems.GOLDEN_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get())
                .add(ModItems.DIAMOND_HAMMER.get())
                .add(ModItems.NETHERITE_HAMMER.get())

                .add(ModItems.ARKADIUM_HELMET.get())
                .add(ModItems.ARKADIUM_CHESTPLATE.get())
                .add(ModItems.ARKADIUM_LEGGINGS.get())
                .add(ModItems.ARKADIUM_BOOTS.get())
                .add(ModItems.ENDER_HELMET.get())
                .add(ModItems.ENDER_CHESTPLATE.get())
                .add(ModItems.ENDER_LEGGINGS.get())
                .add(ModItems.ENDER_BOOTS.get());

        // Tag per gli enchantment di weapon (Sharpness, Looting, etc.)
        tag(ItemTags.WEAPON_ENCHANTABLE)
                .add(ModItems.ARKADIUM_SWORD.get())
                .add(ModItems.DEMON_SCYTHE.get())
                .add(ModItems.ARKADIUM_AXE.get());

        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.ARKADIUM_PICKAXE.get())
                .add(ModItems.ARKADIUM_AXE.get())
                .add(ModItems.ARKADIUM_SHOVEL.get())
                .add(ModItems.ARKADIUM_HOE.get())
                .add(ModItems.ARKADIUM_SWORD.get())
                .add(ModItems.DEMON_SCYTHE.get())


                .add(ModItems.ARKADIUM_HAMMER.get())
                .add(ModItems.GOLDEN_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get())
                .add(ModItems.DIAMOND_HAMMER.get())
                .add(ModItems.NETHERITE_HAMMER.get())

                .add(ModItems.ARKADIUM_HELMET.get())
                .add(ModItems.ARKADIUM_CHESTPLATE.get())
                .add(ModItems.ARKADIUM_LEGGINGS.get())
                .add(ModItems.ARKADIUM_BOOTS.get())
                .add(ModItems.ENDER_HELMET.get())
                .add(ModItems.ENDER_CHESTPLATE.get())
                .add(ModItems.ENDER_LEGGINGS.get())
                .add(ModItems.ENDER_BOOTS.get());


        // Tag per Vanishing Curse
        tag(ItemTags.VANISHING_ENCHANTABLE)
                .add(ModItems.ARKADIUM_PICKAXE.get())
                .add(ModItems.ARKADIUM_AXE.get())
                .add(ModItems.ARKADIUM_SHOVEL.get())
                .add(ModItems.ARKADIUM_HOE.get())
                .add(ModItems.ARKADIUM_SWORD.get())
                .add(ModItems.DEMON_SCYTHE.get())


                .add(ModItems.ARKADIUM_HAMMER.get())
                .add(ModItems.GOLDEN_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get())
                .add(ModItems.DIAMOND_HAMMER.get())
                .add(ModItems.NETHERITE_HAMMER.get())

                .add(ModItems.ARKADIUM_HELMET.get())
                .add(ModItems.ARKADIUM_CHESTPLATE.get())
                .add(ModItems.ARKADIUM_LEGGINGS.get())
                .add(ModItems.ARKADIUM_BOOTS.get())
                .add(ModItems.ENDER_HELMET.get())
                .add(ModItems.ENDER_CHESTPLATE.get())
                .add(ModItems.ENDER_LEGGINGS.get())
                .add(ModItems.ENDER_BOOTS.get());

        // Tag specifici per tipo di tool
        tag(ItemTags.AXES)
                .add(ModItems.ARKADIUM_AXE.get());

        tag(ItemTags.PICKAXES)
                .add(ModItems.ARKADIUM_PICKAXE.get())
                .add(ModItems.ARKADIUM_HAMMER.get())
                .add(ModItems.GOLDEN_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get())
                .add(ModItems.DIAMOND_HAMMER.get())
                .add(ModItems.NETHERITE_HAMMER.get());

        tag(ItemTags.SHOVELS)
                .add(ModItems.ARKADIUM_SHOVEL.get());

        tag(ItemTags.HOES)
                .add(ModItems.ARKADIUM_HOE.get());

        tag(ItemTags.SWORDS)
                .add(ModItems.ARKADIUM_SWORD.get());


        tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.ARKADIUM_HELMET.get())
                .add(ModItems.ENDER_HELMET.get());
        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.ARKADIUM_CHESTPLATE.get())
                .add(ModItems.ENDER_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.ARKADIUM_LEGGINGS.get())
                .add(ModItems.ENDER_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.ARKADIUM_BOOTS.get())
                .add(ModItems.ENDER_BOOTS.get());;

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.ARKADIUM_HELMET.get())
                .add(ModItems.ARKADIUM_CHESTPLATE.get())
                .add(ModItems.ARKADIUM_LEGGINGS.get())
                .add(ModItems.ARKADIUM_BOOTS.get())
                .add(ModItems.ENDER_HELMET.get())
                .add(ModItems.ENDER_CHESTPLATE.get())
                .add(ModItems.ENDER_LEGGINGS.get())
                .add(ModItems.ENDER_BOOTS.get());

        tag(ModTags.Items.ENCHANTABLE_HAMMER)
                .add(ModItems.ARKADIUM_HAMMER.get())
                .add(ModItems.GOLDEN_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get())
                .add(ModItems.DIAMOND_HAMMER.get())
                .add(ModItems.NETHERITE_HAMMER.get());

        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.WALNUT_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_WALNUT_LOG.get().asItem())
                .add(ModBlocks.WALNUT_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_WALNUT_WOOD.get().asItem());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.WALNUT_PLANKS.get().asItem());

        tag(ModTags.Items.ENCHANTABLE_SCYTHE)
                .add(ModItems.DEMON_SCYTHE.get());


        tag(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(ModItems.CRYSIS_DISC.get())
                .add(ModItems.DIAMONDS_DISC.get())
                .add(ModItems.PANICDOX_DISC.get())
                .add(ModItems.DYNAMITE_DISC.get());

        tag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                .add(ModItems.TOMATO_SEEDS.get());

        tag(ItemTags.VILLAGER_PICKS_UP)
                .add(ModItems.TOMATO_SEEDS.get())
            .add(ModItems.TOMATO.get());

    }
}