package net.lordcambion.demoniacraft.block.custom.nonMovable;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;


public class NonMovableStairBlock extends StairBlock {
    public NonMovableStairBlock(BlockState baseBlockState, Properties properties) {
        super(baseBlockState, properties);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }
}