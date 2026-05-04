package net.lordcambion.demoniacraft.item.custom.Staff;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ShockwaveStaff extends Item {

    private static final int WAVE_RADIUS = 10;

    public ShockwaveStaff(Properties properties) {
        super(properties.stacksTo(1).durability(250).rarity(Rarity.EPIC).useCooldown(100));
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (!level.isClientSide && player != null) {
            if (castShockwave(level, player)) {
                // Aggiungi il cooldown manualmente
                player.getCooldowns().addCooldown(new ItemStack(this),100);
                if (!player.getAbilities().instabuild) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    if (stack.getDamageValue() >= stack.getMaxDamage()) {
                        stack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        Level level = player.level();
        if (!level.isClientSide) {
            if (castShockwave(level, player)) {
                // Aggiungi il cooldown manualmente
                player.getCooldowns().addCooldown(new ItemStack(this),100);
                if (!player.getAbilities().instabuild) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    if (stack.getDamageValue() >= stack.getMaxDamage()) {
                        stack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private boolean castShockwave(Level level, Player player) {
        BlockPos playerPos = player.blockPosition();
        AABB area = new AABB(playerPos).inflate(WAVE_RADIUS);

        // Effetti visivi e sonori sempre attivi
        level.playSound(null, playerPos, SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 0.7F, 1.0F);
        spawnWaveParticles(level, playerPos);

        // Trova tutti i mob nell'area che hanno il player come target
        List<Mob> hostileMobs = level.getEntitiesOfClass(Mob.class, area,
                entity -> entity.isAlive() && entity.getTarget() == player);

        // Spingi tutti i mob lontano dal player (se ce ne sono)
        for (Mob mob : hostileMobs) {
            Vec3 awayVector = new Vec3(mob.getX() - player.getX(), 0, mob.getZ() - player.getZ());
            double distance = awayVector.length();

            if (distance > 0) {
                awayVector = awayVector.normalize();
                // Spinta più forte con altezza
                mob.setDeltaMovement(awayVector.x * 1.0, 0.8, awayVector.z * 1.0);
            } else {
                // Se sono nella stessa posizione, spingi in una direzione casuale
                awayVector = new Vec3(level.random.nextDouble() - 0.5, 0, level.random.nextDouble() - 0.5).normalize();
                mob.setDeltaMovement(awayVector.x * 1.0, 0.8, awayVector.z * 1.0);
            }

            // Rimuovi il target e l'aggro
            mob.setTarget(null);
            mob.setLastHurtByMob(null);
        }

        return true;
    }

    private void spawnWaveParticles(Level level, BlockPos center) {
        if (level instanceof ServerLevel serverLevel) {
            // Crea un'onda di particelle concentriche
            for (int i = 0; i < 4; i++) {
                double radius = (i + 1) * 1.5;
                int particles = 25 * (i + 1);

                for (int j = 0; j < particles; j++) {
                    double angle = 2 * Math.PI * j / particles;
                    double x = center.getX() + 0.5 + radius * Math.cos(angle);
                    double y = center.getY() + 1.0;
                    double z = center.getZ() + 0.5 + radius * Math.sin(angle);

                    serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                            x, y, z,
                            1, 0, 0.05, 0, 0);
                }
            }

            // Particelle esplosive al centro
            serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH,
                    center.getX() + 0.5, center.getY() + 1.0, center.getZ() + 0.5,
                    15, 1.5, 0.5, 1.5, 0.1);

            // Aggiungi particelle di fumo per l'effetto onda d'urto
            for (int i = 0; i < 8; i++) {
                double radius = 0.5 + i * 0.8;
                int smokeParticles = 12;

                for (int j = 0; j < smokeParticles; j++) {
                    double angle = 2 * Math.PI * j / smokeParticles;
                    double x = center.getX() + 0.5 + radius * Math.cos(angle);
                    double y = center.getY() + 0.8;
                    double z = center.getZ() + 0.5 + radius * Math.sin(angle);

                    serverLevel.sendParticles(ParticleTypes.SMOKE,
                            x, y, z,
                            1, 0, 0.02, 0, 0);
                }
            }
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - (float)stack.getDamageValue() * 13.0F / (float)stack.getMaxDamage());
    }

    @Override
    public int getBarColor(ItemStack stack) {
        float f = Math.max(0.0F, ((float)stack.getMaxDamage() - (float)stack.getDamageValue()) / (float)stack.getMaxDamage());
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }
}