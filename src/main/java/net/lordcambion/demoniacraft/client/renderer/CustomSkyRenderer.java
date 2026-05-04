package net.lordcambion.demoniacraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.worldgen.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.joml.Matrix4f;

public class CustomSkyRenderer extends SkyRenderer {

    public static final int TOTAL_RUNE_TEXTURES = 7;
    public static final ResourceLocation[] RUNE_SUN_TEXTURES = new ResourceLocation[TOTAL_RUNE_TEXTURES];
    public static final ResourceLocation[] RUNE_MOON_TEXTURES = new ResourceLocation[TOTAL_RUNE_TEXTURES];

    static {
        for (int i = 0; i < TOTAL_RUNE_TEXTURES; i++) {
            RUNE_SUN_TEXTURES[i] = ResourceLocation.fromNamespaceAndPath(
                    Demoniacraft.MOD_ID,
                    "textures/environment/rune_sun_" + i + ".png"
            );
            RUNE_MOON_TEXTURES[i] = ResourceLocation.fromNamespaceAndPath(
                    Demoniacraft.MOD_ID,
                    "textures/environment/rune_moon_" + i + ".png"
            );
        }
    }

    @Override
    public void renderSkyDisc(float pRed, float pGreen, float pBlue) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.level.dimension() == ModDimensions.DEMONVM_LEVEL_KEY) {
            // Cielo nero nella nostra dimensione
            super.renderSkyDisc(0.0f, 0.0f, 0.0f);
        } else {
            // Cielo normale nelle altre dimensioni
            super.renderSkyDisc(pRed, pGreen, pBlue);
        }
    }

    @Override
    public void renderSunMoonAndStars(PoseStack pPoseStack, MultiBufferSource.BufferSource pBufferSource,
                                      float pTimeOfDay, int pMoonPhase, float pRainLevel, float pStarBrightness) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.level.dimension() == ModDimensions.DEMONVM_LEVEL_KEY) {
            // Renderizza le nostre rune invece di sole/luna/stelle vanilla
            renderCustomRunes(pPoseStack, pBufferSource, mc.level.getDayTime());
            pBufferSource.endBatch();
        } else {
            // Rendering normale nelle altre dimensioni
            super.renderSunMoonAndStars(pPoseStack, pBufferSource, pTimeOfDay, pMoonPhase, pRainLevel, pStarBrightness);
        }
    }

    private void renderCustomRunes(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, long gameTime) {
        Minecraft mc = Minecraft.getInstance();
        float timeOfDay = mc.level.getTimeOfDay(0.0f);
        float skyRotation = timeOfDay * 360.0F; // da 0° a 360° nel ciclo giorno/notte

        long dayTime = gameTime % 24000L;
        long totalDays = gameTime / 24000L;

        // --- LOGICA DI AGGIORNAMENTO DELLE RUNE ---
        // Il Sole cambia runa a mezzanotte (18000 tick)
        long sunDayOffset = (dayTime >= 18000L) ? 1 : 0;
        int sunRuneIndex = (int) ((totalDays + sunDayOffset) % TOTAL_RUNE_TEXTURES);

        // La Luna cambia runa a mezzogiorno (6000 tick)
        long moonDayOffset = (dayTime >= 6000L) ? 1 : 0;
        int moonRuneIndex = (int) ((totalDays + moonDayOffset) % TOTAL_RUNE_TEXTURES);
        // ------------------------------------------

        poseStack.pushPose();

        // 🌞 Sole (runa)
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(skyRotation));
        renderRune(bufferSource, poseStack, RUNE_SUN_TEXTURES[sunRuneIndex], 40.0f, 0.0f, 100.0f, 0.0f);
        poseStack.popPose();

        // 🌙 Luna (runa opposta)
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(skyRotation + 180.0F));
        renderRune(bufferSource, poseStack, RUNE_MOON_TEXTURES[moonRuneIndex], 30.0f, 0.0f, 100.0f, 0.0f);
        poseStack.popPose();

        poseStack.popPose();
    }

    private void renderRune(MultiBufferSource bufferSource, PoseStack poseStack, ResourceLocation texture,
                            float halfSize, float x, float y, float z) {
        VertexConsumer vertex = bufferSource.getBuffer(RenderType.celestial(texture));
        Matrix4f mat = poseStack.last().pose();
        int color = ARGB.color(255, 255, 255, 255);

        // Disegna un quad con la texture della runa
        vertex.addVertex(mat, x - halfSize, y, z - halfSize).setUv(0.0F, 0.0F).setColor(color);
        vertex.addVertex(mat, x + halfSize, y, z - halfSize).setUv(1.0F, 0.0F).setColor(color);
        vertex.addVertex(mat, x + halfSize, y, z + halfSize).setUv(1.0F, 1.0F).setColor(color);
        vertex.addVertex(mat, x - halfSize, y, z + halfSize).setUv(0.0F, 1.0F).setColor(color);
    }

    @Override
    public void renderSunriseAndSunset(PoseStack pPoseStack, MultiBufferSource.BufferSource pBufferSource,
                                       float pSunAngle, int pColor) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.level.dimension() == ModDimensions.DEMONVM_LEVEL_KEY) {
            // Nessun alba/tramonto nella nostra dimensione
            return;
        }
        super.renderSunriseAndSunset(pPoseStack, pBufferSource, pSunAngle, pColor);
    }


    @Override
    public void renderDarkDisc() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.level.dimension() == ModDimensions.DEMONVM_LEVEL_KEY) {
            // Sempre disco scuro nella nostra dimensione
            super.renderDarkDisc();
        } else {
            super.renderDarkDisc();
        }
    }
}