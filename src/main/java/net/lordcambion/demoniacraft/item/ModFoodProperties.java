package net.lordcambion.demoniacraft.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.List;




public class ModFoodProperties {
    // Cibo senza effetti - questo dovrebbe funzionare
    public static final FoodProperties POOP = new FoodProperties.Builder()
            .nutrition(1)
            .saturationModifier(0.025f)
            .alwaysEdible()
            .alwaysEdible()
            .build();
    public static final FoodProperties TOMATO = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.034f)
            .build();
    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.053f)
            .build();


}