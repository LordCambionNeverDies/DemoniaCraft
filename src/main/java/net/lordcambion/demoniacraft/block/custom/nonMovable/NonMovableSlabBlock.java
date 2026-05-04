package net.lordcambion.demoniacraft.block.custom.nonMovable;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class NonMovableSlabBlock extends SlabBlock {
    public NonMovableSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }
}
