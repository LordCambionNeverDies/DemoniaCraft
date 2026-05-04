package net.lordcambion.demoniacraft.item.custom.Helmet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EnderHelmetItem extends Item {

    public EnderHelmetItem(Properties properties) {
        super(properties);
    }

    /**
     * Metodo chiamato da: mask.isMonsterDisguise(player, monster)
     * Deve essere definito nella classe Item.
     */
    @Override
    public boolean isMonsterDisguise(ItemStack stack, Player player, Monster monster) {

        return monster instanceof EnderMan;
    }
}