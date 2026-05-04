package net.lordcambion.demoniacraft.entity.client.chair;

import net.lordcambion.demoniacraft.entity.custom.ChairEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class ChairRenderer extends EntityRenderer<ChairEntity,ChairRenderState> {
    public ChairRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ChairRenderState createRenderState() {
        return new ChairRenderState();
    }

    public ResourceLocation getTextureLocation(ChairEntity pEntity){
        return null;
    }

    @Override
    public boolean shouldRender(ChairEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }
}
