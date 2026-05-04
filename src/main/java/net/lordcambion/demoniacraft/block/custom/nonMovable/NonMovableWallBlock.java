package net.lordcambion.demoniacraft.block.custom.nonMovable;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class NonMovableWallBlock extends WallBlock {
    public NonMovableWallBlock(Properties properties) {
        super(properties);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }
}