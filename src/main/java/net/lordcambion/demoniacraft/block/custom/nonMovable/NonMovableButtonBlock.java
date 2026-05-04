package net.lordcambion.demoniacraft.block.custom.nonMovable;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;

public class NonMovableButtonBlock extends ButtonBlock {
    public NonMovableButtonBlock(BlockSetType type, int ticksToStayPressed, Properties properties) {
        super(type, ticksToStayPressed, properties);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY; // Si droppa quando spinto
    }
}