package net.lordcambion.demoniacraft.entity.client.bonereaper;

import net.lordcambion.demoniacraft.entity.custom.BoneReaperEntity;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoneReaperRenderState extends HumanoidRenderState {
    // Stati di animazione custom
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    public boolean isAttacking = false;

    public void copyAnimationStates(BoneReaperEntity entity) {
        // Copia gli stati di animazione dall'entità
        this.idleAnimationState.copyFrom(entity.idleAnimationState);
        this.walkAnimationState.copyFrom(entity.walkAnimationState);
        this.attackAnimationState.copyFrom(entity.attackAnimationState);
        this.isAttacking = entity.isPerformingAttack();


    }
}