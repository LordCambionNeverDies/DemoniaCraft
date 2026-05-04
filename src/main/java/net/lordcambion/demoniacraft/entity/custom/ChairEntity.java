package net.lordcambion.demoniacraft.entity.custom;

import net.lordcambion.demoniacraft.block.custom.Chair.ChairBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class ChairEntity extends Entity {
    public ChairEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {

    }

    @Override
    public boolean hurtServer(ServerLevel pLevel, DamageSource pDamageSource, float pAmount) {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput pInput) {

    }

    @Override
    protected void addAdditionalSaveData(ValueOutput pOutput) {

    }

    private int ticksWithoutChair = 0;

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            BlockPos pos = this.blockPosition();
            BlockState state = this.level().getBlockState(pos);

            if (!(state.getBlock() instanceof ChairBlock)) {
                ticksWithoutChair++;
                // Aspetta fino a 10 tick (~0.5 secondi)
                if (ticksWithoutChair > 10) {
                    this.ejectPassengers();
                    this.discard();
                }
            } else {
                ticksWithoutChair = 0; // reset se torna una sedia
                this.checkAndSyncPosition(pos);
            }

            // Se non ha passeggeri, rimuovila
            if (this.getPassengers().isEmpty()) {
                this.discard();
            }
        }
    }

    private void checkAndSyncPosition(BlockPos pos) {
        double targetX = pos.getX() + 0.5;
        double targetY = pos.getY();
        double targetZ = pos.getZ() + 0.5;

        if (Math.abs(this.getX() - targetX) > 0.1 ||
                Math.abs(this.getY() - targetY) > 0.1 ||
                Math.abs(this.getZ() - targetZ) > 0.1) {
            this.setPos(targetX, targetY, targetZ);
        }
    }

    @Override
    public void positionRider(Entity passenger, MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);

        if (this.hasPassenger(passenger)) {
            // Posiziona il passeggero al centro del blocco della sedia
            BlockPos chairPos = this.blockPosition();
            double x = chairPos.getX() + 0.5;
            double y = chairPos.getY() ;
            double z = chairPos.getZ() + 0.5;

            moveFunction.accept(passenger, x, y, z);
        }
    }

    @Override
    public void removePassenger(Entity passenger) {
        super.removePassenger(passenger);

        if (this.level().isClientSide()) {
            return;
        }

        // Rimuovi l'entità quando il passeggero scende
        this.discard();
    }
}

