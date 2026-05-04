package net.lordcambion.demoniacraft.item.custom.Tools;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.common.ToolActions;

public class ModSwordItem extends CustomToolItem {
    public ModSwordItem(Properties properties) {
        super(properties, ToolActions.DEFAULT_SWORD_ACTIONS);
    }

    // Aggiungi qui logiche specifiche per le spade se necessario
    @Override
    public InteractionResult useOn(UseOnContext context) {
        // Logica specifica per spade (es. sweep attack, etc.)
        return InteractionResult.PASS;
    }
}