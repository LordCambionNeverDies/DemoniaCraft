package net.lordcambion.demoniacraft.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClimbingEffect extends MobEffect {
    // Mappa per tracciare se l'entità era in aria nel tick precedente


    public ClimbingEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        Vec3 motion = entity.getDeltaMovement();
        UUID entityId = entity.getUUID();

        // Effetto arrampicata sui muri
        if (entity.horizontalCollision && !entity.onGround()) {
            // aumento base verso l'alto, scala leggermente con l'amplifier
            double climbSpeed = 0.2D + (0.05D * amplifier);

            // se sta già cadendo, annulla la velocità negativa per evitare "scatti" verso il basso
            if (motion.y < 0) {
                motion = new Vec3(motion.x, 0.0D, motion.z);
            }

            // applica movimento: leggero smorzamento orizzontale e impulso verticale stabile
            entity.setDeltaMovement(motion.x * 0.91D, climbSpeed, motion.z * 0.91D);

            // evita danni da caduta e aiuta la sincronizzazione client/server
            entity.fallDistance = 0.0F;
            entity.hurtMarked = true;

            return super.applyEffectTick(level, entity, amplifier);
        }

        return super.applyEffectTick(level, entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}