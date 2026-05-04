package net.lordcambion.demoniacraft.entity.client.bonereaper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.lordcambion.demoniacraft.Demoniacraft;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoneReaperModel extends HumanoidModel<BoneReaperRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "bonereaper"), "main");

    public static final ModelLayerLocation INNER_ARMOR = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "bonereaper"), "inner_armor");

    public static final ModelLayerLocation OUTER_ARMOR = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "bonereaper"), "outer_armor");


    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart alasx;
    private final ModelPart alainsx;
    private final ModelPart alaoutsx;
    private final ModelPart alisx;
    private final ModelPart ala1sx;
    private final ModelPart back4;
    private final ModelPart front4;
    private final ModelPart ala2sx;
    private final ModelPart back5;
    private final ModelPart front5;
    private final ModelPart ala3sx;
    private final ModelPart back6;
    private final ModelPart front6;
    private final ModelPart aladx;
    private final ModelPart alaindx;
    private final ModelPart alidx;
    private final ModelPart ala1dx;
    private final ModelPart back;
    private final ModelPart front;
    private final ModelPart ala2dx;
    private final ModelPart back2;
    private final ModelPart front2;
    private final ModelPart ala3dx;
    private final ModelPart back3;
    private final ModelPart front3;
    private final ModelPart alaoutdx;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart cape;
    private final ModelPart HEADBLOCK;
    private final ModelPart cornosx;
    private final ModelPart cornodx;

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation attackAnimation;

    public BoneReaperModel(ModelPart root) {
        super(root);
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
        this.alasx = root.getChild("alasx");
        this.alainsx = this.alasx.getChild("alainsx");
        this.alaoutsx = this.alasx.getChild("alaoutsx");
        this.alisx = this.alasx.getChild("alisx");
        this.ala1sx = this.alisx.getChild("ala1sx");
        this.back4 = this.ala1sx.getChild("back4");
        this.front4 = this.ala1sx.getChild("front4");
        this.ala2sx = this.alisx.getChild("ala2sx");
        this.back5 = this.ala2sx.getChild("back5");
        this.front5 = this.ala2sx.getChild("front5");
        this.ala3sx = this.alisx.getChild("ala3sx");
        this.back6 = this.ala3sx.getChild("back6");
        this.front6 = this.ala3sx.getChild("front6");
        this.aladx = root.getChild("aladx");
        this.alaindx = this.aladx.getChild("alaindx");
        this.alidx = this.aladx.getChild("alidx");
        this.ala1dx = this.alidx.getChild("ala1dx");
        this.back = this.ala1dx.getChild("back");
        this.front = this.ala1dx.getChild("front");
        this.ala2dx = this.alidx.getChild("ala2dx");
        this.back2 = this.ala2dx.getChild("back2");
        this.front2 = this.ala2dx.getChild("front2");
        this.ala3dx = this.alidx.getChild("ala3dx");
        this.back3 = this.ala3dx.getChild("back3");
        this.front3 = this.ala3dx.getChild("front3");
        this.alaoutdx = this.aladx.getChild("alaoutdx");
        this.right_arm = root.getChild("right_arm");
        this.left_arm = root.getChild("left_arm");
        this.cape = root.getChild("cape");
        this.HEADBLOCK = root.getChild("HEADBLOCK");
        this.cornosx = this.HEADBLOCK.getChild("cornosx");
        this.cornodx = this.HEADBLOCK.getChild("cornodx");

        this.idleAnimation = BoneReaperAnimations.IDLE.bake(root);
        this.walkAnimation = BoneReaperAnimations.walk.bake(root);
        this.attackAnimation = BoneReaperAnimations.attack.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(32, 21).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.1F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 21).mirror().addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.1F));

        PartDefinition alasx = partdefinition.addOrReplaceChild("alasx", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, 2.1F));

        PartDefinition alainsx = alasx.addOrReplaceChild("alainsx", CubeListBuilder.create().texOffs(40, 21).addBox(-1.7093F, -1.2994F, -1.1F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition alaoutsx = alasx.addOrReplaceChild("alaoutsx", CubeListBuilder.create().texOffs(32, 35).addBox(-1.4256F, -0.8704F, -0.2931F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7F, 3.2F, 0.0F, -2.3165F, 1.2191F, 1.2537F));

        PartDefinition alisx = alasx.addOrReplaceChild("alisx", CubeListBuilder.create(), PartPose.offset(22.0F, -10.0F, -4.5F));

        PartDefinition ala1sx = alisx.addOrReplaceChild("ala1sx", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back4 = ala1sx.addOrReplaceChild("back4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.8146F, -0.2529F, -1.2357F));

        PartDefinition ala5_r1 = back4.addOrReplaceChild("ala5_r1", CubeListBuilder.create().texOffs(44, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3289F, 11.5163F, -0.1F, -0.856F, 0.1582F, -1.3613F));

        PartDefinition front4 = ala1sx.addOrReplaceChild("front4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6F, 4.2F, 14.6F, -2.4119F, -1.2519F, 2.7834F));

        PartDefinition ala5_r2 = front4.addOrReplaceChild("ala5_r2", CubeListBuilder.create().texOffs(40, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3289F, 11.5163F, -0.1F, -1.0164F, 0.3786F, -1.1426F));

        PartDefinition ala2sx = alisx.addOrReplaceChild("ala2sx", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 6.0F, 5.0F, -0.0465F, -0.2577F, 0.4424F));

        PartDefinition back5 = ala2sx.addOrReplaceChild("back5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.8146F, -0.2529F, -1.2357F));

        PartDefinition ala5_r3 = back5.addOrReplaceChild("ala5_r3", CubeListBuilder.create().texOffs(44, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3289F, 11.5163F, -0.1F, -0.856F, 0.1582F, -1.3613F));

        PartDefinition front5 = ala2sx.addOrReplaceChild("front5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6F, 4.2F, 14.6F, -2.4119F, -1.2519F, 2.7834F));

        PartDefinition ala5_r4 = front5.addOrReplaceChild("ala5_r4", CubeListBuilder.create().texOffs(40, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3289F, 11.5163F, -0.1F, -1.0164F, 0.3786F, -1.1426F));

        PartDefinition ala3sx = alisx.addOrReplaceChild("ala3sx", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.9F, 3.3F, 16.1F, -0.9098F, -1.1876F, 0.8079F));

        PartDefinition back6 = ala3sx.addOrReplaceChild("back6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.4F, -0.5F, -0.2F, 1.8146F, -0.2529F, -1.2357F));

        PartDefinition ala5_r5 = back6.addOrReplaceChild("ala5_r5", CubeListBuilder.create().texOffs(44, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3289F, 11.5163F, -0.1F, -0.8653F, 0.0589F, -1.4471F));

        PartDefinition front6 = ala3sx.addOrReplaceChild("front6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6F, 4.2F, 14.6F, -2.4119F, -1.2519F, 2.7834F));

        PartDefinition ala5_r6 = front6.addOrReplaceChild("ala5_r6", CubeListBuilder.create().texOffs(40, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3289F, 11.5163F, -0.1F, -1.0164F, 0.3786F, -1.1426F));

        PartDefinition aladx = partdefinition.addOrReplaceChild("aladx", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.5F, 2.1F));

        PartDefinition alaindx = aladx.addOrReplaceChild("alaindx", CubeListBuilder.create().texOffs(40, 21).addBox(-1.0768F, -0.6695F, -1.1F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.1345F));

        PartDefinition alidx = aladx.addOrReplaceChild("alidx", CubeListBuilder.create(), PartPose.offsetAndRotation(-11.2F, -5.4F, -2.1F, 0.3489F, -0.3807F, -0.2242F));

        PartDefinition ala1dx = alidx.addOrReplaceChild("ala1dx", CubeListBuilder.create(), PartPose.offset(1.0056F, 0.4393F, -0.8798F));

        PartDefinition back = ala1dx.addOrReplaceChild("back", CubeListBuilder.create(), PartPose.offsetAndRotation(12.0F, -3.8F, -1.2F, 1.8146F, -0.2529F, -1.2357F));

        PartDefinition ala5_r7 = back.addOrReplaceChild("ala5_r7", CubeListBuilder.create().texOffs(44, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.7289F, 11.5163F, -1.2F, -1.3257F, 0.3278F, -1.3186F));

        PartDefinition front = ala1dx.addOrReplaceChild("front", CubeListBuilder.create(), PartPose.offsetAndRotation(11.6F, -0.8F, 8.6F, -2.4119F, -1.2519F, 2.7834F));

        PartDefinition ala5_r8 = front.addOrReplaceChild("ala5_r8", CubeListBuilder.create().texOffs(40, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1289F, 15.7163F, 0.2F, -0.4097F, -0.1325F, 0.0964F));

        PartDefinition ala2dx = alidx.addOrReplaceChild("ala2dx", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5056F, 0.4393F, -0.3798F, -0.0465F, -0.2577F, 0.4424F));

        PartDefinition back2 = ala2dx.addOrReplaceChild("back2", CubeListBuilder.create(), PartPose.offsetAndRotation(7.5938F, -3.7878F, 2.0069F, 1.8146F, -0.2529F, -1.2357F));

        PartDefinition ala5_r9 = back2.addOrReplaceChild("ala5_r9", CubeListBuilder.create().texOffs(44, 32).addBox(1.2314F, -11.9518F, -0.5343F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3289F, 10.5163F, -0.1F, -0.7937F, 0.5973F, -0.9699F));

        PartDefinition front2 = ala2dx.addOrReplaceChild("front2", CubeListBuilder.create(), PartPose.offsetAndRotation(12.2938F, 0.1122F, 12.6069F, -2.4119F, -1.2519F, 2.7834F));

        PartDefinition ala5_r10 = front2.addOrReplaceChild("ala5_r10", CubeListBuilder.create().texOffs(40, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.4289F, 11.9163F, 0.9F, -1.3061F, 0.5052F, -1.1059F));

        PartDefinition ala3dx = alidx.addOrReplaceChild("ala3dx", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0944F, 0.7393F, 0.0202F, -0.9098F, -1.1876F, 0.8079F));

        PartDefinition back3 = ala3dx.addOrReplaceChild("back3", CubeListBuilder.create(), PartPose.offsetAndRotation(12.7766F, -7.6219F, -3.6448F, 1.8146F, -0.2529F, -1.2357F));

        PartDefinition ala5_r11 = back3.addOrReplaceChild("ala5_r11", CubeListBuilder.create().texOffs(44, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3289F, 11.5163F, -0.1F, -0.8271F, -0.6152F, -0.7931F));

        PartDefinition front3 = ala3dx.addOrReplaceChild("front3", CubeListBuilder.create(), PartPose.offsetAndRotation(8.8766F, -3.2219F, 11.3552F, -2.5864F, -1.2519F, 2.7834F));

        PartDefinition ala5_r12 = front3.addOrReplaceChild("ala5_r12", CubeListBuilder.create().texOffs(40, 32).addBox(1.2314F, -11.9519F, -0.5343F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3289F, 11.5163F, -0.1F, -0.8037F, 0.6491F, -1.0863F));

        PartDefinition alaoutdx = aladx.addOrReplaceChild("alaoutdx", CubeListBuilder.create().texOffs(32, 35).addBox(-1.2503F, -0.7234F, -1.2857F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.3F, 2.7F, 0.0F, -1.4438F, 1.2191F, 1.2537F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 32).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 32).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition cape = partdefinition.addOrReplaceChild("cape", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition cube_r1 = cape.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, -1.0F, 0.0F, 10.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, -0.01F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r2 = cape.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(42, 43).addBox(-1.0F, -1.0F, 0.0F, 10.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        // La testa standard con la geometria - le armature useranno questa
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        // HEADBLOCK per le animazioni - contiene corni e altre parti custom
        PartDefinition HEADBLOCK = partdefinition.addOrReplaceChild("HEADBLOCK", CubeListBuilder.create(), PartPose.offset(-0.3F, -0.8F, 0.1F));

        PartDefinition cornosx = HEADBLOCK.addOrReplaceChild("cornosx", CubeListBuilder.create().texOffs(32, 35).addBox(-1.4256F, 0.1296F, -0.2931F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -7.0F, -2.0F, -1.6853F, 0.7321F, 1.7416F));

        PartDefinition cornodx = HEADBLOCK.addOrReplaceChild("cornodx", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -7.0F, -1.0F, -1.4356F, 0.6013F, 1.5168F));

        PartDefinition cornodx_r1 = cornodx.addOrReplaceChild("cornodx_r1", CubeListBuilder.create().texOffs(32, 35).addBox(-0.9214F, 0.7603F, -1.1857F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3289F, -0.4837F, 0.4F, 0.0F, 0.0F, 0.0873F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(BoneReaperRenderState state) {
        super.setupAnim(state);

        // Reset parti custom
        this.cape.resetPose();
        this.alasx.getAllParts().forEach(ModelPart::resetPose);
        this.aladx.getAllParts().forEach(ModelPart::resetPose);
        this.HEADBLOCK.getAllParts().forEach(ModelPart::resetPose);

        // Applica le animazioni custom (solo ali e mantello)
        if (state.isAttacking) {
            this.attackAnimation.apply(state.attackAnimationState, state.ageInTicks);
        } else if (state.walkAnimationState.isStarted()) {
            this.walkAnimation.apply(state.walkAnimationState, state.ageInTicks);
            this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        } else {
            this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        }

        // Sincronizza HEADBLOCK con la testa standard
        // Le corna seguiranno automaticamente i movimenti della testa
        this.HEADBLOCK.copyFrom(this.head);
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        ModelPart armPart = this.getArm(arm);

        // IMPORTANTE: Questo posiziona la matrice alla fine del braccio
        armPart.translateAndRotate(poseStack);

        // Aggiusta ulteriormente per posizionare l'item nella mano
        // Questi valori posizionano l'item alla fine del braccio
        if (arm == HumanoidArm.RIGHT) {
            poseStack.translate(0.05F, -0F, 0.0F); // Sposta verso la fine del braccio
        } else {
            poseStack.translate(0.0F, -0F, 0.0F); // Sposta verso la fine del braccio
        }
    }
}