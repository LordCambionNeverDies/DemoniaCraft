package net.lordcambion.demoniacraft.loot;

import com.mojang.serialization.MapCodec;
import net.lordcambion.demoniacraft.Demoniacraft;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {

    public static final DeferredRegister<MapCodec<?extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZER=
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Demoniacraft.MOD_ID);

    public static final RegistryObject<MapCodec<?extends IGlobalLootModifier>>ADD_ITEM=
            LOOT_MODIFIER_SERIALIZER.register("add_item",()->AddItemModifier.CODEC);

    public static void register(BusGroup modbus){
        LOOT_MODIFIER_SERIALIZER.register(modbus);
    }
}
