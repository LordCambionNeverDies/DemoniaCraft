package net.lordcambion.demoniacraft.mixin;

import net.lordcambion.demoniacraft.worldgen.dimension.ModDimensions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    /**
     * Rende il cielo sempre scuro nella dimensione personalizzata,
     * indipendentemente dall'ora del giorno.
     */
    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    private void makeSkyAlwaysDark(Vec3 cameraPos, float partialTick, CallbackInfoReturnable<Integer> cir) {
        ClientLevel self = (ClientLevel)(Object)this;
        if (self.dimension().equals(ModDimensions.DEMONVM_LEVEL_KEY)) {
            int r = (int)(0.02 * 255);
            int g = (int)(0.02 * 255);
            int b = (int)(0.02 * 255);
            int color = (r << 16) | (g << 8) | b;
            cir.setReturnValue(color);
        }
    }

    /**
     * Impedisce qualsiasi variazione di oscuramento del cielo.
     */
    @Inject(method = "getSkyDarken", at = @At("HEAD"), cancellable = true)
    private void disableSkyDarkening(float partialTick, CallbackInfoReturnable<Float> cir) {
        ClientLevel self = (ClientLevel)(Object)this;
        if (self.dimension().equals(ModDimensions.DEMONVM_LEVEL_KEY)) {
            cir.setReturnValue(1.0f); // 1.0 = completamente scuro costante
        }
    }
}
