package net.lordcambion.demoniacraft.item.custom.Chisel;

import net.lordcambion.demoniacraft.block.ModBlocks;
import net.lordcambion.demoniacraft.sound.ModSound;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChiselItem extends Item {

    private static final Map<Block, Block> CHISEL_MAP = Stream.of(
            new AbstractMap.SimpleEntry<>(Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS),
            new AbstractMap.SimpleEntry<>(Blocks.COPPER_BLOCK, Blocks.CHISELED_COPPER),
            new AbstractMap.SimpleEntry<>(Blocks.WAXED_COPPER_BLOCK, Blocks.WAXED_CHISELED_COPPER),
            new AbstractMap.SimpleEntry<>(Blocks.EXPOSED_COPPER, Blocks.EXPOSED_CHISELED_COPPER),
            new AbstractMap.SimpleEntry<>(Blocks.WAXED_EXPOSED_COPPER, Blocks.WAXED_EXPOSED_CHISELED_COPPER),
            new AbstractMap.SimpleEntry<>(Blocks.OXIDIZED_COPPER, Blocks.OXIDIZED_CHISELED_COPPER),
            new AbstractMap.SimpleEntry<>(Blocks.WAXED_OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_CHISELED_COPPER),
            new AbstractMap.SimpleEntry<>(Blocks.WEATHERED_COPPER, Blocks.WEATHERED_CHISELED_COPPER),
            new AbstractMap.SimpleEntry<>(Blocks.WAXED_WEATHERED_COPPER, Blocks.WAXED_WEATHERED_CHISELED_COPPER),
            new AbstractMap.SimpleEntry<>(Blocks.QUARTZ_BLOCK, Blocks.CHISELED_QUARTZ_BLOCK),
            new AbstractMap.SimpleEntry<>(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE),
            new AbstractMap.SimpleEntry<>(Blocks.TUFF, Blocks.CHISELED_TUFF),//new
            new AbstractMap.SimpleEntry<>(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE),//new
            new AbstractMap.SimpleEntry<>(Blocks.NETHER_BRICKS, Blocks.CHISELED_NETHER_BRICKS),//new
            new AbstractMap.SimpleEntry<>(Blocks.POLISHED_BLACKSTONE, Blocks.CHISELED_POLISHED_BLACKSTONE),//new
            new AbstractMap.SimpleEntry<>(Blocks.RESIN_BRICKS, Blocks.CHISELED_RESIN_BRICKS),//new
            new AbstractMap.SimpleEntry<>(Blocks.TUFF_BRICKS, Blocks.CHISELED_TUFF_BRICKS),//new
            new AbstractMap.SimpleEntry<>(Blocks.SMOOTH_STONE, ModBlocks.PEDESTAL.get()),//new

            new AbstractMap.SimpleEntry<>(Blocks.DEEPSLATE, Blocks.CHISELED_DEEPSLATE)
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    public ChiselItem(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, TooltipDisplay pTooltipDisplay,
                                Consumer<Component> pTooltipAdder, TooltipFlag pFlag) {
        if(!Screen.hasShiftDown()){
            pTooltipAdder.accept(Component.translatable("tooltip.demoniacraft.shift_down"));
        }else{
            pTooltipAdder.accept(Component.translatable("tooltip.demoniacraft.chisel"));
        }

        super.appendHoverText(pStack, pContext, pTooltipDisplay, pTooltipAdder, pFlag);
    }
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level =pContext.getLevel();
        Block clickedBlock =level.getBlockState(pContext.getClickedPos()).getBlock();

        if(CHISEL_MAP.containsKey(clickedBlock)){
            if(!level.isClientSide()){
                level.setBlockAndUpdate(pContext.getClickedPos(),CHISEL_MAP.get(clickedBlock).defaultBlockState());

                pContext.getItemInHand().hurtAndBreak(1,((ServerLevel) level),
                        ((ServerPlayer) pContext.getPlayer()),
                        item -> pContext.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

                level.playSound(null,pContext.getClickedPos(), ModSound.CHISEL_USE.get(), SoundSource.BLOCKS);

            }
        }


        return InteractionResult.SUCCESS;
    }

}
