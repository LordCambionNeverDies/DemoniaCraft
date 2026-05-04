package net.lordcambion.demoniacraft.event;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.entity.ModEntities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Demoniacraft.MOD_ID)
public class UndeadStaffHandler {

    private static final int ATTACK_RADIUS = 20;
    private static final int SEARCH_RADIUS = 50;
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) return;

        if (hasUndeadStaffEffect(mob)) {
            LivingEntity newTarget = event.getNewTarget();

            if (newTarget instanceof Player) {
                event.setNewTarget(null);
                return;
            }

            if (newTarget instanceof Mob targetMob && hasUndeadStaffEffect(targetMob)) {
                event.setNewTarget(null);
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent.Post event) {
        tickCounter++;
        if (tickCounter % 10 != 0) {
            return;
        }

        event.getServer().getAllLevels().forEach(level -> {
            long currentTime = level.getGameTime();
            List<ServerPlayer> players = level.players();

            for (Player player : players) {
                AABB playerArea = new AABB(player.blockPosition()).inflate(SEARCH_RADIUS);

                List<Mob> affectedMobs = level.getEntitiesOfClass(Mob.class, playerArea,
                        mob -> hasUndeadStaffEffect(mob));

                for (Mob affectedMob : affectedMobs) {
                    try {
                        long expireTime = affectedMob.getPersistentData().get("UndeadStaffEffect").asLong().orElse(0L);

                        if (expireTime == 0L) {
                            affectedMob.getPersistentData().remove("UndeadStaffEffect");
                            continue;
                        }

                        if (currentTime >= expireTime) {
                            affectedMob.getPersistentData().remove("UndeadStaffEffect");
                            affectedMob.setTarget(null);
                            continue;
                        }

                        LivingEntity currentTarget = affectedMob.getTarget();

                        if (currentTarget == null || !currentTarget.isAlive()) {
                            findAndSetTarget(affectedMob, level);
                        } else if (currentTarget instanceof Player ||
                                (currentTarget instanceof Mob targetMob && hasUndeadStaffEffect(targetMob))) {
                            findAndSetTarget(affectedMob, level);
                        }
                    } catch (Exception e) {
                        affectedMob.getPersistentData().remove("UndeadStaffEffect");
                    }
                }
            }
        });
    }

    private static void findAndSetTarget(Mob affectedMob, Level level) {
        AABB searchArea = new AABB(affectedMob.blockPosition()).inflate(ATTACK_RADIUS);

        List<Mob> potentialTargets = level.getEntitiesOfClass(Mob.class, searchArea,
                target -> target != affectedMob &&
                        target.isAlive() &&
                        !hasUndeadStaffEffect(target));

        if (!potentialTargets.isEmpty()) {
            Mob nearest = potentialTargets.stream()
                    .min((a, b) -> Double.compare(
                            affectedMob.distanceToSqr(a),
                            affectedMob.distanceToSqr(b)
                    ))
                    .orElse(null);

            if (nearest != null) {
                affectedMob.setTarget(nearest);
            }
        }
    }

    private static boolean hasUndeadStaffEffect(Mob mob) {
        if (!mob.getPersistentData().contains("UndeadStaffEffect")) {
            return false;
        }

        try {
            long expireTime = mob.getPersistentData().get("UndeadStaffEffect").asLong().orElse(0L);
            if (expireTime == 0L) return false;

            long currentTime = mob.level().getGameTime();
            return currentTime < expireTime;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isUndead(LivingEntity entity) {
        return entity.getType() == EntityType.ZOMBIE ||
                entity.getType() == EntityType.SKELETON ||
                entity.getType() == EntityType.WITHER_SKELETON ||
                entity.getType() == EntityType.ZOMBIE_VILLAGER ||
                entity.getType() == EntityType.ZOMBIFIED_PIGLIN ||
                entity.getType() == EntityType.HUSK ||
                entity.getType() == EntityType.STRAY ||
                entity.getType() == EntityType.PHANTOM ||
                entity.getType() == EntityType.WITHER ||
                entity.getType() == EntityType.ZOGLIN ||
                entity.getType() == ModEntities.BONEREAPER.get() ||
                entity.getType() == EntityType.DROWNED;
    }
}