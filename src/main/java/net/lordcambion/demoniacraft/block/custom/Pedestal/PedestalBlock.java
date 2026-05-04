package net.lordcambion.demoniacraft.block.custom.Pedestal;

import com.mojang.serialization.MapCodec;
import net.lordcambion.demoniacraft.block.custom.Entities.custom.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PedestalBlock extends BaseEntityBlock {
    public  static final VoxelShape SHAPE= Block.box(2,0,2,14,13,14);
    public static final MapCodec<PedestalBlock> CODEC= simpleCodec(PedestalBlock::new);
    public PedestalBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof PedestalBlockEntity pedestalBlockEntity) {
            // Restituisce 5 se c'è un item, 0 se è vuoto
            return pedestalBlockEntity.inventory.getStackInSlot(0).isEmpty() ? 0 : 5;


            // ItemStack stack = pedestalBlockEntity.inventory.getStackInSlot(0);
            // if (stack.isEmpty()) return 0;
            // return (int) Math.ceil((stack.getCount() / (float) stack.getMaxStackSize()) * 15);
        }
        return 0;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PedestalBlockEntity(pPos,pState) ;
    }

    @Override
    public BlockState playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {

        if (!pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PedestalBlockEntity pedestalBlockEntity) {
                pedestalBlockEntity.drops();
            }
        }
        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);

    }

    @Override
    public void wasExploded(ServerLevel pLevel, BlockPos pPos, Explosion pExplosion) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof PedestalBlockEntity pedestalBlockEntity) {
            pedestalBlockEntity.drops();
        }
        super.wasExploded(pLevel, pPos, pExplosion);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if(pLevel.getBlockEntity(pPos) instanceof PedestalBlockEntity pedestalBlockEntity) {
            if(pedestalBlockEntity.inventory.getStackInSlot(0).isEmpty() && !pStack.isEmpty()) {
                pedestalBlockEntity.inventory.insertItem(0, pStack.copy(), false);
                pStack.shrink(1);
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            } else if(pStack.isEmpty()) {
                ItemStack stackOnPedestal = pedestalBlockEntity.inventory.extractItem(0, 1, false);
                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, stackOnPedestal);
                pedestalBlockEntity.clearContents();
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }
        }

        return InteractionResult.SUCCESS;
    }





}
