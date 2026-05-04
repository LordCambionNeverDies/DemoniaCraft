package net.lordcambion.demoniacraft.block.custom;

import com.mojang.serialization.MapCodec;
import net.lordcambion.demoniacraft.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class TomatoCropBlock extends CropBlock {

    public static final MapCodec<CropBlock> CODEC = simpleCodec(CropBlock::new);
    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE = IntegerProperty.create("age",0,5);

//    private static final VoxelShape[] SHAPES_BY_AGE = new VoxelShape[]{
//            Block.box(0.0,0.0,0.0,16.0,2.0,16.0),
//            Block.box(0.0,0.0,0.0,16.0,2.0,16.0),
//            Block.box(0.0,0.0,0.0,16.0,2.0,16.0),
//            Block.box(0.0,0.0,0.0,16.0,2.0,16.0),
//            Block.box(0.0,0.0,0.0,16.0,2.0,16.0),
//            Block.box(0.0,0.0,0.0,16.0,2.0,16.0),
//            Block.box(0.0,0.0,0.0,16.0,2.0,16.0),
//
//    };

    public TomatoCropBlock(Properties properties){
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.TOMATO_SEEDS.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
