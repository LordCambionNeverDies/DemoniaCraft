package net.lordcambion.demoniacraft.potion;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    // Should be DeferredRegister<Potion>, not MobEffect
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, Demoniacraft.MOD_ID);

    // Use .get() to get the actual MobEffect from the RegistryObject
    public static final RegistryObject<Potion> BOUNCING_POTION = POTIONS.register("bouncing_potion",
            () -> new Potion("demoniacraft_bouncing_potion", new MobEffectInstance(ModEffects.BOUNCE_EFFECT.getHolder().get(), 1800, 0)));
public static final RegistryObject<Potion> CLIMBING_POTION = POTIONS.register("climbing_potion",
            () -> new Potion("demoniacraft_climbing_potion", new MobEffectInstance(ModEffects.CLIMB_EFFECT.getHolder().get(), 1200, 0)));

//ok


    public static void register(BusGroup eventBus) {
        POTIONS.register(eventBus);
    }
}
