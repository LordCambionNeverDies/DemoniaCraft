package net.lordcambion.demoniacraft.entity;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.entity.custom.BoneReaperEntity;
import net.lordcambion.demoniacraft.entity.custom.ChairEntity;
import net.lordcambion.demoniacraft.entity.custom.HedgehogEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Demoniacraft.MOD_ID);

    //public static ResourceKey<EntityType<?>> HEDGEHOG_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.withDefaultNamespace("hedgehog"));


    public static ResourceKey<EntityType<?>> HEDGEHOG_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "hedgehog"));

    public static ResourceKey<EntityType<?>> BONEREAPER_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "bonereaper"));

    public static ResourceKey<EntityType<?>> CHAIR_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "chair_entity"));

    public static final RegistryObject<EntityType<HedgehogEntity>> HEDGEHOG =
            ENTITY_TYPES.register("hedgehog", () -> EntityType.Builder.of(HedgehogEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.32f)
                    .build(HEDGEHOG_KEY)); // CORREGGI: usa toString()

    public static final RegistryObject<EntityType<BoneReaperEntity>> BONEREAPER =
            ENTITY_TYPES.register("bonereaper", () -> EntityType.Builder.of(BoneReaperEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 2.4f)
                    .build(BONEREAPER_KEY)); // CORREGGI: usa toString()

    public static final RegistryObject<EntityType<ChairEntity>> CHAIR =
            ENTITY_TYPES.register("chair_entity", () -> EntityType.Builder.of(ChairEntity::new, MobCategory.MISC)
                    .sized(0.7f, 2.4f)
                    .build(CHAIR_KEY)); // CORREGGI: usa toString()
    public static void register(BusGroup modbus){
        ENTITY_TYPES.register(modbus);
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BONEREAPER.get(), BoneReaperEntity.createAttributes().build());
    }



}
