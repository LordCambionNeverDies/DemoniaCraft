package net.lordcambion.demoniacraft.event.render;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.EntitySpectatorShaderManager;
import net.minecraftforge.client.event.RegisterEntitySpectatorShadersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = Demoniacraft.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnderHelmetShaderHandler {

    @SubscribeEvent
    public static void registerShaders(RegisterEntitySpectatorShadersEvent event) {
        event.register(EntityType.ENDERMAN, ResourceLocation.withDefaultNamespace("invert"));
        Demoniacraft.LOGGER.info("Registered enderman spectator shader: invert");
    }
}

@Mod.EventBusSubscriber(modid = Demoniacraft.MOD_ID, value = Dist.CLIENT)
class EnderHelmetTickHandler {

    private static boolean helmetActive = false;
    private static int reapplyCounter = 0;
    private static final ResourceLocation ENDERMAN_SHADER = ResourceLocation.withDefaultNamespace("invert");
    private static Field postEffectIdField = null;
    private static Field effectActiveField = null;

    static {
        try {
            // Setup reflection per controllo diretto
            postEffectIdField = net.minecraft.client.renderer.GameRenderer.class.getDeclaredField("postEffectId");
            effectActiveField = net.minecraft.client.renderer.GameRenderer.class.getDeclaredField("effectActive");
            postEffectIdField.setAccessible(true);
            effectActiveField.setAccessible(true);
        } catch (Exception e) {
            Demoniacraft.LOGGER.warn("Reflection setup failed, using fallback method");
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        ItemStack helmet = mc.player.getItemBySlot(EquipmentSlot.HEAD);
        boolean wearingHelmet = helmet.is(ModItems.ENDER_HELMET.get());

        // Gestione attivazione/disattivazione principale
        if (wearingHelmet && !helmetActive) {
            activateEndermanShader(mc);
            helmetActive = true;
        } else if (!wearingHelmet && helmetActive) {
            deactivateShader(mc);
            helmetActive = false;
            reapplyCounter = 0;
        }

        // Se l'elmetto è attivo, verifica periodicamente che lo shader sia ancora attivo
        if (helmetActive) {
            reapplyCounter++;
            if (reapplyCounter >= 20) { // Controlla ogni secondo (20 tick)
                if (!isShaderActive(mc)) {
                    Demoniacraft.LOGGER.debug("Shader was disabled, re-applying");
                    activateEndermanShader(mc);
                }
                reapplyCounter = 0;
            }
        }
    }

    private static boolean isShaderActive(Minecraft mc) {
        try {
            if (postEffectIdField != null) {
                ResourceLocation currentShader = (ResourceLocation) postEffectIdField.get(mc.gameRenderer);
                Boolean isActive = (Boolean) effectActiveField.get(mc.gameRenderer);

                ResourceLocation targetShader = EntitySpectatorShaderManager.get(EntityType.ENDERMAN);
                if (targetShader == null) {
                    targetShader = ENDERMAN_SHADER;
                }

                return currentShader != null && currentShader.equals(targetShader) && isActive;
            }
        } catch (Exception e) {
            // Fallback al metodo pubblico
        }

        // Fallback: usa il metodo pubblico
        ResourceLocation currentShader = mc.gameRenderer.currentPostEffect();
        ResourceLocation targetShader = EntitySpectatorShaderManager.get(EntityType.ENDERMAN);
        if (targetShader == null) {
            targetShader = ENDERMAN_SHADER;
        }
        return currentShader != null && currentShader.equals(targetShader);
    }

    private static void activateEndermanShader(Minecraft mc) {
        try {
            // Metodo diretto via reflection
            if (postEffectIdField != null && effectActiveField != null) {
                ResourceLocation shader = EntitySpectatorShaderManager.get(EntityType.ENDERMAN);
                if (shader == null) {
                    shader = ENDERMAN_SHADER;
                }

                postEffectIdField.set(mc.gameRenderer, shader);
                effectActiveField.set(mc.gameRenderer, true);
                Demoniacraft.LOGGER.debug("Enderman shader activated directly: {}", shader);
                return;
            }
        } catch (Exception e) {
            Demoniacraft.LOGGER.warn("Direct activation failed, using fallback");
        }

        // Fallback: usa il metodo vanilla
        try {
            mc.gameRenderer.checkEntityPostEffect(new EnderMan(EntityType.ENDERMAN, mc.level));
            Demoniacraft.LOGGER.debug("Enderman shader activated via fallback");
        } catch (Exception e) {
            Demoniacraft.LOGGER.error("Failed to activate enderman shader", e);
        }
    }

    private static void deactivateShader(Minecraft mc) {
        try {
            // Metodo diretto via reflection
            if (postEffectIdField != null && effectActiveField != null) {
                postEffectIdField.set(mc.gameRenderer, null);
                effectActiveField.set(mc.gameRenderer, false);
                Demoniacraft.LOGGER.debug("Shader deactivated directly");
                return;
            }
        } catch (Exception e) {
            Demoniacraft.LOGGER.warn("Direct deactivation failed, using fallback");
        }

        // Fallback: usa il metodo vanilla
        try {
            mc.gameRenderer.clearPostEffect();
            Demoniacraft.LOGGER.debug("Shader deactivated via fallback");
        } catch (Exception e) {
            Demoniacraft.LOGGER.error("Failed to deactivate shader", e);
        }
    }
}