package net.lordcambion.demoniacraft.event;

import net.lordcambion.demoniacraft.item.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.phys.Vec3;


@Mod.EventBusSubscriber
public class ModArmorEvents {

    // distanza del teletrasporto
    private static final double TELEPORT_DISTANCE = 5.0;

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // controlla se indossa armatura completa di Arkadium
        if (isWearingFullEnder(player)) {
            LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity le ? le : null;
            if (attacker != null) {
                // calcola nuova posizione
                Vec3 pos = player.position();
                double xOffset = Math.random() * TELEPORT_DISTANCE * 2 - TELEPORT_DISTANCE; // casuale X
                double zOffset = Math.random() * TELEPORT_DISTANCE * 2 - TELEPORT_DISTANCE; // casuale Z
                double yOffset = TELEPORT_DISTANCE; // in aria
                attacker.teleportTo(pos.x + xOffset, pos.y + yOffset, pos.z + zOffset);
            }
        }
    }
//    @SubscribeEvent
//    public static void onEndermanTarget(LivingChangeTargetEvent event) {
//        if (!(event.getEntity() instanceof EnderMan enderman)) return;
//
//        // debug: registra quando l'evento viene lanciato
//        LOGGER.debug("LivingChangeTargetEvent fired: entity={} original={} new={}",
//                event.getEntity(), event.getOriginalTarget(), event.getNewTarget());
//
//        // controlliamo sia original che new target (più robusto)
//        LivingEntity orig = event.getOriginalTarget();
//        LivingEntity newT = event.getNewTarget();
//
//        Player player = null;
//        if (orig instanceof Player p) player = p;
//        else if (newT instanceof Player p2) player = p2;
//
//        if (player == null) return;
//
//        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
//        if (!helmet.isEmpty() && helmet.getItem() == ModItems.ENDER_HELMET.get()) {
//            // metodo più affidabile: forziamo il nuovo target a null (il mob non punterà al player)
//            event.setNewTarget(null);
//            LOGGER.info("Prevented Enderman {} from targeting player {}", enderman.getUUID(), player.getName().getString());
//        }
//    }


    private static boolean isWearingFullEnder(Player player) {
        ItemStack helmet = player.getInventory().getEquipment().get(EquipmentSlot.HEAD); // slot casco
        ItemStack chest = player.getInventory().getEquipment().get(EquipmentSlot.CHEST);  // chestplate
        ItemStack legs = player.getInventory().getEquipment().get(EquipmentSlot.LEGS);   // leggings
        ItemStack boots = player.getInventory().getEquipment().get(EquipmentSlot.FEET);  // stivali

        // qui metti i tuoi item di Arkadium, ad esempio ArkadiumItems.HELMET
        return !helmet.isEmpty() && !chest.isEmpty() && !legs.isEmpty() && !boots.isEmpty()
                && helmet.getItem() == ModItems.ENDER_HELMET.get() // sostituire con Arkadium helmet
                && chest.getItem() == ModItems.ENDER_CHESTPLATE.get()
                && legs.getItem() == ModItems.ENDER_LEGGINGS.get()
                && boots.getItem() == ModItems.ENDER_BOOTS.get();

    }
}
