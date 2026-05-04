package net.lordcambion.demoniacraft.entity.client.hedgehog;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.entity.custom.HedgehogEntity;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HedgehogModel extends EntityModel<HedgehogRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "hedgehog"), "main"
    );

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart rightEarCube;
    private final ModelPart leftEarCube;
    private final ModelPart frontLeftLeg;
    private final ModelPart frontRightLeg;
    private final ModelPart backLeftLeg;
    private final ModelPart backRightLeg;
    private final ModelPart tail;

    // Top spikes
    private final ModelPart top;
    private final ModelPart top2;
    private final ModelPart top3;
    private final ModelPart top4;
    private final ModelPart top5;
    private final ModelPart top6;
    private final ModelPart top7;
    private final ModelPart top8;
    private final ModelPart top9;

    // Side spikes — left side
    private final ModelPart sideL;
    private final ModelPart sideL2;
    private final ModelPart sideL3;
    private final ModelPart sideL4;
    private final ModelPart sideL5;
    private final ModelPart sideL6;
    private final ModelPart sideL7;
    private final ModelPart sideL8;

    // Side spikes — right side
    private final ModelPart sideL9;
    private final ModelPart sideL10;
    private final ModelPart sideL11;
    private final ModelPart sideL12;
    private final ModelPart sideL13;
    private final ModelPart sideL14;
    private final ModelPart sideL15;
    private final ModelPart sideL16;
    private final ModelPart lower;
    private final ModelPart roll;
    // Animations
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation scaredAnimation;
    private final KeyframeAnimation rollUpAnimation;
    private final KeyframeAnimation rollOutAnimation;

    public HedgehogModel(ModelPart root) {
        super(root);

        this.body = root.getChild("body");
        var upper = this.body.getChild("upper");
        var head1 = upper.getChild("head1");
        this.head = head1.getChild("head");
        this.rightEarCube = head1.getChild("right_ear_cube"); // Deve corrispondere a "right_ear_cube"
        this.leftEarCube = head1.getChild("left_ear_cube");   // Deve corrispondere a "left_ear_cube"

        var lower = this.body.getChild("lower");
        this.frontLeftLeg = lower.getChild("front_left_leg");   // Deve corrispondere a "front_left_leg"
        this.frontRightLeg = lower.getChild("front_right_leg"); // Deve corrispondere a "front_right_leg"
        this.backLeftLeg = lower.getChild("back_left_leg");     // Deve corrispondere a "back_left_leg"
        this.backRightLeg = lower.getChild("back_right_leg");   // Deve corrispondere a "back_right_leg"

        this.tail = lower.getChild("tail");

        var topspikes = lower.getChild("topspikes");
        this.top = topspikes.getChild("top");
        this.top2 = topspikes.getChild("top2");
        this.top3 = topspikes.getChild("top3");
        this.top4 = topspikes.getChild("top4");
        this.top5 = topspikes.getChild("top5");
        this.top6 = topspikes.getChild("top6");
        this.top7 = topspikes.getChild("top7");
        this.top8 = topspikes.getChild("top8");
        this.top9 = topspikes.getChild("top9");

        var sideLeft = lower.getChild("sideLeft");
        this.sideL = sideLeft.getChild("sideL");
        this.sideL2 = sideLeft.getChild("sideL2");
        this.sideL3 = sideLeft.getChild("sideL3");
        this.sideL4 = sideLeft.getChild("sideL4");
        this.sideL5 = sideLeft.getChild("sideL5");
        this.sideL6 = sideLeft.getChild("sideL6");
        this.sideL7 = sideLeft.getChild("sideL7");
        this.sideL8 = sideLeft.getChild("sideL8");

        var side_Left2 = lower.getChild("side_Left2");
        this.sideL9 = side_Left2.getChild("sideL9");
        this.sideL10 = side_Left2.getChild("sideL10");
        this.sideL11 = side_Left2.getChild("sideL11");
        this.sideL12 = side_Left2.getChild("sideL12");
        this.sideL13 = side_Left2.getChild("sideL13");
        this.sideL14 = side_Left2.getChild("sideL14");
        this.sideL15 = side_Left2.getChild("sideL15");
        this.sideL16 = side_Left2.getChild("sideL16");
        this.lower = this.body.getChild("lower");
        this.roll = this.lower.getChild("roll");
        // Bake animations
        this.idleAnimation = HedgehogAnimations.HEDGEHOG_IDLE.bake(root);
        this.walkAnimation = HedgehogAnimations.HEDGEHOG_WALK.bake(root);
        this.rollUpAnimation = HedgehogAnimations.HEDGEHOG_ROLL_UP.bake(root);
        this.scaredAnimation = HedgehogAnimations.HEDGEHOG_SCARED.bake(root);
        this.rollOutAnimation = HedgehogAnimations.HEDGEHOG_ROLL_OUT.bake(root);

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-2.5F, 18.0F, -7.6F));

        PartDefinition upper = body.addOrReplaceChild("upper", CubeListBuilder.create(), PartPose.offset(1.5F, 6.0F, 7.6F));

        PartDefinition head1 = upper.addOrReplaceChild("head1", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -7.0F));

        PartDefinition head = head1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(12, 15).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, 1.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition right_ear_cube = head1.addOrReplaceChild("right_ear_cube", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -1.5176F, 0.0682F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.0F, 1.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition left_ear_cube = head1.addOrReplaceChild("left_ear_cube", CubeListBuilder.create().texOffs(18, 19).addBox(-1.0F, -1.5176F, 0.0682F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.0F, 1.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition lower = body.addOrReplaceChild("lower", CubeListBuilder.create(), PartPose.offset(1.5F, 6.0F, 7.6F));

        PartDefinition front_left_leg = lower.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(12, 19).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -1.0F, -2.5F));

        PartDefinition front_right_leg = lower.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(0, 20).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -1.0F, -2.5F));

        PartDefinition back_left_leg = lower.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(6, 20).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -1.0F, 2.5F));

        PartDefinition back_right_leg = lower.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(20, 15).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -1.0F, 2.5F));

        PartDefinition tail = lower.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, -0.0865F, 0.0933F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -2.0F, 2.0F));

        PartDefinition body3 = lower.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(0, 0).addBox(-2.9873F, -3.0F, -5.0754F, 6.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -3.0F, -1.0F));

        PartDefinition topspikes = lower.addOrReplaceChild("topspikes", CubeListBuilder.create(), PartPose.offset(1.0F, -6.0F, -3.0F));

        PartDefinition top2 = topspikes.addOrReplaceChild("top2", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(2.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition top3 = topspikes.addOrReplaceChild("top3", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(2.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 0.0F, 3.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition top4 = topspikes.addOrReplaceChild("top4", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(2.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, -2.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition top5 = topspikes.addOrReplaceChild("top5", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(2.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, 1.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition top6 = topspikes.addOrReplaceChild("top6", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(2.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, 4.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition top7 = topspikes.addOrReplaceChild("top7", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(2.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -2.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition top8 = topspikes.addOrReplaceChild("top8", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(2.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 1.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition top9 = topspikes.addOrReplaceChild("top9", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(2.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 4.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition top = topspikes.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(2.5F, 0.0F, 0.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 0.0F, -3.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition sideLeft = lower.addOrReplaceChild("sideLeft", CubeListBuilder.create(), PartPose.offset(4.0F, -3.5F, -3.0F));

        PartDefinition sideL = sideLeft.addOrReplaceChild("sideL", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -2.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition sideL2 = sideLeft.addOrReplaceChild("sideL2", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition sideL3 = sideLeft.addOrReplaceChild("sideL3", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition sideL4 = sideLeft.addOrReplaceChild("sideL4", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 2.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition sideL5 = sideLeft.addOrReplaceChild("sideL5", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition sideL6 = sideLeft.addOrReplaceChild("sideL6", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition sideL7 = sideLeft.addOrReplaceChild("sideL7", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 4.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition sideL8 = sideLeft.addOrReplaceChild("sideL8", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 4.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition side_Left2 = lower.addOrReplaceChild("side_Left2", CubeListBuilder.create(), PartPose.offset(-2.0F, -3.5F, -3.0F));

        PartDefinition sideL9 = side_Left2.addOrReplaceChild("sideL9", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -2.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition sideL10 = side_Left2.addOrReplaceChild("sideL10", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition sideL11 = side_Left2.addOrReplaceChild("sideL11", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition sideL12 = side_Left2.addOrReplaceChild("sideL12", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 2.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition sideL13 = side_Left2.addOrReplaceChild("sideL13", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition sideL14 = side_Left2.addOrReplaceChild("sideL14", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition sideL15 = side_Left2.addOrReplaceChild("sideL15", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 4.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition sideL16 = side_Left2.addOrReplaceChild("sideL16", CubeListBuilder.create().texOffs(1, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 4.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition roll = lower.addOrReplaceChild("roll", CubeListBuilder.create(), PartPose.offset(1.0F, -4.0F, -0.6F));

        PartDefinition cube_r1 = roll.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(6, 23).addBox(-4.5F, 1.0F, -3.0F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(HedgehogRenderState state) {
        super.setupAnim(state);

        // Reset tutte le parti del modello
        this.root().getAllParts().forEach(ModelPart::resetPose);

        // Gestione delle animazioni in base allo stato
        HedgehogEntity.HedgehogState currentState = state.getCurrentState();

        switch (currentState) {
            case ROLLING:
                this.rollUpAnimation.apply(state.rollUpAnimationState, state.ageInTicks);
                break;
            case SCARED:
                this.scaredAnimation.apply(state.rolledAnimationState, state.ageInTicks);
                break;
            case UNROLLING:
                this.rollOutAnimation.apply(state.rollOutAnimationState, state.ageInTicks);
                break;
            default: // IDLE
                // Applica PRIMA la camminata, POI l'idle (si sovrappongono)
                this.walkAnimation.apply(state.walkAnimationState, state.ageInTicks);
                this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
                break;
        }
    }



}