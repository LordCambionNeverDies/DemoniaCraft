package net.lordcambion.demoniacraft.entity.ai;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class ScytheMeleeAttackGoal extends Goal {
    protected final PathfinderMob mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private long lastCanUseCheck;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;

    // Range aumentato per la scythe (2 blocchi di base + 2 della scythe = 4 totali)
    private static final double ATTACK_REACH_SQR = 16.0D; // 4 blocchi al quadrato
    private static final double MIN_DISTANCE_SQR = 9.0D; // Mantieni almeno 3 blocchi di distanza

    public ScytheMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        this.mob = pMob;
        this.speedModifier = pSpeedModifier;
        this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        long i = this.mob.level().getGameTime();
        if (i - this.lastCanUseCheck < 20L) {
            return false;
        } else {
            this.lastCanUseCheck = i;
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (canPenalize) {
                    if (--this.ticksUntilNextPathRecalculation <= 0) {
                        this.path = this.mob.getNavigation().createPath(livingentity, 0);
                        this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                        return this.path != null;
                    } else {
                        return true;
                    }
                }
                this.path = this.mob.getNavigation().createPath(livingentity, 0);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                }
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.mob.getNavigation().isDone();
        } else if (!this.mob.isWithinMeleeAttackRange(livingentity)) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.speedModifier);
        this.mob.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
        LivingEntity livingentity = this.mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.mob.setTarget(null);
        }

        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return;
        }

        this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        double distanceSqr = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());

        this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);

        // Se il mob è troppo vicino, allontanati
        if (distanceSqr < MIN_DISTANCE_SQR) {
            // Calcola direzione opposta al target
            double dx = this.mob.getX() - target.getX();
            double dz = this.mob.getZ() - target.getZ();
            double length = Math.sqrt(dx * dx + dz * dz);

            if (length > 0) {
                dx /= length;
                dz /= length;

                // Muoviti nella direzione opposta
                double retreatX = this.mob.getX() + dx * 2;
                double retreatZ = this.mob.getZ() + dz * 2;

                this.mob.getNavigation().moveTo(retreatX, this.mob.getY(), retreatZ, this.speedModifier * 1.2);
            }
        }
        // Se è nel range di attacco, fermati e attacca
        else if (distanceSqr <= ATTACK_REACH_SQR) {
            this.mob.getNavigation().stop();
            this.checkAndPerformAttack(target);
        }
        // Se è troppo lontano, avvicinati
        else {
            if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(target))
                    && this.ticksUntilNextPathRecalculation <= 0
                    && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D
                    || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D
                    || this.mob.getRandom().nextFloat() < 0.05F)) {

                this.pathedTargetX = target.getX();
                this.pathedTargetY = target.getY();
                this.pathedTargetZ = target.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);

                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                    if (this.mob.getNavigation().getPath() != null) {
                        net.minecraft.world.level.pathfinder.Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
                        if (finalPathPoint != null && target.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                            failedPathFindingPenalty = 0;
                        else
                            failedPathFindingPenalty += 10;
                    } else {
                        failedPathFindingPenalty += 10;
                    }
                }

                if (distanceSqr > 1024.0D) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (distanceSqr > 256.0D) {
                    this.ticksUntilNextPathRecalculation += 5;
                }

                if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }
        }
    }

    protected void checkAndPerformAttack(LivingEntity pEnemy) {
        if (this.canPerformAttack(pEnemy) && this.isFacingTarget(pEnemy)) {
            this.resetAttackCooldown();
            this.mob.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(this.mob.level() instanceof net.minecraft.server.level.ServerLevel serverLevel ? serverLevel : null, pEnemy);
        }
    }

    // Controlla se il mob sta guardando il target (entro 60 gradi)
    protected boolean isFacingTarget(LivingEntity target) {
        // Calcola l'angolo verso il target
        double dx = target.getX() - this.mob.getX();
        double dz = target.getZ() - this.mob.getZ();
        double angleToTarget = Math.toDegrees(Math.atan2(dz, dx)) - 90.0;

        // Normalizza l'angolo tra -180 e 180
        while (angleToTarget < -180.0) angleToTarget += 360.0;
        while (angleToTarget >= 180.0) angleToTarget -= 360.0;

        // Ottieni la rotazione corrente del mob
        float mobYaw = this.mob.getYRot();
        while (mobYaw < -180.0F) mobYaw += 360.0F;
        while (mobYaw >= 180.0F) mobYaw -= 360.0F;

        // Calcola la differenza
        double angleDiff = Math.abs(angleToTarget - mobYaw);
        if (angleDiff > 180.0) angleDiff = 360.0 - angleDiff;

        // Permetti attacco se sta guardando entro 60 gradi (30 gradi per lato)
        return angleDiff <= 60.0;
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(20);
    }

    protected boolean canPerformAttack(LivingEntity pEnemy) {
        return this.ticksUntilNextAttack <= 0;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return ATTACK_REACH_SQR;
    }
}