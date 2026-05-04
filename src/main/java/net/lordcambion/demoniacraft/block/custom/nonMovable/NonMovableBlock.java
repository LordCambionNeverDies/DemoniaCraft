package net.lordcambion.demoniacraft.block.custom.nonMovable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public abstract class NonMovableBlock extends Block {
    public NonMovableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }
}