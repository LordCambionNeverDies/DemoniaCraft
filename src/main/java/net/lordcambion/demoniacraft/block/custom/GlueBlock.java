package net.lordcambion.demoniacraft.block.custom;

import net.lordcambion.demoniacraft.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GlueBlock extends HoneyBlock {

    public GlueBlock(Properties properties) {
        super(properties);

    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        Block otherBlock = other.getBlock();

        // Il GlueBlock non si attacca a bottoni e pressure plate
        if (otherBlock instanceof ButtonBlock ||
                otherBlock instanceof net.minecraft.world.level.block.BasePressurePlateBlock
                        ||otherBlock.defaultBlockState().is(ModTags.Blocks.NOT_STICKY)) {
            return false;
        }

        // Comportamento normale per gli altri blocchi
        return super.canStickTo(state, other);
    }


    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        // Copia della logica di HoneyBlock senza particelle
        entity.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
        if (entity.causeFallDamage(fallDistance, 0.2F, level.damageSources().fall())) {
            entity.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier applier) {
        // Applica solo un leggero rallentamento quando l’entità è sopra il blocco
        if (!(level.getBlockEntity(pos) instanceof net.minecraft.world.level.block.piston.PistonMovingBlockEntity)) {
            entity.makeStuckInBlock(state, new Vec3(0.2, 0.02, 0.2));
        }

        super.entityInside(state, level, pos, entity, applier);
    }




}
