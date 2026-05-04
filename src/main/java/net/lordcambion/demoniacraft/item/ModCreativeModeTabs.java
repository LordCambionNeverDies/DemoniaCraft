package net.lordcambion.demoniacraft.item;


import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS=
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Demoniacraft.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ARKADIUM_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("arkadium_items_tab",
                    ()->CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.ARKADIUM_INGOT.get()))
                            .title(Component.translatable("creativetab.demoniacraft.arkadium_items"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(ModItems.ARKADIUM_INGOT.get());
                                pOutput.accept((ModItems.RAW_ARKADIUM.get()));

                            }).build());

    public static final RegistryObject<CreativeModeTab> TOOLS_TAB =
            CREATIVE_MODE_TABS.register("tools_tab",
                    ()->CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.CHISEL.get()))
                            .title(Component.translatable("creativetab.demoniacraft.tools"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept((ModItems.CHISEL.get()));

                                pOutput.accept((ModItems.ARKADIUM_PICKAXE.get()));
                                pOutput.accept((ModItems.ARKADIUM_AXE.get()));
                                pOutput.accept((ModItems.ARKADIUM_SHOVEL.get()));
                                pOutput.accept((ModItems.ARKADIUM_HOE.get()));


                                pOutput.accept((ModItems.ARKADIUM_HAMMER.get()));
                                pOutput.accept((ModItems.GOLDEN_HAMMER.get()));
                                pOutput.accept((ModItems.IRON_HAMMER.get()));
                                pOutput.accept((ModItems.DIAMOND_HAMMER.get()));
                                pOutput.accept((ModItems.NETHERITE_HAMMER.get()));
                            }).build());

    public static final RegistryObject<CreativeModeTab> COMBAT_TAB =
            CREATIVE_MODE_TABS.register("combat_tab",
                    ()->CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.ARKADIUM_SWORD.get()))
                            .title(Component.translatable("creativetab.demoniacraft.combat"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept((ModItems.ARKADIUM_SWORD.get()));
                                pOutput.accept((ModItems.DEMON_SCYTHE.get()));


                                pOutput.accept((ModItems.ENDER_BOW.get()));
                                pOutput.accept((ModItems.ENDER_ARROW.get()));

                                pOutput.accept((ModItems.ARKADIUM_HELMET.get()));
                                pOutput.accept((ModItems.ARKADIUM_CHESTPLATE.get()));
                                pOutput.accept((ModItems.ARKADIUM_LEGGINGS.get()));
                                pOutput.accept((ModItems.ARKADIUM_BOOTS.get()));
                                pOutput.accept((ModItems.ARKADIUM_HORSE_ARMOR.get()));

                                pOutput.accept((ModItems.ENDER_HELMET.get()));
                                pOutput.accept((ModItems.ENDER_CHESTPLATE.get()));
                                pOutput.accept((ModItems.ENDER_LEGGINGS.get()));
                                pOutput.accept((ModItems.ENDER_BOOTS.get()));


                                pOutput.accept((ModItems.ARKADIUM_HAMMER.get()));
                                pOutput.accept((ModItems.GOLDEN_HAMMER.get()));
                                pOutput.accept((ModItems.IRON_HAMMER.get()));
                                pOutput.accept((ModItems.DIAMOND_HAMMER.get()));
                                pOutput.accept((ModItems.NETHERITE_HAMMER.get()));

                            }).build());


    public static final RegistryObject<CreativeModeTab> FOOD_TAB =
            CREATIVE_MODE_TABS.register("food_tab",
                    ()->CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.POOP.get()))
                            .title(Component.translatable("creativetab.demoniacraft.food"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(ModItems.POOP.get());
                                pOutput.accept(ModItems.STRAWBERRY.get());
                                pOutput.accept(ModItems.TOMATO.get());
                                pOutput.accept(ModItems.TOMATO_SEEDS.get());
                            }).build());

    public static final RegistryObject<CreativeModeTab> MISCELLANEOUS_TAB =
            CREATIVE_MODE_TABS.register("miscellaneous_tab",
                    ()->CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.PYRESTONE.get()))
                            .title(Component.translatable("creativetab.demoniacraft.miscellaneous"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept((ModItems.GLUE_BOTTLE.get()));
                                pOutput.accept((ModItems.PYRESTONE.get()));
                                pOutput.accept(ModItems.CRYSIS_DISC.get());
                                pOutput.accept(ModItems.DYNAMITE_DISC.get());
                                pOutput.accept(ModItems.PANICDOX_DISC.get());
                                pOutput.accept(ModItems.DIAMONDS_DISC.get());
                                pOutput.accept(ModItems.HEDGEHOG_SPAWN_EGG.get());
                                pOutput.accept(ModItems.BONEREAPER_SPAWN_EGG.get());
                                pOutput.accept(ModItems.UNDEAD_STAFF.get());
                                pOutput.accept(ModItems.SHOCKWAVE_STAFF.get());
                            }).build());

     public static final RegistryObject<CreativeModeTab> BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("blocks_tab",
                    ()->CreativeModeTab.builder().icon(()-> new ItemStack(ModBlocks.ARKADIUM_BLOCK.get()))
                            .withTabsBefore(ARKADIUM_ITEMS_TAB.getId())
                            .title(Component.translatable("creativetab.demoniacraft.blocks"))
                            .displayItems((pParameters, pOutput) -> {
                                //ores
                                pOutput.accept(ModBlocks.ARKADIUM_BLOCK.get());
                                pOutput.accept(ModBlocks.ARKADIUM_ORE.get());
                                pOutput.accept(ModBlocks.ARKADIUM_DEEPSLATE_ORE.get());
                                pOutput.accept(ModBlocks.ARKADIUM_NETHER_ORE.get());
                                pOutput.accept(ModBlocks.ARKADIUM_END_ORE.get());
                                pOutput.accept(ModBlocks.PYRESTONE_ORE.get());
                                pOutput.accept(ModBlocks.DARK_STONE_BLOCK.get());
                                pOutput.accept(ModBlocks.DARK_COBBLESTONE_BLOCK.get());

                                pOutput.accept(ModBlocks.GLUE_BLOCK.get());
                                pOutput.accept(ModBlocks.COPPER_LAMP.get());
                                //obsidian
                                pOutput.accept(ModBlocks.OBSIDIAN_STAIRS.get());
                                pOutput.accept(ModBlocks.OBSIDIAN_SLAB.get());
                                pOutput.accept(ModBlocks.OBSIDIAN_WALL.get());
                                pOutput.accept(ModBlocks.OBSIDIAN_BUTTON.get());
                                pOutput.accept(ModBlocks.OBSIDIAN_PRESSURE_PLATE.get());
                                //walnut
                                pOutput.accept(ModBlocks.WALNUT_LOG.get());
                                pOutput.accept(ModBlocks.WALNUT_WOOD.get());
                                pOutput.accept(ModBlocks.STRIPPED_WALNUT_LOG.get());
                                pOutput.accept(ModBlocks.STRIPPED_WALNUT_WOOD.get());
                                pOutput.accept(ModBlocks.WALNUT_PLANKS.get());
                                pOutput.accept(ModBlocks.WALNUT_STAIRS.get());
                                pOutput.accept(ModBlocks.WALNUT_SLAB.get());
                                pOutput.accept(ModBlocks.WALNUT_FENCE.get());
                                pOutput.accept(ModBlocks.WALNUT_FENCE_GATE.get());
                                pOutput.accept(ModBlocks.WALNUT_BUTTON.get());
                                pOutput.accept(ModBlocks.WALNUT_PRESSURE_PLATE.get());
                                pOutput.accept(ModBlocks.WALNUT_DOOR.get());
                                pOutput.accept(ModBlocks.WALNUT_TRAPDOOR.get());
                                pOutput.accept(ModBlocks.WALNUT_LEAVES.get());

                                pOutput.accept(ModBlocks.WALNUT_SAPLING.get());
                                //chairs
                                pOutput.accept(ModBlocks.ACACIA_CHAIR.get());
                                pOutput.accept(ModBlocks.BAMBOO_CHAIR.get());
                                pOutput.accept(ModBlocks.BIRCH_CHAIR.get());
                                pOutput.accept(ModBlocks.CHERRY_CHAIR.get());
                                pOutput.accept(ModBlocks.CRIMSON_CHAIR.get());
                                pOutput.accept(ModBlocks.DARK_OAK_CHAIR.get());
                                pOutput.accept(ModBlocks.JUNGLE_CHAIR.get());
                                pOutput.accept(ModBlocks.MANGROVE_CHAIR.get());
                                pOutput.accept(ModBlocks.OAK_CHAIR.get());
                                pOutput.accept(ModBlocks.PALE_OAK_CHAIR.get());
                                pOutput.accept(ModBlocks.SPRUCE_CHAIR.get());
                                pOutput.accept(ModBlocks.WALNUT_CHAIR.get());
                                pOutput.accept(ModBlocks.WARPED_CHAIR.get());

                                //pedestal
                                pOutput.accept(ModBlocks.PEDESTAL.get());


                            }).build());




    public static void register(BusGroup eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
