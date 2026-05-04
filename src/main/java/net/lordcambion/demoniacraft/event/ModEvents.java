package net.lordcambion.demoniacraft.event;


import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.item.custom.Hammer.HammerItem;
import net.lordcambion.demoniacraft.item.custom.Portal.UndeadStaff;
import net.lordcambion.demoniacraft.worldgen.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

import static net.lordcambion.demoniacraft.Demoniacraft.LOGGER;

@Mod.EventBusSubscriber(modid = Demoniacraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {






    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();


    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();

            // Previeni loop infiniti
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            // Ottieni tutti i blocchi da distruggere
            for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                // Salta il blocco iniziale (già rotto)
                if(pos.equals(initialBlockPos)) {
                    continue;
                }

                BlockState blockState = serverPlayer.level().getBlockState(pos);

                // Controlla se il blocco può essere rotto con questo tool
                if(!blockState.isAir() && blockState.getDestroySpeed(serverPlayer.level(), pos) >= 0) {
                    // Aggiungi alla lista per prevenire loop
                    HARVESTED_BLOCKS.add(pos);

                    // Rompi il blocco
                    serverPlayer.gameMode.destroyBlock(pos);

                    // Consuma durabilità (1 per ogni blocco extra)
                    mainHandItem.hurtAndBreak(1, serverPlayer, serverPlayer.getEquipmentSlotForItem(mainHandItem));
                    //System.out.println("Hammer breaking " + HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer).size() + " blocks");
                    // Rimuovi dalla lista
                    HARVESTED_BLOCKS.remove(pos);
                }
            }
        }
    }
}