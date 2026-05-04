package net.lordcambion.demoniacraft.event;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.worldgen.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Demoniacraft.MOD_ID, value = Dist.CLIENT)
public class DimensionFogHandler {

    private static float lightningIntensity = 0.0f;
    private static long lastLightningTime = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        ClientLevel level = mc.level;
        Player player = mc.player;

        // Controlla se siamo nella dimensione custom
        if (level.dimension() == ModDimensions.DEMONVM_LEVEL_KEY) {
            spawnFogParticles(level, player);
            updateLightning(level.getGameTime());
        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.level.dimension() == ModDimensions.DEMONVM_LEVEL_KEY) {
            // Fog molto denso
            event.setNearPlaneDistance(1.0f);
            event.setFarPlaneDistance(32.0f + (lightningIntensity * 16.0f)); // Si espande con i lampi
        }
    }

    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.level.dimension() == ModDimensions.DEMONVM_LEVEL_KEY) {
            // Colore del fog rosso-nero con lampi
            float red = 0.1f + (lightningIntensity * 0.7f);
            event.setRed(red);
            event.setGreen(0.0f);
            event.setBlue(0.0f);
        }
    }

    private static void updateLightning(long gameTime) {
        // Lightning flash ogni 200-400 ticks casualmente
        if (gameTime - lastLightningTime > 600 && Math.random() < 0.0025) {
            lastLightningTime = gameTime;
            lightningIntensity = 1.0f;
        }

        // Fade out del lampo
        if (lightningIntensity > 0) {
            lightningIntensity -= 0.025f;
            if (lightningIntensity < 0) lightningIntensity = 0;
        }
    }

    private static void spawnFogParticles(ClientLevel level, Player player) {
        double px = player.getX();
        double py = player.getY();
        double pz = player.getZ();

        if (py < 40) { // Solo a bassa quota, come la bedrock fog
            for (int i = 0; i < 10; i++) {
                double x = px + (level.random.nextDouble() - 0.5) * 16.0;
                double y = py + (level.random.nextDouble() - 0.5) * 4.0;
                double z = pz + (level.random.nextDouble() - 0.5) * 16.0;

                level.addParticle(
                        ParticleTypes.ASH, // effetto simile alla bedrock fog
                        x, y, z,
                        0.0, 0.0, 0.0
                );
            }
        }
    }
}