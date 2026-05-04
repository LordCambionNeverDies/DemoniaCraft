package net.lordcambion.demoniacraft.entity.client.bonereaper;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoneReaperItemLayer extends RenderLayer<BoneReaperRenderState, BoneReaperModel> {

    public BoneReaperItemLayer(RenderLayerParent<BoneReaperRenderState, BoneReaperModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       BoneReaperRenderState renderState, float yRot, float xRot) {

        // Renderizza SOLO la falce nella mano destra, non tutti gli item
        if (!renderState.rightHandItem.isEmpty()) {
            renderItemInHand(poseStack, bufferSource, packedLight, renderState, HumanoidArm.RIGHT);
        }
    }

    private void renderItemInHand(PoseStack poseStack, MultiBufferSource bufferSource,
                                  int packedLight, BoneReaperRenderState renderState, HumanoidArm arm) {
        poseStack.pushPose();

        BoneReaperModel model = this.getParentModel();

        // Usa translateToHand del modello
        model.translateToHand(arm, poseStack);

        // Renderizza l'item
        var itemRenderState = arm == HumanoidArm.RIGHT ? renderState.rightHandItem : renderState.leftHandItem;

        // Usa la trasformazione corretta per le armi
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F));

        // Scala l'item se necessario
        poseStack.scale(0.8F, 0.8F, 0.8F);



        // Renderizza l'item

        itemRenderState.render(poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }
}