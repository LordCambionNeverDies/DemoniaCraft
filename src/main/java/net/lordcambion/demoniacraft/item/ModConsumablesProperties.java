package net.lordcambion.demoniacraft.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.List;

public class ModConsumablesProperties {
    public static final Consumable POOP = Consumables.defaultFood()
            .onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                            List.of(new MobEffectInstance(MobEffects.NAUSEA, 300, 1))
                    )
            )
            .build();

    public static final Consumable TOMATO = Consumables.defaultFood()

            .build();
    public static final Consumable STRAWBERRY = Consumables.defaultFood()
            .consumeSeconds(0.4f)
            .build();
}
