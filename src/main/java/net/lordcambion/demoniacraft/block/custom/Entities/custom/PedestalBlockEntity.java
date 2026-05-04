package net.lordcambion.demoniacraft.block.custom.Entities.custom;

import net.lordcambion.demoniacraft.block.custom.Entities.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PedestalBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }
    };
    private float rotation;

    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PEDESTAL_BE.get(), pPos, pBlockState);
    }
public float getRenderingRotation(){
        rotation+=0.5f;
        if(rotation>=360){
            rotation=0;
        }
        return rotation;
}

    // Salva l'inventario
    @Override
    protected void saveAdditional(ValueOutput pOutput) {
        super.saveAdditional(pOutput);
        // Usa hasLevel() per verificare se level è disponibile
        if (hasLevel()) {
            pOutput.store("inventory", CompoundTag.CODEC, inventory.serializeNBT(level.registryAccess()));
        }
    }

    // Carica l'inventario
    @Override
    protected void loadAdditional(ValueInput pInput) {
        super.loadAdditional(pInput);
        pInput.read("inventory", CompoundTag.CODEC).ifPresent(tag -> {
            // Usa il lookup provider dal ValueInput invece di level
            if (pInput.lookup() != null) {
                inventory.deserializeNBT(pInput.lookup(), tag);
            }
        });
    }

    // Sincronizzazione client-server
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        CompoundTag tag = super.getUpdateTag(pRegistries);
        tag.put("inventory", inventory.serializeNBT(pRegistries));
        return tag;
    }

    // Metodo per gestire i drop quando il blocco viene distrutto
    public void drops() {
        if (level == null || level.isClientSide()) return;

        ItemStack stack = inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), stack);
        }
    }

    // Metodo per pulire l'inventario
    public void clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
        setChanged();
    }
}