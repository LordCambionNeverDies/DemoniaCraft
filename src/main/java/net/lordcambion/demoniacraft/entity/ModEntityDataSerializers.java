package net.lordcambion.demoniacraft.entity;

import net.lordcambion.demoniacraft.entity.custom.HedgehogEntity;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class ModEntityDataSerializers {
    public static final EntityDataSerializer<HedgehogEntity.HedgehogState> HEDGEHOG_STATE =
            EntityDataSerializer.forValueType(HedgehogEntity.HedgehogState.STREAM_CODEC);


    public static void register() {
        EntityDataSerializers.registerSerializer(HEDGEHOG_STATE);
    }
}