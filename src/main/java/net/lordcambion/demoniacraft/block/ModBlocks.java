package net.lordcambion.demoniacraft.block;

import net.lordcambion.demoniacraft.block.custom.*;
import net.lordcambion.demoniacraft.block.custom.Chair.ChairBlock;
import net.lordcambion.demoniacraft.block.custom.Pedestal.PedestalBlock;
import net.lordcambion.demoniacraft.block.custom.nonMovable.*;
import net.lordcambion.demoniacraft.Demoniacraft;

import net.lordcambion.demoniacraft.block.custom.Leaves.WalnutLeavesBlock;
import net.lordcambion.demoniacraft.item.ModItems;
import net.lordcambion.demoniacraft.worldgen.tree.ModTreeGrowers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS=
            DeferredRegister.create(ForgeRegistries.BLOCKS, Demoniacraft.MOD_ID);

    public static final RegistryObject<Block> DARK_STONE_BLOCK =registerBlock("dark_stone",
            ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .setId(BLOCKS.key("dark_stone")
                    )));

    public static final RegistryObject<Block> DARK_COBBLESTONE_BLOCK =registerBlock("dark_cobblestone",
            ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
                    .requiresCorrectToolForDrops()
                    .setId(BLOCKS.key("dark_cobblestone")
                    )));

    public static final RegistryObject<Block> ARKADIUM_BLOCK =registerBlock("arkadium_block",
            ()-> new Block(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL).setId(BLOCKS.key("arkadium_block"))));


    public static final RegistryObject<Block> ARKADIUM_ORE =registerBlock("arkadium_ore",
            ()-> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(2.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE).setId(BLOCKS.key("arkadium_ore"))));

    public static final RegistryObject<Block> ARKADIUM_END_ORE =registerBlock("arkadium_end_ore",
            ()-> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(2.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE).setId(BLOCKS.key("arkadium_end_ore"))));

    public static final RegistryObject<Block> ARKADIUM_NETHER_ORE =registerBlock("arkadium_nether_ore",
            ()-> new DropExperienceBlock(UniformInt.of(2,4), BlockBehaviour.Properties.of()
                    .strength(2.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE).setId(BLOCKS.key("arkadium_nether_ore"))));

    public static final RegistryObject<Block> PYRESTONE_ORE =registerBlock("pyrestone_ore",
            ()-> new DropExperienceBlock(UniformInt.of(3,5), BlockBehaviour.Properties.of()
                    .strength(3.33f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.NETHERRACK).setId(BLOCKS.key("pyrestone_ore"))));


    public static final RegistryObject<Block> ARKADIUM_DEEPSLATE_ORE =registerBlock("arkadium_deepslate_ore",
            ()-> new DropExperienceBlock(UniformInt.of(3,6), BlockBehaviour.Properties.of()
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE).setId(BLOCKS.key("arkadium_deepslate_ore"))
            ));


    public static final RegistryObject<Block> GLUE_BLOCK = registerBlockWithTooltip("glue_block",
            ()->new GlueBlock(BlockBehaviour.Properties.of()
                    .strength(1.5f)

                    .noTerrainParticles()
                    .sound(SoundType.HONEY_BLOCK)
                    .setId(BLOCKS.key("glue_block"))
            ));

    public static final RegistryObject<StairBlock>  OBSIDIAN_STAIRS =registerBlock("obsidian_stairs",
            ()-> new NonMovableStairBlock(Blocks.OBSIDIAN.defaultBlockState(),
                    BlockBehaviour.Properties.of().
                            mapColor(MapColor.COLOR_BLACK).
                            instrument(NoteBlockInstrument.BASEDRUM).
                            requiresCorrectToolForDrops().
                            strength(50.0F, 1200.0F)
                            .setId(BLOCKS.key("obsidian_stairs"))
            ));

public static final RegistryObject<SlabBlock>  OBSIDIAN_SLAB =registerBlock("obsidian_slab",
            ()-> new NonMovableSlabBlock(
                    BlockBehaviour.Properties.of().
                            mapColor(MapColor.COLOR_BLACK).
                            instrument(NoteBlockInstrument.BASEDRUM).
                            requiresCorrectToolForDrops().
                            strength(50.0F, 1200.0F)
                            .setId(BLOCKS.key("obsidian_slab"))
            ));

public static final RegistryObject<PressurePlateBlock>  OBSIDIAN_PRESSURE_PLATE =registerBlock("obsidian_pressure_plate",
            ()-> new NonMovablePressurePlateBlock(BlockSetType.STONE,
                    BlockBehaviour.Properties.of().
                            mapColor(MapColor.COLOR_BLACK).
                            instrument(NoteBlockInstrument.BASEDRUM).
                            requiresCorrectToolForDrops().
                            strength(50.0F, 1200.0F)
                            .setId(BLOCKS.key("obsidian_pressure_plate"))
            ));

