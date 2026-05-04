package net.lordcambion.demoniacraft.client.renderer;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.worldgen.dimension.ModDimensionSpecialEffects;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Demoniacraft.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEffects {

    @SubscribeEvent
    public static void onRegisterEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "demonvm"),
                new ModDimensionSpecialEffects()
        );
    }
}