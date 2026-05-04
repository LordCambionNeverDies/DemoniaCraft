package net.lordcambion.demoniacraft.mixin;

import net.lordcambion.demoniacraft.worldgen.dimension.ModDimensions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.renderer.fog.environment.AirBasedFogEnvironment")
public class AirBasedFogEnvironmentMixin {

    @Inject(method = "getBaseColor", at = @At("HEAD"), cancellable = true)
    private void forceBlackFog(ClientLevel level, Camera camera, int renderDistance, float partialTick, CallbackInfoReturnable<Integer> cir) {
        if (level.dimension().equals(ModDimensions.DEMONVM_LEVEL_KEY)) {
            cir.setReturnValue(0x000000); // colore completamente nero
        }
    }
}
