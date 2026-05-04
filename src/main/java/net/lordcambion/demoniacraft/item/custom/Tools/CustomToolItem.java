package net.lordcambion.demoniacraft.item.custom.Tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolAction;

import java.util.Set;

public abstract class CustomToolItem extends Item {
    private final Set<ToolAction> supportedActions;

    public CustomToolItem(Properties properties, Set<ToolAction> supportedActions) {
        super(properties);
        this.supportedActions = supportedActions;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return supportedActions.contains(toolAction);
    }

}