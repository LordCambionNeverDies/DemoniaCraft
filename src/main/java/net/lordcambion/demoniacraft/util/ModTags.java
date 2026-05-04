package net.lordcambion.demoniacraft.util;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import javax.swing.text.html.HTML;

import static net.minecraft.tags.TagEntry.tag;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> NEEDS_ARKADIUM_TOOL =createTag("needs_arkadium_tool");
        public static final TagKey<Block> INCORRECT_FOR_ARKADIUM_TOOL =createTag("incorrect_for_arkadium_tool");

        public static final TagKey<Block> NOT_STICKY =createTag("not_sticky");

        public static final TagKey<Block> PORTAL_FRAME_BLOCKS
                = createTag("portal_frame_blocks");


        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID,name));
        }

    }

    public static class Items{
        //public static final TagKey<Item> TRANSFORMABLE_ITEMS =createTag("transformable_items");
        public static final TagKey<Item> ENCHANTABLE_HAMMER =createTag("enchantable_hammer");
        public static final TagKey<Item> ENCHANTABLE_SCYTHE =createTag("enchantable_scythe");
        public static final TagKey<Item> ARKADIUM_REPAIR =createTag("arkadium_repair");
        public static final TagKey<Item> DAMAGED =createTag("damaged");
        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID,name));
        }
    }
}
