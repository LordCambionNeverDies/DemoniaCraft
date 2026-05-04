package net.lordcambion.demoniacraft.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SlimeyEffect extends MobEffect {
    // Mappa per tracciare se l'entità era in aria nel tick precedente
    private static final Map<UUID, Boolean> wasInAir = new HashMap<>();
    // Mappa per tracciare la velocità di caduta al momento dell'impatto
    private static final Map<UUID, Double> lastVelocityY = new HashMap<>();

    public SlimeyEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        Vec3 motion = entity.getDeltaMovement();
        UUID entityId = entity.getUUID();

        // Controlla se era in aria nel tick precedente
        boolean wasAirborne = wasInAir.getOrDefault(entityId, false);

        // Salva la velocità verticale quando è in aria e sta cadendo
        if (!entity.onGround() && motion.y < 0) {
            lastVelocityY.put(entityId, motion.y);
        }

        // Effetto rimbalzo: se era in aria e ora è a terra
        if (wasAirborne && entity.onGround()) {
            // Usa la velocità di caduta salvata per calcolare il rimbalzo
            double lastVelY = lastVelocityY.getOrDefault(entityId, 0.0D);

            // Rimbalza solo se la velocità è significativa (maggiore di 0.3 in valore assoluto)
            if (lastVelY < -0.3D) {
                // Rimbalza con l'80% della velocità di impatto
                double bounceStrength = Math.abs(lastVelY) * 0.8D + (0.05D * amplifier);

                // Se il rimbalzo risultante è troppo debole, non rimbalzare
                if (bounceStrength > 0.25D) {
                    entity.setDeltaMovement(motion.x, bounceStrength, motion.z);
                    entity.fallDistance = 0.0F;
                    entity.hurtMarked = true;
                }

                // IMPORTANTE: resetta la velocità salvata per evitare accumulo
                lastVelocityY.remove(entityId);
            }
        }

        // Aggiorna lo stato per il prossimo tick
        wasInAir.put(entityId, !entity.onGround());



        return super.applyEffectTick(level, entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}