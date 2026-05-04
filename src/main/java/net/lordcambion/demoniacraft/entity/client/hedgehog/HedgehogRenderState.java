package net.lordcambion.demoniacraft.entity.client.hedgehog;

import net.lordcambion.demoniacraft.entity.HedgehogVariants;
import net.lordcambion.demoniacraft.entity.custom.HedgehogEntity;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HedgehogRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState rollUpAnimationState = new AnimationState();
    public final AnimationState rolledAnimationState = new AnimationState();
    public final AnimationState rollOutAnimationState = new AnimationState();

    public HedgehogVariants variant = HedgehogVariants.GRAY;
    public HedgehogEntity.HedgehogState currentState = HedgehogEntity.HedgehogState.IDLE;
    public boolean baby = false; // Aggiungi questo campo

    public HedgehogEntity.HedgehogState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(HedgehogEntity.HedgehogState state) {
        this.currentState = state;
    }
}
