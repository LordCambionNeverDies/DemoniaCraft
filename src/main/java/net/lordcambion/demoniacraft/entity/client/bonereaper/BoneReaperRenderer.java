package net.lordcambion.demoniacraft.entity.client.bonereaper;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.entity.custom.BoneReaperEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoneReaperRenderer extends HumanoidMobRenderer<BoneReaperEntity, BoneReaperRenderState, BoneReaperModel> {

    private static final ResourceLocation BONE_REAPER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "textures/entity/bonereaper.png");

    public BoneReaperRenderer(EntityRendererProvider.Context context) {
        super(context,
                new BoneReaperModel(context.bakeLayer(BoneReaperModel.LAYER_LOCATION)),
                0.5F);

        // Usa i tuoi modelli personalizzati invece di quelli dello skeleton
        this.addLayer(new HumanoidArmorLayer<>(this,
                new BoneReaperArmorModel(context.bakeLayer(BoneReaperModel.INNER_ARMOR)),
                new BoneReaperArmorModel(context.bakeLayer(BoneReaperModel.OUTER_ARMOR)),
                context.getEquipmentRenderer()
        ));
    }

    @Override
    protected HumanoidModel.ArmPose getArmPose(BoneReaperEntity entity, HumanoidArm arm) {
        if (entity.isAggressive() && entity.getMainHandItem().isEmpty()) {
            return HumanoidModel.ArmPose.ITEM;
        }
        return HumanoidModel.ArmPose.ITEM;
    }

    @Override
    public BoneReaperRenderState createRenderState() {
        return new BoneReaperRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(BoneReaperRenderState renderState) {
        return BONE_REAPER_TEXTURE;
    }

    @Override
    public void extractRenderState(BoneReaperEntity entity, BoneReaperRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.copyAnimationStates(entity);

        // IMPORTANTE: Disabilita il rendering degli item da parte del HumanoidMobRenderer
        // Imposta gli item come vuoti così non vengono renderizzati due volte

    }
}