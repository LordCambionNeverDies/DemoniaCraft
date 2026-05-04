package net.lordcambion.demoniacraft.util;

import net.lordcambion.demoniacraft.entity.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class EntityUtil {
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