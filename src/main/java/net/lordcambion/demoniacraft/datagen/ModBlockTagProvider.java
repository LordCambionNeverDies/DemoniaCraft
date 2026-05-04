package net.lordcambion.demoniacraft.datagen;

import net.lordcambion.demoniacraft.Demoniacraft;

import net.lordcambion.demoniacraft.block.ModBlocks;
import net.lordcambion.demoniacraft.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Demoniacraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.PYRESTONE_ORE.get())
                .add(ModBlocks.DARK_STONE_BLOCK.get())
                .add(ModBlocks.DARK_COBBLESTONE_BLOCK.get())
                .add(ModBlocks.ARKADIUM_ORE.get())
                .add(ModBlocks.ARKADIUM_BLOCK.get())
                .add(ModBlocks.ARKADIUM_END_ORE.get())
                .add(ModBlocks.ARKADIUM_NETHER_ORE.get())
                .add(ModBlocks.ARKADIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.OBSIDIAN_PRESSURE_PLATE.get())
                .add(ModBlocks.OBSIDIAN_BUTTON.get())
                .add(ModBlocks.OBSIDIAN_STAIRS.get())
                .add(ModBlocks.OBSIDIAN_SLAB.get())
                .add(ModBlocks.OBSIDIAN_WALL.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.PYRESTONE_ORE.get())
                .add(ModBlocks.ARKADIUM_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ARKADIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.ARKADIUM_END_ORE.get())
                .add(ModBlocks.ARKADIUM_NETHER_ORE.get())
                .add(ModBlocks.OBSIDIAN_PRESSURE_PLATE.get())
                .add(ModBlocks.OBSIDIAN_BUTTON.get())
                .add(ModBlocks.OBSIDIAN_STAIRS.get())
                .add(ModBlocks.OBSIDIAN_SLAB.get())
                .add(ModBlocks.OBSIDIAN_WALL.get());


        tag(BlockTags.FENCES)
                .add(ModBlocks.WALNUT_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.WALNUT_FENCE_GATE.get());
        tag(BlockTags.WALLS)
                .add(ModBlocks.OBSIDIAN_WALL.get());
        tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.WALNUT_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.WALNUT_TRAPDOOR.get());

        tag(ModTags.Blocks.NOT_STICKY)
                .add(ModBlocks.OBSIDIAN_PRESSURE_PLATE.get())
                .add(ModBlocks.OBSIDIAN_BUTTON.get())
                .add(ModBlocks.OBSIDIAN_STAIRS.get())
                .add(ModBlocks.OBSIDIAN_SLAB.get())
                .add(ModBlocks.OBSIDIAN_WALL.get());

        tag(ModTags.Blocks.NEEDS_ARKADIUM_TOOL)
                .add(Blocks.BEDROCK)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_ARKADIUM_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .remove(ModTags.Blocks.NEEDS_ARKADIUM_TOOL);

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.WALNUT_LOG.get())
                .add(ModBlocks.STRIPPED_WALNUT_LOG.get())
                .add(ModBlocks.WALNUT_WOOD.get())
                .add(ModBlocks.STRIPPED_WALNUT_WOOD.get());

    }
}