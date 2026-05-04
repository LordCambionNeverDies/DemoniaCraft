package net.lordcambion.demoniacraft.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class LampBlock extends Block {
    public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public LampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LIT,false)
                .setValue(POWERED, false)
                .setValue(CLICKED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CLICKED,POWERED,LIT);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide() && pLevel instanceof ServerLevel serverlevel) {
            // Stesso comportamento della redstone: toggle dello stato LIT e suono
            BlockState newState = pState.cycle(LIT);
            pLevel.playSound(null, pPos, newState.getValue(LIT) ? SoundEvents.COPPER_BULB_TURN_ON : SoundEvents.COPPER_BULB_TURN_OFF, SoundSource.BLOCKS);
            pLevel.setBlockAndUpdate(pPos, newState);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (pOldState.getBlock() != pState.getBlock() && pLevel instanceof ServerLevel serverlevel) {
            this.checkAndFlip(pState, serverlevel, pPos);
        }
    }

    @Override
    protected void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, @Nullable Orientation pOrientation, boolean pMovedByPiston) {
        if (pLevel instanceof ServerLevel serverlevel) {
            this.checkAndFlip(pState, serverlevel, pPos);
        }
    }

    public void checkAndFlip(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        boolean flag = pLevel.hasNeighborSignal(pPos);
        if (flag != pState.getValue(POWERED)) {
            BlockState blockstate = pState;
            if (!pState.getValue(POWERED)) {
                blockstate = pState.cycle(LIT);
                pLevel.playSound(null, pPos, blockstate.getValue(LIT) ? SoundEvents.COPPER_BULB_TURN_ON : SoundEvents.COPPER_BULB_TURN_OFF, SoundSource.BLOCKS);
            }

            pLevel.setBlock(pPos, blockstate.setValue(POWERED, flag), 3);
        }
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos).getValue(LIT) ? 15 : 0;
    }
}