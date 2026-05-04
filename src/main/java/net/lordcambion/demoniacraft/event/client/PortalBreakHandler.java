package net.lordcambion.demoniacraft.event.client;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.worldgen.portal.DemonPortalStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Demoniacraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PortalBreakHandler {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();

        // Controlla se la rottura di un blocco deve rompere il portale
        DemonPortalStructure.checkAndBreakPortal(level, pos);
    }
}