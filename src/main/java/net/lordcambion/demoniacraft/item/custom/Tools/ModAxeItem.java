package net.lordcambion.demoniacraft.item.custom.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;

public  class ModAxeItem extends CustomToolItem {
    public ModAxeItem(Properties properties) {
        super(properties, ToolActions.DEFAULT_AXE_ACTIONS);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);

        // Scortecciare
        BlockState strippedState = state.getToolModifiedState(context, ToolActions.AXE_STRIP, false);
        if (strippedState != null) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide) {
                level.setBlock(pos, strippedState, 11);
                context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            return InteractionResult.SUCCESS;
        }

        // Rimuovere ossidazione
        BlockState scrapedState = state.getToolModifiedState(context, ToolActions.AXE_SCRAPE, false);
        if (scrapedState != null) {
            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3005, pos, 0);
            if (!level.isClientSide) {
                level.setBlock(pos, scrapedState, 11);
                context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            return InteractionResult.SUCCESS;
        }

        // Rimuovere cera
        BlockState waxOffState = state.getToolModifiedState(context, ToolActions.AXE_WAX_OFF, false);
        if (waxOffState != null) {
            level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3004, pos, 0);
            if (!level.isClientSide) {
                level.setBlock(pos, waxOffState, 11);
                context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
