package net.lordcambion.demoniacraft.block.custom.Entities;




import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.block.ModBlocks;
import net.lordcambion.demoniacraft.block.custom.Entities.custom.PedestalBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Demoniacraft.MOD_ID);

    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BE =
            BLOCK_ENTITIES.register("pedestal_be", () ->
                    new BlockEntityType<>(
                            PedestalBlockEntity::new,
                            Set.of(ModBlocks.PEDESTAL.get())
                    ));

    public static void register(BusGroup eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}