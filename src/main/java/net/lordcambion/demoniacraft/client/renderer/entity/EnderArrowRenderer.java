package net.lordcambion.demoniacraft.client.renderer.entity;

import net.lordcambion.demoniacraft.Demoniacraft;

import net.lordcambion.demoniacraft.entity.projectile.EnderArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;

public class EnderArrowRenderer extends ArrowRenderer<EnderArrowEntity, ArrowRenderState> {

    public static final ResourceLocation ENDER_ARROW_LOCATION =
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "textures/entity/projectiles/ender_arrow.png");

    public EnderArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected ResourceLocation getTextureLocation(ArrowRenderState renderState) {
        return ENDER_ARROW_LOCATION;
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }
}