public static final RegistryObject<ButtonBlock>  OBSIDIAN_BUTTON =registerBlock("obsidian_button",
            ()-> new NonMovableButtonBlock(BlockSetType.STONE,22,
                    BlockBehaviour.Properties.of().
                            mapColor(MapColor.COLOR_BLACK).
                            instrument(NoteBlockInstrument.BASEDRUM).
                            requiresCorrectToolForDrops().
                            strength(50.0F, 1200.0F)
                            .noCollission()
                            .setId(BLOCKS.key("obsidian_button"))
            ));

public static final RegistryObject<WallBlock>  OBSIDIAN_WALL =registerBlock("obsidian_wall",
            ()-> new NonMovableWallBlock(
                    BlockBehaviour.Properties.of().
                            mapColor(MapColor.COLOR_BLACK).
                            instrument(NoteBlockInstrument.BASEDRUM).
                            requiresCorrectToolForDrops().
                            strength(50.0F, 1200.0F)
                            .setId(BLOCKS.key("obsidian_wall"))
            ));

    public static final RegistryObject<LampBlock> COPPER_LAMP = registerBlock("copper_lamp",
            () -> new LampBlock(BlockBehaviour.Properties.of().strength(2f)
                    .lightLevel(state -> state.getValue(LampBlock.LIT) ? 15 : 0)  // CAMBIA QUI: CLICKED -> LIT
                    .setId(BLOCKS.key("copper_lamp"))));

    //plants
    public static final RegistryObject<Block> STRAWBERRY_BUSH= BLOCKS.register("strawberry_bush",
            ()-> new StrawBerryBushBlock(BlockBehaviour.Properties.of()
                   .mapColor(MapColor.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY)
                    .setId(BLOCKS.key("strawberry_bush"))));

    public static final RegistryObject<Block> TOMATO_CROP= BLOCKS.register("tomato_crop",
            ()->new TomatoCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)
                            .setId(BLOCKS.key("tomato_crop"))));

    //log
    public static final RegistryObject<RotatedPillarBlock> WALNUT_LOG =registerBlock("walnut_log",
            ()-> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                    .setId(BLOCKS.key("walnut_log"))));
    public static final RegistryObject<RotatedPillarBlock> WALNUT_WOOD =registerBlock("walnut_wood",
            ()-> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)
                    .setId(BLOCKS.key("walnut_wood"))));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_WALNUT_LOG =registerBlock("stripped_walnut_log",
            ()-> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)
                    .setId(BLOCKS.key("stripped_walnut_log"))));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_WALNUT_WOOD =registerBlock("stripped_walnut_wood",
            ()-> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
                    .setId(BLOCKS.key("stripped_walnut_wood"))));
    public static final RegistryObject<Block> WALNUT_PLANKS =registerBlock("walnut_planks",
            ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .setId(BLOCKS.key("walnut_planks"))){


                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });

    public static final RegistryObject<Block> WALNUT_LEAVES = registerBlock("walnut_leaves",
            () -> new WalnutLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .setId(BLOCKS.key("walnut_leaves"))) {


            });

    public static final RegistryObject<Block> WALNUT_SAPLING = registerBlock("walnut_sapling",
            () -> new SaplingBlock(ModTreeGrowers.WALNUT,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
                    .setId(BLOCKS.key("walnut_sapling"))) {


            });


    public static final RegistryObject<StairBlock>  WALNUT_STAIRS =registerBlock("walnut_stairs",
            ()-> new StairBlock(ModBlocks.WALNUT_PLANKS.get().defaultBlockState(),

                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()
                            .setId(BLOCKS.key("walnut_stairs"))
            ));

    public static final RegistryObject<SlabBlock>  WALNUT_SLAB =registerBlock("walnut_slab",
            ()-> new NonMovableSlabBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()
                            .setId(BLOCKS.key("walnut_slab"))
            ));

    public static final RegistryObject<PressurePlateBlock>  WALNUT_PRESSURE_PLATE =registerBlock("walnut_pressure_plate",
            ()-> new PressurePlateBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()
                            .setId(BLOCKS.key("walnut_pressure_plate"))
            ));

    public static final RegistryObject<ButtonBlock>  WALNUT_BUTTON =registerBlock("walnut_button",
            ()-> new NonMovableButtonBlock(BlockSetType.OAK,20,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()
                            .noCollission()
                            .setId(BLOCKS.key("walnut_button"))
            ));

    public static final RegistryObject<FenceBlock>  WALNUT_FENCE =registerBlock("walnut_fence",
            ()-> new FenceBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()
                            .setId(BLOCKS.key("walnut_fence"))
            ));
    public static final RegistryObject<FenceGateBlock>  WALNUT_FENCE_GATE =registerBlock("walnut_fence_gate",
            ()-> new FenceGateBlock(WoodType.OAK,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()
                            .setId(BLOCKS.key("walnut_fence_gate"))
            ));
    public static final RegistryObject<DoorBlock>  WALNUT_DOOR =registerBlock("walnut_door",
            ()-> new DoorBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
                            .ignitedByLava()
                            .setId(BLOCKS.key("walnut_door"))
            ));

    public static final RegistryObject<TrapDoorBlock>  WALNUT_TRAPDOOR =registerBlock("walnut_trapdoor",
            ()-> new TrapDoorBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
                            .ignitedByLava()
                            .setId(BLOCKS.key("walnut_trapdoor"))
            ));
    public static final RegistryObject<Block> DEMONVM_PORTAL_BLOCK=registerBlock("demonvm_portal_block",
            ()->new ModPortalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noLootTable().noOcclusion().noCollission()

                    .strength(-1.0F)
                    .randomTicks()
                    .lightLevel(state -> 15)
                    .sound(SoundType.GLASS)
                    .pushReaction(PushReaction.BLOCK)
                    .setId(BLOCKS.key("demonvm_portal_block"))));

