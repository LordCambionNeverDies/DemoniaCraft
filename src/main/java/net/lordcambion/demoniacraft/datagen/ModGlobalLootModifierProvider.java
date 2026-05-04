package net.lordcambion.demoniacraft.datagen;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.item.ModItems;
import net.lordcambion.demoniacraft.loot.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Demoniacraft.MOD_ID, registries);
    }

    @Override
    protected void start(HolderLookup.Provider registries) {
            this.add("tomato_seeds_from_short_grass",
                    new AddItemModifier(new LootItemCondition[]{
                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS).build(),
                            LootItemRandomChanceCondition.randomChance(0.25f).build()}, ModItems.TOMATO_SEEDS.get()
                    ));
        this.add("tomato_seeds_from_tall_grass",
                new AddItemModifier(new LootItemCondition[]{
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                        LootItemRandomChanceCondition.randomChance(0.05f).build()}, ModItems.TOMATO_SEEDS.get()
                ));

        this.add("tomato_seeds_from_savana_grass",
                new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition(ResourceLocation.withDefaultNamespace("chests/village/village_savanna_house"))
                }, ModItems.TOMATO_SEEDS.get()
                ));

        this.add("chisel_from_jungle_temple",
                new AddItemModifier(new LootItemCondition[]{
                       new LootTableIdCondition(ResourceLocation.withDefaultNamespace("chests/jungle_temple"))
                }, ModItems.CHISEL.get()
                ));

        this.add("chisel_from_jungle_temple",
                new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition(ResourceLocation.withDefaultNamespace("entities/zombie")),
                        (LootItemRandomChanceCondition.randomChance(0.5f).build())
                }, ModItems.TOMATO_SEEDS.get()
                ));

    }
}
