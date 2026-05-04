package net.lordcambion.demoniacraft.enchantment;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.enchantment.custom.LifeStealEnchantmentEffect;
import net.lordcambion.demoniacraft.enchantment.custom.LightningStrikerEnchantmentEffect;
import net.lordcambion.demoniacraft.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;

public class ModEnchantments {
    public static final ResourceKey<Enchantment>LIGHTNING_STRIKER =ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID,"lightning_striker"));
    public static final ResourceKey<Enchantment>LIFE_STEAL =ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID,"life_steal"));

    public static void bootstrap(BootstrapContext<Enchantment>context){
        var enchantments=context.lookup(Registries.ENCHANTMENT);
        var items =context.lookup(Registries.ITEM);
        register(context,LIGHTNING_STRIKER,Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ModTags.Items.ENCHANTABLE_HAMMER),
                items.getOrThrow(ModTags.Items.ENCHANTABLE_HAMMER),
                3,3,
                Enchantment.dynamicCost(25,8),
                Enchantment.dynamicCost(55,8),
                3,
                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM,new LightningStrikerEnchantmentEffect()));
        register(context,LIFE_STEAL,Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ModTags.Items.ENCHANTABLE_SCYTHE),
                        items.getOrThrow(ModTags.Items.ENCHANTABLE_SCYTHE),
                        3,5,
                        Enchantment.dynamicCost(15,8),
                        Enchantment.dynamicCost(40,8),
                        5,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM,new LifeStealEnchantmentEffect()));


    }

    public static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment>key, Enchantment.Builder builder){
        registry.register(key,builder.build(key.location()));
    }
}