//chairs
    public static  final RegistryObject<Block> ACACIA_CHAIR = registerBlock("acacia_chair",
        ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                .setId(BLOCKS.key("acacia_chair"))));
    public static  final RegistryObject<Block> BAMBOO_CHAIR = registerBlock("bamboo_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("bamboo_chair"))));
    public static  final RegistryObject<Block> BIRCH_CHAIR = registerBlock("birch_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("birch_chair"))));
    public static  final RegistryObject<Block> CHERRY_CHAIR = registerBlock("cherry_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("cherry_chair"))));
    public static  final RegistryObject<Block> CRIMSON_CHAIR = registerBlock("crimson_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("crimson_chair"))));
    public static  final RegistryObject<Block> DARK_OAK_CHAIR = registerBlock("dark_oak_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("dark_oak_chair"))));
    public static  final RegistryObject<Block> JUNGLE_CHAIR = registerBlock("jungle_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("jungle_chair"))));
    public static  final RegistryObject<Block> MANGROVE_CHAIR = registerBlock("mangrove_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("mangrove_chair"))));
    public static  final RegistryObject<Block> OAK_CHAIR = registerBlock("oak_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("oak_chair"))));
    public static  final RegistryObject<Block> PALE_OAK_CHAIR = registerBlock("pale_oak_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("pale_oak_chair"))));
    public static  final RegistryObject<Block> SPRUCE_CHAIR = registerBlock("spruce_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("spruce_chair"))));
    public static  final RegistryObject<Block> WALNUT_CHAIR = registerBlock("walnut_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("walnut_chair"))));
    public static  final RegistryObject<Block> WARPED_CHAIR = registerBlock("warped_chair",
            ()-> new ChairBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(1)
                    .setId(BLOCKS.key("warped_chair"))));


    public static final RegistryObject<PedestalBlock> PEDESTAL = registerBlock("pedestal",
            () -> new PedestalBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .destroyTime(2.5f)
                    .setId(BLOCKS.key("pedestal"))));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registeredBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registeredBlockItem(String name, RegistryObject<T> block){
        ModItems.ITEMS.register(name,()-> new BlockItem(block.get(),new Item.Properties().setId(ModItems.ITEMS.key(name))));
    }
    private static <T extends Block> RegistryObject<T> registerBlockWithTooltip(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        ModItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(),
                new Item.Properties().setId(ModItems.ITEMS.key(name))) {
            @Override
            public void appendHoverText(ItemStack pStack, TooltipContext pContext, TooltipDisplay pTooltipDisplay,
                                        Consumer<Component> pTooltipAdder, TooltipFlag pFlag) {
                if(!Screen.hasShiftDown()){
                    pTooltipAdder.accept(Component.translatable("tooltip.demoniacraft.shift_down"));
                }else{
                    pTooltipAdder.accept(Component.translatable("tooltip.demoniacraft."+name));
                }

                super.appendHoverText(pStack, pContext, pTooltipDisplay, pTooltipAdder, pFlag);
            }
        });

        return toReturn;
    }


    public static void register(BusGroup eventBus){
        BLOCKS.register(eventBus);
    }

}
