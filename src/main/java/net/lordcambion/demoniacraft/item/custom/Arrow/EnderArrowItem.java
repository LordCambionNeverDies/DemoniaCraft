package net.lordcambion.demoniacraft.item.custom.Arrow;

import net.lordcambion.demoniacraft.entity.projectile.EnderArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class EnderArrowItem extends ArrowItem {

    public EnderArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level pLevel, ItemStack pAmmo, LivingEntity pShooter, @Nullable ItemStack pWeapon) {
        return new EnderArrowEntity(pLevel, pShooter, pAmmo.copyWithCount(1), pWeapon);
    }
}