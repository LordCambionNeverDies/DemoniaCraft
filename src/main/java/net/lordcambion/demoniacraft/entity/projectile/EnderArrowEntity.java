package net.lordcambion.demoniacraft.entity.projectile;

import net.lordcambion.demoniacraft.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;

public class EnderArrowEntity extends Arrow {

    public EnderArrowEntity(EntityType<? extends EnderArrowEntity> entityType, Level level) {
        super(entityType, level);
    }

    public EnderArrowEntity(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(level, x, y, z, pickupItemStack, firedFromWeapon);
    }

    public EnderArrowEntity(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(level, owner, pickupItemStack, firedFromWeapon);
    }

    @Override
    public void tick() {
        super.tick();

        // Aggiungi particelle di enderman durante il volo
        if (this.level().isClientSide && !this.isInGround()) {
            for (int i = 0; i < 2; i++) {
                this.level().addParticle(
                        ParticleTypes.PORTAL,
                        this.getRandomX(0.5),
                        this.getRandomY(),
                        this.getRandomZ(0.5),
                        (this.random.nextDouble() - 0.5) * 0.1,
                        (this.random.nextDouble() - 0.5) * 0.1,
                        (this.random.nextDouble() - 0.5) * 0.1
                );
            }
        }
    }


    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        if (!this.level().isClientSide && pResult.getEntity() instanceof LivingEntity target) {
            // Teletrasporta il nemico in una posizione random
            teleportEntityRandomly(target);
        }
    }


    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        if (!this.level().isClientSide && this.getOwner() instanceof LivingEntity shooter) {
            // Teletrasporta il tiratore alla posizione della freccia
            teleportEntityToArrow(shooter);
            this.discard(); // Rimuovi la freccia dopo il teletrasporto
        }
    }
//    private void teleportEntityRandomly(LivingEntity entity) {
//        if (entity.level() instanceof ServerLevel serverLevel) {
//            double range = 8.0D; // Range del teletrasporto
//            double x = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * range * 2;
//            double y = entity.getY() + (entity.getRandom().nextInt(16) - 8);
//            double z = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * range * 2;
//
//            // Effetti sonori e particelle
//            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
//                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
//
//            entity.teleportTo(x, y, z);
//
//            entity.level().playSound(null, x, y, z,
//                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
//        }
//    }

    private void teleportEntityRandomly(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            double range = 8.0D; // Range del teletrasporto
            double randomX = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * range * 2;
            double randomZ = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * range * 2;

            // Trova la posizione y più alta e sicura in base alle coordinate X e Z casuali.
            // Questo previene il teletrasporto sottoterra.
            double safeY = serverLevel.getHeight(
                    net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING,
                    (int) randomX,
                    (int) randomZ
            );

            // Applica un piccolo offset in Y per assicurarsi che l'entità sia sul blocco e non ci sia collisione immediata.
            double targetY = safeY + 0.1D;

            // Effetti sonori e particelle PRIMA del teletrasporto
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);

            // Teletrasporta alla nuova posizione sicura
            entity.teleportTo(randomX, targetY, randomZ);

            // Effetti sonori DOPO il teletrasporto
            entity.level().playSound(null, randomX, targetY, randomZ,
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
    }


    private void teleportEntityToArrow(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);

            entity.teleportTo(this.getX(), this.getY(), this.getZ());

            entity.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
    }
    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.ENDER_ARROW.get());
    }
}