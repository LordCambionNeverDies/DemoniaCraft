package net.lordcambion.demoniacraft.event;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.lordcambion.demoniacraft.item.ModItems;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Demoniacraft.MOD_ID)
public class ChiselEventHandler {

    private static final Map<UUID, Boolean> wasDamagedState = new HashMap<>();
    private static final Map<UUID, ItemState> playerItemStates = new HashMap<>();
    private static final float DAMAGE_THRESHOLD = 0.5f;

    private static class ItemState {
        public final UUID itemUUID;
        public final boolean isDamaged;

        public ItemState(UUID itemUUID, boolean isDamaged) {
            this.itemUUID = itemUUID;
            this.isDamaged = isDamaged;
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() == ModItems.CHISEL.get()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                handleChiselInteraction(stack, player);
            }
        } else {
            // Reset state if not holding chisel
            if (event.getEntity() instanceof ServerPlayer player) {
                wasDamagedState.remove(player.getUUID());
            }
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        // Reset state when crafting new items
        if (event.getEntity() instanceof ServerPlayer player) {
            playerItemStates.remove(player.getUUID());
            wasDamagedState.remove(player.getUUID());
        }
    }

    private static void handleChiselInteraction(ItemStack stack, ServerPlayer player) {
        UUID playerId = player.getUUID();
        UUID itemId = getItemUUID(stack);

        // Get current damage state
        boolean isCurrentlyDamaged = calculateDamageState(stack);
        boolean wasDamaged = wasDamagedState.getOrDefault(playerId, false);

        // Check if this is a new item or state changed
        ItemState currentState = playerItemStates.get(playerId);
        boolean isNewItem = currentState == null || !currentState.itemUUID.equals(itemId);
        boolean stateChanged = isNewItem || (currentState.isDamaged != isCurrentlyDamaged);

//        player.sendSystemMessage(Component.literal("Chisel - Danno: " + stack.getDamageValue() + "/" + stack.getMaxDamage() +
//                ", Danneggiato: " + isCurrentlyDamaged + ", EraDanneggiato: " + wasDamaged +
//                ", NuovoItem: " + isNewItem + ", StatoCambiato: " + stateChanged));

        // Trigger effects only when crossing the threshold
        if (stateChanged && !wasDamaged && isCurrentlyDamaged) {
            playBreakEffects(player);
            player.sendSystemMessage(Component.literal("SUONO RIPRODUOTTO! Chisel ha superato la soglia di danno"));
        }

        // Update states
        wasDamagedState.put(playerId, isCurrentlyDamaged);
        playerItemStates.put(playerId, new ItemState(itemId, isCurrentlyDamaged));
    }

    private static boolean calculateDamageState(ItemStack stack) {
        int damage = stack.getDamageValue();
        int maxDamage = stack.getMaxDamage();

        if (maxDamage <= 0) return false;

        double damagePercentage = (double) damage / maxDamage;
        return damagePercentage >= DAMAGE_THRESHOLD;
    }

    private static UUID getItemUUID(ItemStack stack) {
        // Create a unique ID based on item data and damage
        // This helps track when the player switches to a different chisel
        String uniqueData = stack.getDamageValue() + "-" + System.identityHashCode(stack);
        return UUID.nameUUIDFromBytes(uniqueData.getBytes());
    }

    private static void playBreakEffects(ServerPlayer player) {
        // Play break sound
        player.level().playSound(null, player.blockPosition(), SoundEvents.ITEM_BREAK.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

        // Spawn break particles
        for (int i = 0; i < 8; i++) {
            double x = player.getX() + (player.getRandom().nextDouble() - 0.5) * 1.5;
            double y = player.getY() + 1.0 + player.getRandom().nextDouble() * 1.5;
            double z = player.getZ() + (player.getRandom().nextDouble() - 0.5) * 1.5;

            player.getServer().getLevel(player.level().dimension()).sendParticles(
                    ParticleTypes.CRIT,
                    x, y, z,
                    3,
                    0.2, 0.2, 0.2,
                    0.05
            );

            player.getServer().getLevel(player.level().dimension()).sendParticles(
                    ParticleTypes.SMOKE,
                    x, y, z,
                    2,
                    0.1, 0.1, 0.1,
                    0.02
            );
        }
    }
}