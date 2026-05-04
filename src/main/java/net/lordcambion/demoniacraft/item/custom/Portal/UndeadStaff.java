package net.lordcambion.demoniacraft.item.custom.Portal;

import net.lordcambion.demoniacraft.entity.ModEntities;
import net.lordcambion.demoniacraft.worldgen.portal.DemonPortalStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.stream.Collectors;

public class UndeadStaff extends Item {

    private static final int EFFECT_RADIUS = 15;

    public UndeadStaff(Properties properties) {
        super(properties.stacksTo(1).durability(250).rarity(Rarity.EPIC).useCooldown(100));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        Direction facing = context.getHorizontalDirection();
        ItemStack stack = context.getItemInHand();

        if (!level.isClientSide && player != null) {

            // Shift + Click: crea il portale
            if (player.isShiftKeyDown()) {
                if (DemonPortalStructure.tryCreatePortal(level, pos.above(), facing)) {
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

                // Aggiungi il cooldown manualmente
                player.getCooldowns().addCooldown(new ItemStack(this),100);


        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        Level level = player.level();
        if (!level.isClientSide) {
            // Click destro su un'entità: applica l'effetto della staffa
            if (applyUndeadStaffEffect(level, player)) {
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

    private boolean applyUndeadStaffEffect(Level level, Player player) {
        BlockPos playerPos = player.blockPosition();
        AABB area = new AABB(playerPos).inflate(EFFECT_RADIUS);

        // Trova tutti i non morti nell'area
        List<Mob> undeadMobs = level.getEntitiesOfClass(Mob.class, area,
                entity -> isUndead(entity) && entity.isAlive());

        if (undeadMobs.isEmpty()) {
            return false;
        }

        // Effetti visivi e sonori
        level.playSound(null, playerPos, SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 0.7F, 1.0F);
        spawnEffectParticles(level, playerPos);

        long expireTime = level.getGameTime() + 100; // 100 tick = 5 secondi

        for (Mob undead : undeadMobs) {
            // Aggiungiamo un tag al mob con il tempo di scadenza
            undead.getPersistentData().putLong("UndeadStaffEffect", expireTime);
        }

        return true;
    }

    private boolean isUndead(LivingEntity entity) {
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

    private void spawnEffectParticles(Level level, BlockPos center) {
        // Solo sul server, invia i pacchetti particelle ai client
        if (level instanceof ServerLevel serverLevel) {
            // Particelle di effetto magico
            for (int i = 0; i < 20; i++) {
                double x = center.getX() + 0.5 + (level.random.nextDouble() - 0.5) * EFFECT_RADIUS;
                double y = center.getY() + 1.0 + (level.random.nextDouble() - 0.5) * 2;
                double z = center.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * EFFECT_RADIUS;

                serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                        x, y, z,
                        1, 0, 0, 0, 0.05);
            }

            // Particelle esplosive al centro
            serverLevel.sendParticles(ParticleTypes.WITCH,
                    center.getX() + 0.5, center.getY() + 1.0, center.getZ() + 0.5,
                    10, 1.0, 0.5, 1.0, 0.1);
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