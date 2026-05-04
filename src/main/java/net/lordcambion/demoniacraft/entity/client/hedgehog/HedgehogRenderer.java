package net.lordcambion.demoniacraft.entity.client.hedgehog;

import com.mojang.blaze3d.vertex.PoseStack;
import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.entity.HedgehogVariants;
import net.lordcambion.demoniacraft.entity.custom.HedgehogEntity;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HedgehogRenderer extends AgeableMobRenderer<HedgehogEntity, HedgehogRenderState, HedgehogModel> {
    private static final ResourceLocation GRAY_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "textures/entity/hedgehog_gray.png");
    private static final ResourceLocation BROWN_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "textures/entity/hedgehog_brown.png");

    public HedgehogRenderer(EntityRendererProvider.Context context) {
        super(context,
                new HedgehogModel(context.bakeLayer(HedgehogModel.LAYER_LOCATION)), // Adult model
                new HedgehogBabyModel(context.bakeLayer(HedgehogModel.LAYER_LOCATION)), // Baby model
                0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(HedgehogRenderState state) {
        return switch (state.variant) {
            case GRAY -> GRAY_TEXTURE;
            case BROWN -> BROWN_TEXTURE;
        };
    }

    @Override
    public HedgehogRenderState createRenderState() {
        return new HedgehogRenderState();
    }

    @Override
    protected void scale(HedgehogRenderState pRenderState, PoseStack pPoseStack) {
        if (pRenderState.baby) {
            float scale = 0.5F; // 50% delle dimensioni originali
            pPoseStack.scale(scale, scale, scale);
        }
        super.scale(pRenderState, pPoseStack);
    }

    @Override
    public void extractRenderState(HedgehogEntity entity, HedgehogRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        // Copia gli animation states
        renderState.idleAnimationState.copyFrom(entity.idleAnimationState);
        renderState.walkAnimationState.copyFrom(entity.walkAnimationState);
        renderState.rollUpAnimationState.copyFrom(entity.rollUpAnimationState);
        renderState.rolledAnimationState.copyFrom(entity.peekAnimationState);
        renderState.rollOutAnimationState.copyFrom(entity.rollOutAnimationState);

        // Copia lo stato corrente
        renderState.setCurrentState(entity.getState());

        // Copia la variante
        renderState.variant = entity.getVariant();

        // COPIA LO STATO BABY - QUESTA È LA PARTE MANCANTE!
        renderState.baby = entity.isBaby();
    }
}