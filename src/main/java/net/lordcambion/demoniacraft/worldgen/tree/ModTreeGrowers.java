package net.lordcambion.demoniacraft.worldgen.tree;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public  static final TreeGrower WALNUT = new TreeGrower(Demoniacraft.MOD_ID+ ":walnut",
            Optional.empty(),Optional.of(ModConfiguredFeatures.WALNUT_TREE_KEY),Optional.empty());
}
