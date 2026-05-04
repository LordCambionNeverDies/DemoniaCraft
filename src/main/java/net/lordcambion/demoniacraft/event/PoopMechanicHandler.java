package net.lordcambion.demoniacraft.event;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.item.ModItems;
import net.lordcambion.demoniacraft.sound.ModSound;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Demoniacraft.MOD_ID)
public class PoopMechanicHandler {

    // Mappa per tracciare quanto tempo ogni giocatore tiene premuto shift
    private static final Map<UUID, Integer> shiftTimers = new HashMap<>();
    private static final int REQUIRED_TICKS = 100; // 5 secondi = 100 ticks (20 ticks per secondo)

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // Inizializza il timer quando il giocatore entra
        if (event.getEntity() instanceof ServerPlayer) {
            shiftTimers.put(event.getEntity().getUUID(), 0);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        // Pulisci il timer quando il giocatore esce
        shiftTimers.remove(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        // Controlla tutti i giocatori connessi
        event.getServer().getPlayerList().getPlayers().forEach(player -> {
            checkPlayerPoop(player);
        });
    }

    private static void checkPlayerPoop(ServerPlayer player) {
        UUID playerId = player.getUUID();

        // Controlla se il giocatore ha la barra della fame piena (20 punti fame)
        boolean isFullHunger = player.getFoodData().getFoodLevel() >= 20;

        // Controlla se il giocatore sta tenendo premuto shift
        boolean isSneaking = player.isShiftKeyDown();

        if (isFullHunger && isSneaking) {
            // Incrementa il timer per questo giocatore
            int currentTime = shiftTimers.getOrDefault(playerId, 0);
            currentTime++;
            shiftTimers.put(playerId, currentTime);

            // Debug per vedere il progresso ogni secondo
            if (currentTime % 20 == 0) {
                //player.sendSystemMessage(Component.literal("Progresso cacca: " + (currentTime / 20) + "/5 secondi"));
            }

            // Se ha raggiunto i 5 secondi
            if (currentTime >= REQUIRED_TICKS) {
                // Spawna la cacca
                spawnPoop(player);

                // Reset del timer
                shiftTimers.remove(playerId);

                // Riduci la fame di 1 punto dopo aver fatto la cacca
                player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - 1);
                player.level().playSound(
                        null, // null = tutti sentono
                        player.getX(), player.getY(), player.getZ(), // posizione
                        ModSound.POOP.get(), // suono
                        net.minecraft.sounds.SoundSource.PLAYERS, // categoria suono
                        1.0f, // volume
                        1.0f);  // pitch
                //player.sendSystemMessage(Component.literal("Hai fatto la cacca!"));
            }
        } else {
            // Se non sta più sneakando o non ha più la fame piena, reset del timer
            if (shiftTimers.containsKey(playerId)) {
                shiftTimers.remove(playerId);
            }
        }
    }

    private static void spawnPoop(ServerPlayer player) {
        // Crea l'item stack della cacca
        ItemStack poopStack = new ItemStack(ModItems.POOP.get());

        // Spawna l'item nel mondo alla posizione del giocatore
        ItemEntity poopEntity = new ItemEntity(
                player.level(),
                player.getX(),
                player.getY(),
                player.getZ(),
                poopStack
        );

        // Aggiungi un piccolo movimento casuale all'item
        poopEntity.setDeltaMovement(
                (player.getRandom().nextDouble() - 0.5) * 0.1,
                0.2,
                (player.getRandom().nextDouble() - 0.5) * 0.1
        );

        // Spawna l'entità nel mondo
        player.level().addFreshEntity(poopEntity);
    }
}