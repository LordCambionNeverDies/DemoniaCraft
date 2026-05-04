package net.lordcambion.demoniacraft.block.custom.nonMovable;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;

public class NonMovablePressurePlateBlock extends PressurePlateBlock {
    public NonMovablePressurePlateBlock(BlockSetType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY; // Si droppa quando spinto
    }
}