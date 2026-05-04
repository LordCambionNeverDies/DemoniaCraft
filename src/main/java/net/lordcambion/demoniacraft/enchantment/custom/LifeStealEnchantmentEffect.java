package net.lordcambion.demoniacraft.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record LifeStealEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LifeStealEnchantmentEffect> CODEC = MapCodec.unit(LifeStealEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel pLevel, int pEnchantmentLevel, EnchantedItemInUse pItem, Entity pEntity, Vec3 pOrigin) {
        if (!(pEntity instanceof LivingEntity target)) return;

        Entity attackerEntity = pItem.owner();
        if (!(attackerEntity instanceof LivingEntity attacker)) return;

        // Non permettere auto-guarigione
        if (attacker == target) return;

        // Ottieni l'item stack e calcola il cooldown
        ItemStack weapon = pItem.itemStack();
        float cooldownMultiplier = calculateCooldownMultiplier(attacker, weapon);

        // Se il cooldown è troppo basso, non guarire
        if (cooldownMultiplier <= 0.1f) return;

        float baseHealAmount = calculateHealAmount(pEnchantmentLevel);
        float actualHealAmount = baseHealAmount * cooldownMultiplier;

        if (attacker.getHealth() < attacker.getMaxHealth() && actualHealAmount > 0.1f) {
            attacker.heal(actualHealAmount);
            spawnHealParticles(pLevel, attacker);
        }
    }

    private float calculateCooldownMultiplier(LivingEntity attacker, ItemStack weapon) {
        if (attacker instanceof Player player) {
            // Usa il sistema di cooldown corretto con ItemStack
            float cooldownPercent = player.getCooldowns().getCooldownPercent(weapon, 0.0f);

            // NOTA: getCooldownPercent restituisce:
            // - 1.0 quando l'arma è appena stata usata (cooldown massimo)
            // - 0.0 quando l'arma è pronta (nessun cooldown)
            // Quindi dobbiamo invertire il valore per la nostra logica
            float readiness = 1.0f - cooldownPercent;

            // Applica la curva al valore di "prontezza"
            return calculateCooldownCurve(readiness);
        } else {
            // Per le entità non player, assumi cooldown completo
            return 1.0f;
        }
    }

    private float calculateCooldownCurve(float readiness) {
        // readiness va da 0.0 (arma appena usata) a 1.0 (arma pronta)

        // Curva con soglia progressiva
        if (readiness < 0.3f) return 0.0f; // No heal sotto 30% di prontezza
        if (readiness < 0.7f) return (readiness - 0.3f) * 2.5f; // Scaling 30-70%
        return 1.0f; // Heal completo sopra 70%
    }

    private void spawnHealParticles(ServerLevel level, LivingEntity entity) {
        // Puoi implementare effetti particellari qui se desideri
        // Esempio: particelle di cuore o particelle personalizzate
    }

    private float calculateHealAmount(int enchantmentLevel) {
        return enchantmentLevel + 1;
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}