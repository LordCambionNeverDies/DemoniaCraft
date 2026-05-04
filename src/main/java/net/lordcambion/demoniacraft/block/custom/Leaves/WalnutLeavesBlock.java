package net.lordcambion.demoniacraft.block.custom.Leaves;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WalnutLeavesBlock extends LeavesBlock {
    public static final MapCodec<WalnutLeavesBlock> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(propertiesCodec())
                            .apply(instance, WalnutLeavesBlock::new)
            );

    public WalnutLeavesBlock(BlockBehaviour.Properties properties) {
        super(0.05F, properties);
    }

    @Override
    public MapCodec<WalnutLeavesBlock> codec() {
        return CODEC;
    }

    @Override
    protected void spawnFallingLeavesParticle(Level pLevel, BlockPos pPos, RandomSource pRandom) {
        // Implementazione particelle (puoi anche lasciarlo vuoto)
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 30;
    }
}