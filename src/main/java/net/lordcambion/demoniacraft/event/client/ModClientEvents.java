package net.lordcambion.demoniacraft.event.client;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.entity.ModEntities;
import net.lordcambion.demoniacraft.entity.client.bonereaper.BoneReaperModel;
import net.lordcambion.demoniacraft.entity.client.bonereaper.BoneReaperRenderer;
import net.lordcambion.demoniacraft.item.ModItems;
import net.lordcambion.demoniacraft.potion.ModPotions;
import net.lordcambion.demoniacraft.sound.ModSound;
import net.minecraft.client.Minecraft; // Import necessario
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Demoniacraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onComputeFovModifierEvent(ComputeFovModifierEvent event){

        // --- NUOVO CONTROLLO AGGIUNTO QUI ---
        // Verifica se la visuale è in prima persona
        if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            return; // Se NON è in prima persona, esci e non applicare lo zoom.
        }
        // ------------------------------------

        if(event.getPlayer().isUsingItem()&&event.getPlayer().getUseItem().getItem()== ModItems.ENDER_BOW.get()){

            // Ho sostituito il tuo codice originale con la logica corretta per lo zoom progressivo che abbiamo discusso
            int maxBowDrawTicks = 20;
            float pullProgress = (float)event.getPlayer().getTicksUsingItem() / maxBowDrawTicks;

            if(pullProgress > 1.0f) {
                pullProgress = 1.0f;
            }

            // Imposta lo zoom massimo desiderato (es. 0.80 per uno zoom moderato)
            float minFovModifier = 0.80f;
            float fovReductionFactor = 1.0f - minFovModifier;

            float fovModifier = 1.0f - (fovReductionFactor * pullProgress);

            event.setNewFovModifier(fovModifier);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event){
        if(event.getEntity() instanceof Sheep sheep && event.getSource().getDirectEntity() instanceof ServerPlayer player){
            if(player.getMainHandItem().getItem()== Items.END_ROD){
                player.sendSystemMessage(Component.literal(player.getName().getString()+" Hit a Sheep with and End Rod! YOU SICK FREAK!"));
                sheep.addEffect(new MobEffectInstance(MobEffects.INFESTED,600,5));
                player.getMainHandItem().shrink(1);
                sheep.spawnAtLocation(player.level().getLevel(), ModItems.POOP.get());
                player.level().playSound(
                        null, // null = tutti sentono
                        player.getX(), player.getY(), player.getZ(), // posizione
                        ModSound.POOP.get(), // suono
                        net.minecraft.sounds.SoundSource.PLAYERS, // categoria suono
                        1.0f, // volume
                        1.0f);  // pitch
            }
        }
    }

    @SubscribeEvent
    public  static void onBrewingRecipeRegsiter(BrewingRecipeRegisterEvent event){
        PotionBrewing.Builder builder= event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.BOUNCING_POTION.getHolder().get());
        builder.addMix(Potions.AWKWARD, Items.FERMENTED_SPIDER_EYE, ModPotions.CLIMBING_POTION.getHolder().get());
    }




}