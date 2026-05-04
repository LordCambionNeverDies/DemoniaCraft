package net.lordcambion.demoniacraft.block.custom.Chair;

import com.mojang.serialization.MapCodec;
import net.lordcambion.demoniacraft.entity.ModEntities;
import net.lordcambion.demoniacraft.entity.custom.ChairEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChairBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<ChairBlock> CODEC = simpleCodec(ChairBlock::new);

    // Definisci le forme per la direzione NORTH (di riferimento)
    private static final VoxelShape BASE_NORTH = Block.box(2.9, 7.0, 2.5, 13.1, 8.0, 13.0);
    private static final VoxelShape LEG_1_NORTH = Block.box(3.0, 0.0, 11.0, 5.0, 7.0, 13.0);
    private static final VoxelShape LEG_2_NORTH = Block.box(11.0, 0.0, 11.0, 13.0, 7.0, 13.0);
    private static final VoxelShape LEG_3_NORTH = Block.box(11.0, 0.0, 3.0, 13.0, 7.0, 5.0);
    private static final VoxelShape LEG_4_NORTH = Block.box(3.0, 0.0, 3.0, 5.0, 7.0, 5.0);
    private static final VoxelShape BACKREST_NORTH = Block.box(3.0, 8.0, 11.5, 13.0, 19.0, 13.0);

    // Forma completa per NORTH
    private static final VoxelShape SHAPE_NORTH = Shapes.or(BASE_NORTH, LEG_1_NORTH, LEG_2_NORTH, LEG_3_NORTH, LEG_4_NORTH, BACKREST_NORTH);

    public ChairBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if(!pLevel.isClientSide()){
            Entity entity=null;
            List<ChairEntity> entities=pLevel.getEntities(ModEntities.CHAIR.get(),new AABB(pPos),chair->true);
            if(entities.isEmpty()){
                entity=ModEntities.CHAIR.get().spawn((ServerLevel) pLevel,pPos, EntitySpawnReason.TRIGGERED);
            }else{
                entity=entities.get(0);
            }
            pPlayer.startRiding(entity);
        }

        return InteractionResult.SUCCESS;
    }



    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);

        // Ruota la forma in base alla direzione
        return rotateShape(direction);
    }

    // Metodo per ruotare le VoxelShape
    private VoxelShape rotateShape(Direction direction) {
        return switch (direction) {
            case SOUTH -> rotateShape(SHAPE_NORTH, Direction.SOUTH);
            case EAST -> rotateShape(SHAPE_NORTH, Direction.EAST);
            case WEST -> rotateShape(SHAPE_NORTH, Direction.WEST);
            default -> SHAPE_NORTH; // NORTH e altri casi
        };
    }

    // Metodo helper per ruotare una VoxelShape
    private VoxelShape rotateShape(VoxelShape shape, Direction direction) {
        if (direction == Direction.NORTH) {
            return shape;
        }

        VoxelShape[] buffer = new VoxelShape[]{ shape, Shapes.empty() };

        // Numero di rotazioni di 90 gradi necessarie
        int rotations = (direction.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;

        for (int i = 0; i < rotations; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                // Ruota di 90 gradi in senso orario
                double newMinX = 1 - maxZ;
                double newMinZ = minX;
                double newMaxX = 1 - minZ;
                double newMaxZ = maxX;

                buffer[1] = Shapes.or(buffer[1],
                        Block.box(newMinX * 16, minY * 16, newMinZ * 16,
                                newMaxX * 16, maxY * 16, newMaxZ * 16));
            });

            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        // La sedia guarda nella direzione opposta al player
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        // Solo se NON è movimento da pistone, rimuovi l'entità
        if (!pLevel.isClientSide()) {
            List<ChairEntity> entities = pLevel.getEntities(ModEntities.CHAIR.get(),
                    new AABB(pPos), chair -> true);

            for (ChairEntity entity : entities) {
                entity.ejectPassengers();
                entity.discard();
            }
        }

        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);

        // Se il blocco è stato spostato da un pistone
        if (pMovedByPiston && !pLevel.isClientSide()) {
            // Cerca l'entità della sedia nelle posizioni adiacenti
            for (Direction direction : Direction.values()) {
                BlockPos oldPos = pPos.relative(direction);
                List<ChairEntity> entities = pLevel.getEntities(ModEntities.CHAIR.get(),
                        new AABB(oldPos), chair -> !chair.getPassengers().isEmpty());

                if (!entities.isEmpty()) {
                    ChairEntity chairEntity = entities.get(0);
                    // Sposta l'entità nella nuova posizione
                    chairEntity.setPos(pPos.getX() + 0.5, pPos.getY(), pPos.getZ() + 0.5);
                    return;
                }
            }
        }
    }

}