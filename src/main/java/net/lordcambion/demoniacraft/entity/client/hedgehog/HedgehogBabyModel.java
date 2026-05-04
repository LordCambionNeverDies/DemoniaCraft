package net.lordcambion.demoniacraft.entity.client.hedgehog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HedgehogBabyModel extends HedgehogModel {
    public HedgehogBabyModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        // Usa lo stesso layer definition dell'adulto
        // Il scaling viene gestito automaticamente dal renderer
        return HedgehogModel.createBodyLayer();
    }

    // Override del setupAnim se necessario per animazioni specifiche dei baby
    @Override
    public void setupAnim(HedgehogRenderState state) {
        super.setupAnim(state);
        // Eventuali modifiche specifiche per i baby possono essere aggiunte qui
    }
}