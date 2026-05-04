package net.lordcambion.demoniacraft.init;

import net.lordcambion.demoniacraft.Demoniacraft;

import net.lordcambion.demoniacraft.entity.projectile.EnderArrowEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Demoniacraft.MOD_ID);

    public static final RegistryObject<EntityType<EnderArrowEntity>> ENDER_ARROW =
            ENTITY_TYPES.register("ender_arrow",
                    () -> EntityType.Builder.<EnderArrowEntity>of(EnderArrowEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "ender_arrow"))));



    public static void register(BusGroup modBusGroup) {

        ENTITY_TYPES.register(modBusGroup);
    }
}