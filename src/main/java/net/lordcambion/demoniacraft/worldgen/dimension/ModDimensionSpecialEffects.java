package net.lordcambion.demoniacraft.worldgen.dimension;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

public class ModDimensionSpecialEffects extends DimensionSpecialEffects {

    private static float lightningIntensity = 0.0f;
    private static long lastLightningTime = 0;

    public ModDimensionSpecialEffects() {
        super(
                SkyType.OVERWORLD,   // OVERWORLD = permette rendering del cielo (necessario per custom sky)
                false,               // forceBrightLightmap
                false                // constantAmbientLight
        );
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 pFogColor, float pBrightness) {
        return new Vec3(0.0, 0.0, 0.0); // nero assoluto
    }

    @Override
    public boolean isFoggyAt(int pX, int pY) {
        return false;
    }

    private void updateLightning(long currentTime) {
        if (currentTime - lastLightningTime > 15000 && Math.random() < 0.005) {
            lastLightningTime = currentTime;
            lightningIntensity = 1.0f;
        }

        if (lightningIntensity > 0) {
            lightningIntensity -= 0.1f;
            if (lightningIntensity < 0) {
                lightningIntensity = 0;
            }
        }
    }

    public static float getLightningIntensity() {
        return lightningIntensity;
    }
}