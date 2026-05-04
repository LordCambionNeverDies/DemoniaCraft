package net.lordcambion.demoniacraft.sound;

import net.lordcambion.demoniacraft.Demoniacraft;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSound {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS=
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Demoniacraft.MOD_ID);

    public static final RegistryObject<SoundEvent> CHISEL_USE =registerSoundEvent("chisel_use");

    public static final RegistryObject<SoundEvent> POOP =registerSoundEvent("poop");

    //canzoni
    public static final RegistryObject<SoundEvent> PANICDOX =registerSoundEvent("panicdox");
        public static final ResourceKey<JukeboxSong> PANICDOX_KEY = ResourceKey.create(Registries.JUKEBOX_SONG,
                ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID,"panicdox"));

         public static final RegistryObject<SoundEvent> CRYSIS =registerSoundEvent("crysis");
        public static final ResourceKey<JukeboxSong> CRYSIS_KEY = ResourceKey.create(Registries.JUKEBOX_SONG,
                ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID,"crysis"));

         public static final RegistryObject<SoundEvent> DYNAMITE =registerSoundEvent("dynamite");
        public static final ResourceKey<JukeboxSong> DYNAMITE_KEY = ResourceKey.create(Registries.JUKEBOX_SONG,
                ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID,"dynamite"));

    public static final RegistryObject<SoundEvent> DIAMONDS =registerSoundEvent("diamonds");
    public static final ResourceKey<JukeboxSong> DIAMONDS_KEY = ResourceKey.create(Registries.JUKEBOX_SONG,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID,"diamonds"));


    private static ResourceKey<JukeboxSong> create(String pName) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.withDefaultNamespace(pName));
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return  SOUND_EVENTS.register(name,
                () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID,name)));
    }

    public  static void register(BusGroup modBusGroup){
        SOUND_EVENTS.register(modBusGroup);
    }
}
