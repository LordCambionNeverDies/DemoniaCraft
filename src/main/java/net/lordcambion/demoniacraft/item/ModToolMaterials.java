package net.lordcambion.demoniacraft.item;

import net.lordcambion.demoniacraft.util.ModTags;
import net.minecraft.world.item.ToolMaterial;

public class ModToolMaterials {
    public static final ToolMaterial ARKADIUM = new ToolMaterial(
            ModTags.Blocks.INCORRECT_FOR_ARKADIUM_TOOL, // Blocks that can't be mined
            2031,                                       // Durability
            4.0F,                                       // Mining speed (deve essere float)
            3.0F,                                       // Attack damage bonus
            20,                                         // Enchantment value
            ModTags.Items.ARKADIUM_REPAIR               // Repair items tag
    );
}