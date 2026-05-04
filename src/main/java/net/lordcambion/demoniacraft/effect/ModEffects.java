package net.lordcambion.demoniacraft.effect;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public  static  final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Demoniacraft.MOD_ID);

    public static final RegistryObject<MobEffect> BOUNCE_EFFECT = MOB_EFFECTS.register("bounce",
            () -> new SlimeyEffect(MobEffectCategory.NEUTRAL, 0x36ebab)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "bounce"),
                            -0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "bounce"),
                            -1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final RegistryObject<MobEffect> CLIMB_EFFECT = MOB_EFFECTS.register("climb",
            () -> new ClimbingEffect(MobEffectCategory.NEUTRAL, 000000)
                    .addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "climb"),
                            -0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public  static  void register(BusGroup eventBus){
        MOB_EFFECTS.register(eventBus);
    }

}
