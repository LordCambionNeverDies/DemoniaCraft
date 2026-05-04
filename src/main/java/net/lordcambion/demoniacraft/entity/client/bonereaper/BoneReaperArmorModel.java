package net.lordcambion.demoniacraft.entity.client.bonereaper;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BoneReaperArmorModel extends HumanoidModel<BoneReaperRenderState> {

    public BoneReaperArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createInnerArmorLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Rimuovi la parte head e hat esistenti e ricreale con l'offset corretto
        partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16)
                        .addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        // Head con hat incluso
        PartDefinition head = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // Hat come child di head
        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        partdefinition.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.25F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16).mirror()
                        .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.25F)),
                PartPose.offset(5.0F, 2.0F, 0.0F));

        partdefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.25F)),
                PartPose.offset(-2.0F, 12.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16).mirror()
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.25F)),
                PartPose.offset(2.0F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createOuterArmorLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16)
                        .addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        partdefinition.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.5F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16).mirror()
                        .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.5F)),
                PartPose.offset(5.0F, 2.0F, 0.0F));

        partdefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.5F)),
                PartPose.offset(-2.0F, 12.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16).mirror()
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.5F)),
                PartPose.offset(2.0F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}