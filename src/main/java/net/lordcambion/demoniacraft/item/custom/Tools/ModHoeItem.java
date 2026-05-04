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

public  class ModHoeItem extends CustomToolItem {
    public ModHoeItem(Properties properties) {
        super(properties, ToolActions.DEFAULT_HOE_ACTIONS);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);

        BlockState modifiedState = state.getToolModifiedState(context, ToolActions.HOE_TILL, false);
        if (modifiedState != null) {
            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide) {
                level.setBlock(pos, modifiedState, 11);
                context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}