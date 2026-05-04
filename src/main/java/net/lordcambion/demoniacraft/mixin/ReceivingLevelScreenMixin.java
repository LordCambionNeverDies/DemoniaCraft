package net.lordcambion.demoniacraft.mixin;

import net.lordcambion.demoniacraft.block.custom.ModPortalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ReceivingLevelScreen.class)
public abstract class ReceivingLevelScreenMixin extends Screen {

    @Shadow @Final
    private ReceivingLevelScreen.Reason reason;

    @Shadow @Nullable
    private TextureAtlasSprite cachedNetherPortalSprite;

    @Shadow
    protected abstract TextureAtlasSprite getNetherPortalSprite();

    @Unique
    @Nullable
    private TextureAtlasSprite demoniacraft$cachedDemonPortalSprite;

    // Costruttore necessario per estendere Screen
    protected ReceivingLevelScreenMixin() {
        super(null);
    }

    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    private void renderDemonPortalBackground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        // Controlla se stiamo attraversando un portale demoniaco
        if (this.minecraft != null && this.minecraft.player != null && demoniacraft$isDemonPortalTransition()) {
            // Usa la texture del portale demoniaco come sfondo
            TextureAtlasSprite demonPortalSprite = demoniacraft$getDemonPortalSprite();
            pGuiGraphics.blitSprite(
                    RenderPipelines.GUI_OPAQUE_TEXTURED_BACKGROUND,
                    demonPortalSprite,
                    0, 0,
                    pGuiGraphics.guiWidth(),
                    pGuiGraphics.guiHeight()
            );
            ci.cancel();
        }
    }

    @Unique
    private boolean demoniacraft$isDemonPortalTransition() {
        // Controlla se il giocatore sta attraversando un portale demoniaco
        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.level != null) {
            var pos = this.minecraft.player.blockPosition();
            // Controlla anche le posizioni adiacenti nel caso il giocatore sia tra due blocchi
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        var checkPos = pos.offset(dx, dy, dz);
                        var state = this.minecraft.level.getBlockState(checkPos);
                        if (state.getBlock() instanceof ModPortalBlock) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Unique
    private TextureAtlasSprite demoniacraft$getDemonPortalSprite() {
        if (this.demoniacraft$cachedDemonPortalSprite == null && this.minecraft != null) {
            try {
                // Prova a ottenere la texture del portale demoniaco
                var portalBlock = net.lordcambion.demoniacraft.block.ModBlocks.DEMONVM_PORTAL_BLOCK.get();
                this.demoniacraft$cachedDemonPortalSprite = this.minecraft.getBlockRenderer()
                        .getBlockModelShaper()
                        .getParticleIcon(portalBlock.defaultBlockState());
            } catch (Exception e) {
                // Fallback alla texture del portale del Nether in caso di errore
                this.demoniacraft$cachedDemonPortalSprite = this.getNetherPortalSprite();
            }
        }
        return this.demoniacraft$cachedDemonPortalSprite != null
                ? this.demoniacraft$cachedDemonPortalSprite
                : this.getNetherPortalSprite();
    }
}