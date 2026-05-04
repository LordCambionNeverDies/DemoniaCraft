package net.lordcambion.demoniacraft.item.custom.Tools;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.common.ToolActions;

// Piccone custom
public class ModPickaxeItem extends CustomToolItem {
    public ModPickaxeItem(Properties properties) {
        super(properties, ToolActions.DEFAULT_PICKAXE_ACTIONS);
    }

    // I picconi di solito non hanno azioni speciali con tasto destro
    @Override
    public InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }
}