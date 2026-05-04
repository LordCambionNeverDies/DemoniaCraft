package net.lordcambion.demoniacraft.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import net.lordcambion.demoniacraft.block.ModBlocks;

import net.lordcambion.demoniacraft.block.custom.LampBlock;
import net.lordcambion.demoniacraft.block.custom.TomatoCropBlock;
import net.lordcambion.demoniacraft.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraftforge.fml.common.Mod;


import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModBlockModelGenerators extends BlockModelGenerators {
    final List<Block> nonOrientableTrapdoor = ImmutableList.of(Blocks.OAK_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR, Blocks.IRON_TRAPDOOR);
    final Map<Block, TexturedModel> texturedModels = ImmutableMap.<Block, TexturedModel>builder()
            .build();

    static final ImmutableMap<BlockFamily.Variant, BiConsumer<ModBlockFamilyProvider, Block>> SHAPE_CONSUMERS = ImmutableMap.<BlockFamily.Variant, BiConsumer<ModBlockFamilyProvider, Block>>builder()
            .put(BlockFamily.Variant.BUTTON, ModBlockFamilyProvider::button)
            .put(BlockFamily.Variant.DOOR, ModBlockFamilyProvider::door)
            .put(BlockFamily.Variant.CHISELED, ModBlockFamilyProvider::fullBlockVariant)
            .put(BlockFamily.Variant.CRACKED, ModBlockFamilyProvider::fullBlockVariant)
            .put(BlockFamily.Variant.CUSTOM_FENCE, ModBlockFamilyProvider::customFence)
            .put(BlockFamily.Variant.FENCE, ModBlockFamilyProvider::fence)
            .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, ModBlockFamilyProvider::customFenceGate)
            .put(BlockFamily.Variant.FENCE_GATE, ModBlockFamilyProvider::fenceGate)
            .put(BlockFamily.Variant.SIGN, ModBlockFamilyProvider::sign)
            .put(BlockFamily.Variant.SLAB, ModBlockFamilyProvider::slab)
            .put(BlockFamily.Variant.STAIRS, ModBlockFamilyProvider::stairs)
            .put(BlockFamily.Variant.PRESSURE_PLATE, ModBlockFamilyProvider::pressurePlate)
            .put(BlockFamily.Variant.TRAPDOOR, ModBlockFamilyProvider::trapdoor)
            .put(BlockFamily.Variant.WALL, ModBlockFamilyProvider::wall)
            .build();


    protected void createDoor(Block pDoorBlock) {
        TextureMapping texturemapping = TextureMapping.door(pDoorBlock);
        MultiVariant multivariant = plainVariant(ModModelTemplates.DOOR_BOTTOM_LEFT.create(pDoorBlock, texturemapping, this.modelOutput));
        MultiVariant multivariant1 = plainVariant(ModModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput));
        MultiVariant multivariant2 = plainVariant(ModModelTemplates.DOOR_BOTTOM_RIGHT.create(pDoorBlock, texturemapping, this.modelOutput));
        MultiVariant multivariant3 = plainVariant(ModModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput));
        MultiVariant multivariant4 = plainVariant(ModModelTemplates.DOOR_TOP_LEFT.create(pDoorBlock, texturemapping, this.modelOutput));
        MultiVariant multivariant5 = plainVariant(ModModelTemplates.DOOR_TOP_LEFT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput));
        MultiVariant multivariant6 = plainVariant(ModModelTemplates.DOOR_TOP_RIGHT.create(pDoorBlock, texturemapping, this.modelOutput));
        MultiVariant multivariant7 = plainVariant(ModModelTemplates.DOOR_TOP_RIGHT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput));
        this.registerSimpleFlatItemModel(pDoorBlock.asItem());
        this.blockStateOutput
                .accept(createDoor(pDoorBlock,
                        multivariant,
                        multivariant1,
                        multivariant2,
                        multivariant3,
                        multivariant4,
                        multivariant5,
                        multivariant6,
                        multivariant7));
    }


    protected static BlockModelDefinitionGenerator createTrapdoor(Block pTrapdoorBlock, MultiVariant pTop, MultiVariant pBottom, MultiVariant pOpen) {
        return MultiVariantGenerator.dispatch(pTrapdoorBlock)
                .with(
                        PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.OPEN)
                                .select(Direction.NORTH, Half.BOTTOM, false, pBottom)
                                .select(Direction.SOUTH, Half.BOTTOM, false, pBottom)
                                .select(Direction.EAST, Half.BOTTOM, false, pBottom)
                                .select(Direction.WEST, Half.BOTTOM, false, pBottom)
                                .select(Direction.NORTH, Half.TOP, false, pTop)
                                .select(Direction.SOUTH, Half.TOP, false, pTop)
                                .select(Direction.EAST, Half.TOP, false, pTop)
                                .select(Direction.WEST, Half.TOP, false, pTop)
                                .select(Direction.NORTH, Half.BOTTOM, true, pOpen)
                                .select(Direction.SOUTH, Half.BOTTOM, true, pOpen.with(Y_ROT_180))
                                .select(Direction.EAST, Half.BOTTOM, true, pOpen.with(Y_ROT_90))
                                .select(Direction.WEST, Half.BOTTOM, true, pOpen.with(Y_ROT_270))
                                .select(Direction.NORTH, Half.TOP, true, pOpen)
                                .select(Direction.SOUTH, Half.TOP, true, pOpen.with(Y_ROT_180))
                                .select(Direction.EAST, Half.TOP, true, pOpen.with(Y_ROT_90))
                                .select(Direction.WEST, Half.TOP, true, pOpen.with(Y_ROT_270))
                );
    }



    public ModBlockModelGenerators(
            Consumer<BlockModelDefinitionGenerator> pBlockStateOutput, ItemModelOutput pItemModelOutput, BiConsumer<ResourceLocation, ModelInstance> pModelOutput
    ) {
        super(pBlockStateOutput, pItemModelOutput, pModelOutput);
    }

    @Override
    public void run() {

        ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach(
                p_375984_ -> this.family(p_375984_.getBaseBlock()).generateFor(p_375984_)

        );
//        createTrivialCube(ModBlocks.BEAN_BLOCK.get());
        createTrivialCube(ModBlocks.ARKADIUM_BLOCK.get());
        createTrivialCube(ModBlocks.ARKADIUM_ORE.get());
        createTrivialCube(ModBlocks.ARKADIUM_END_ORE.get());
        createTrivialCube(ModBlocks.ARKADIUM_NETHER_ORE.get());
        createTrivialCube(ModBlocks.ARKADIUM_DEEPSLATE_ORE.get());
        createTrivialCube(ModBlocks.PYRESTONE_ORE.get());
        createTrivialCube(ModBlocks.GLUE_BLOCK.get());
        createTrivialCube(ModBlocks.DARK_STONE_BLOCK.get());
        createTrivialCube(ModBlocks.DARK_COBBLESTONE_BLOCK.get());

        createLamp((LampBlock) ModBlocks.COPPER_LAMP.get());

        createCropBlock(
                (CropBlock) ModBlocks.TOMATO_CROP.get(),
                TomatoCropBlock.AGE,  // Passa la proprietà custom
                0, 1, 2, 3, 4, 5      // 6 stadi (da 0 a 5)
        );
        //createTrivialCube(ModBlocks.DEMONVM_PORTAL_BLOCK.get());
        createBerryBush(
                ModBlocks.STRAWBERRY_BUSH.get(),
                ModItems.STRAWBERRY.get(),
                BlockStateProperties.AGE_3,
                0, 1, 2, 3
        );

        createPortalBlock(ModBlocks.DEMONVM_PORTAL_BLOCK.get());
        this.createPlantWithDefaultItem(ModBlocks.WALNUT_SAPLING.get(), Blocks.POTTED_OAK_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
        //createTrivialCube(ModBlocks.WALNUT_PLANKS.get());
        woodProvider(ModBlocks.WALNUT_LOG.get()).logWithHorizontal(ModBlocks.WALNUT_LOG.get()).wood(ModBlocks.WALNUT_WOOD.get());
        woodProvider(ModBlocks.STRIPPED_WALNUT_LOG.get()).logWithHorizontal(ModBlocks.STRIPPED_WALNUT_LOG.get()).wood(ModBlocks.STRIPPED_WALNUT_WOOD.get());
        createTintedLeaves(ModBlocks.WALNUT_LEAVES.get(), TexturedModel.LEAVES, -12012264);

        /*

        createStairs(ModBlocks.OBSIDIAN_STAIRS.get(), Blocks.OBSIDIAN);
        createSlab(ModBlocks.OBSIDIAN_SLAB.get(),Blocks.OBSIDIAN);
        createButton(ModBlocks.OBSIDIAN_SLAB.get(),Blocks.OBSIDIAN);

        createPressurePlate(ModBlocks.OBSIDIAN_PRESSURE_PLATE.get(), Blocks.OBSIDIAN);
        createWall(ModBlocks.OBSIDIAN_WALL.get(), Blocks.OBSIDIAN);
*/

    }


    public class ModBlockFamilyProvider extends BlockFamilyProvider {
        private final TextureMapping mapping;
        private final Map<ModelTemplate, ResourceLocation> models = Maps.newHashMap();
        @Nullable
        private BlockFamily family;
        @Nullable
        private ResourceLocation fullBlock;
        private final Set<Block> skipGeneratingModelsFor = new HashSet<>();
        public ModBlockFamilyProvider(TextureMapping pMapping) {
            super(pMapping);
            this.mapping = pMapping;
        }

        @Override
        public BlockFamilyProvider generateFor(BlockFamily pFamily) {
            this.family = pFamily;
            pFamily.getVariants().forEach((p_375413_, p_375795_) -> {
                if (!this.skipGeneratingModelsFor.contains(p_375795_)) {
                    BiConsumer<ModBlockFamilyProvider, Block> biconsumer = ModBlockModelGenerators.SHAPE_CONSUMERS.get(p_375413_);
                    if (biconsumer != null) {
                        biconsumer.accept(this, p_375795_);
                    }
                }
            });
            return this;
        }

        protected BlockModelGenerators.BlockFamilyProvider door(Block pDoorBlock) {
            ModBlockModelGenerators.this.createDoor(pDoorBlock);
            return this;
        }


        public BlockModelGenerators.BlockFamilyProvider button(Block pButtonBlock) {
            MultiVariant multivariant = BlockModelGenerators.plainVariant(
                    ModModelTemplates.BUTTON.create(pButtonBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant1 = ModBlockModelGenerators.plainVariant(
                    ModModelTemplates.BUTTON_PRESSED.create(pButtonBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
            );
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createButton(pButtonBlock, multivariant, multivariant1));
            ResourceLocation resourcelocation = ModModelTemplates.BUTTON_INVENTORY.create(pButtonBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.registerSimpleItemModel(pButtonBlock, resourcelocation);
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider wall(Block pWallBlock) {
            // Usa TextureMapping.cube con il blocco base invece di this.mapping
            TextureMapping textureMapping = TextureMapping.cube(this.family.getBaseBlock());

            MultiVariant multivariant = BlockModelGenerators.plainVariant(
                    ModModelTemplates.WALL_POST.create(pWallBlock, textureMapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant1 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.WALL_LOW_SIDE.create(pWallBlock, textureMapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant2 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.WALL_TALL_SIDE.create(pWallBlock, textureMapping, ModBlockModelGenerators.this.modelOutput)
            );

            // Assicurati che createWall usi le varianti corrette
            ModBlockModelGenerators.this.blockStateOutput.accept(
                    ModBlockModelGenerators.modcreateWall(pWallBlock, multivariant, multivariant1, multivariant2)
            );

            ResourceLocation resourcelocation = ModModelTemplates.WALL_INVENTORY.create(pWallBlock, textureMapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.registerSimpleItemModel(pWallBlock, resourcelocation);
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider customFence(Block pFenceBlock) {
            TextureMapping texturemapping = TextureMapping.customParticle(pFenceBlock);
            MultiVariant multivariant = BlockModelGenerators.plainVariant(
                    ModModelTemplates.CUSTOM_FENCE_POST.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant1 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.CUSTOM_FENCE_SIDE_NORTH.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant2 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.CUSTOM_FENCE_SIDE_EAST.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant3 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.CUSTOM_FENCE_SIDE_SOUTH.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant4 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.CUSTOM_FENCE_SIDE_WEST.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput)
            );
            ModBlockModelGenerators.this.blockStateOutput
                    .accept(BlockModelGenerators.createCustomFence(pFenceBlock, multivariant, multivariant1, multivariant2, multivariant3, multivariant4));
            ResourceLocation resourcelocation = ModModelTemplates.CUSTOM_FENCE_INVENTORY.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.registerSimpleItemModel(pFenceBlock, resourcelocation);
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider fence(Block pFenceBlock) {
            MultiVariant multivariant = BlockModelGenerators.plainVariant(
                    ModModelTemplates.FENCE_POST.create(pFenceBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant1 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.FENCE_SIDE.create(pFenceBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
            );
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFence(pFenceBlock, multivariant, multivariant1));
            ResourceLocation resourcelocation = ModModelTemplates.FENCE_INVENTORY.create(pFenceBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.registerSimpleItemModel(pFenceBlock, resourcelocation);
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider customFenceGate(Block pCustomFenceGateBlock) {
            TextureMapping texturemapping = TextureMapping.customParticle(pCustomFenceGateBlock);
            MultiVariant multivariant = BlockModelGenerators.plainVariant(
                    ModModelTemplates.CUSTOM_FENCE_GATE_OPEN.create(pCustomFenceGateBlock, texturemapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant1 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.CUSTOM_FENCE_GATE_CLOSED.create(pCustomFenceGateBlock, texturemapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant2 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.CUSTOM_FENCE_GATE_WALL_OPEN.create(pCustomFenceGateBlock, texturemapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant3 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.CUSTOM_FENCE_GATE_WALL_CLOSED.create(pCustomFenceGateBlock, texturemapping, ModBlockModelGenerators.this.modelOutput)
            );
            ModBlockModelGenerators.this.blockStateOutput
                    .accept(BlockModelGenerators.createFenceGate(pCustomFenceGateBlock, multivariant, multivariant1, multivariant2, multivariant3, false));
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider fenceGate(Block pFenceGateBlock) {
            MultiVariant multivariant = BlockModelGenerators.plainVariant(
                    ModModelTemplates.FENCE_GATE_OPEN.create(pFenceGateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant1 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.FENCE_GATE_CLOSED.create(pFenceGateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant2 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.FENCE_GATE_WALL_OPEN.create(pFenceGateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant3 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.FENCE_GATE_WALL_CLOSED.create(pFenceGateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
            );
            ModBlockModelGenerators.this.blockStateOutput
                    .accept(BlockModelGenerators.createFenceGate(pFenceGateBlock, multivariant, multivariant1, multivariant2, multivariant3, true));
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider pressurePlate(Block pPressurePlateBlock) {
            MultiVariant multivariant = BlockModelGenerators.plainVariant(
                    ModModelTemplates.PRESSURE_PLATE_UP.create(pPressurePlateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
            );
            MultiVariant multivariant1 = BlockModelGenerators.plainVariant(
                    ModModelTemplates.PRESSURE_PLATE_DOWN.create(pPressurePlateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
            );
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pPressurePlateBlock, multivariant, multivariant1));
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider sign(Block pSignBlock) {
            if (this.family == null) {
                throw new IllegalStateException("Family not defined");
            } else {
                Block block = this.family.getVariants().get(BlockFamily.Variant.WALL_SIGN);
                MultiVariant multivariant = ModBlockModelGenerators.plainVariant(
                        ModModelTemplates.PARTICLE_ONLY.create(pSignBlock, this.mapping, ModBlockModelGenerators.this.modelOutput)
                );
                ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pSignBlock, multivariant));
                ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, multivariant));
                ModBlockModelGenerators.this.registerSimpleFlatItemModel(pSignBlock.asItem());
                return this;
            }
        }

        public BlockModelGenerators.BlockFamilyProvider slab(Block pSlabBlock) {
            if (this.fullBlock == null) {
                throw new IllegalStateException("Full block not generated yet");
            } else {
                ResourceLocation resourcelocation = this.getOrCreateModel(ModModelTemplates.SLAB_BOTTOM, pSlabBlock);
                MultiVariant multivariant = BlockModelGenerators.plainVariant(this.getOrCreateModel(ModModelTemplates.SLAB_TOP, pSlabBlock));
                ModBlockModelGenerators.this.blockStateOutput
                        .accept(
                                BlockModelGenerators.createSlab(
                                        pSlabBlock,
                                        BlockModelGenerators.plainVariant(resourcelocation),
                                        multivariant,
                                        BlockModelGenerators.plainVariant(this.fullBlock)  // Usa plainVariant invece di variant
                                )
                        );
                ModBlockModelGenerators.this.registerSimpleItemModel(pSlabBlock, resourcelocation);
                return this;
            }
        }

        public BlockModelGenerators.BlockFamilyProvider stairs(Block pStairsBlock) {
            MultiVariant multivariant = BlockModelGenerators.plainVariant(this.getOrCreateModel(ModModelTemplates.STAIRS_INNER, pStairsBlock));
            ResourceLocation resourcelocation = this.getOrCreateModel(ModModelTemplates.STAIRS_STRAIGHT, pStairsBlock);
            MultiVariant multivariant1 = BlockModelGenerators.plainVariant(this.getOrCreateModel(ModModelTemplates.STAIRS_OUTER, pStairsBlock));
            ModBlockModelGenerators.this.blockStateOutput
                    .accept(BlockModelGenerators.createStairs(pStairsBlock, multivariant, BlockModelGenerators.plainVariant(resourcelocation), multivariant1));
            ModBlockModelGenerators.this.registerSimpleItemModel(pStairsBlock, resourcelocation);
            return this;
        }

        protected BlockModelGenerators.BlockFamilyProvider fullBlockVariant(Block pBlock) {
            TexturedModel texturedmodel = ModBlockModelGenerators.this.texturedModels.getOrDefault(pBlock, TexturedModel.CUBE.get(pBlock));
            MultiVariant multivariant = BlockModelGenerators.plainVariant(texturedmodel.create(pBlock, ModBlockModelGenerators.this.modelOutput));
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pBlock, multivariant));
            return this;
        }




        protected void trapdoor(Block pTrapdoorBlock) {
            if (ModBlockModelGenerators.this.nonOrientableTrapdoor.contains(pTrapdoorBlock)) {
                ModBlockModelGenerators.this.createTrapdoor(pTrapdoorBlock);
            } else {
                ModBlockModelGenerators.this.createOrientableTrapdoor(pTrapdoorBlock);
            }
        }
    }
    protected void createPortalBlock(Block block) {
        this.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(block)
                                .with(
                                        PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS)
                                                .select(Direction.Axis.X, plainVariant(ModelLocationUtils.getModelLocation(block, "_ns")))
                                                .select(Direction.Axis.Z, plainVariant(ModelLocationUtils.getModelLocation(block, "_ew")))
                                )
                );
    }
    protected void createOrientableTrapdoor(Block pOrientableTrapdoorBlock) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(pOrientableTrapdoorBlock);
        MultiVariant multivariant = plainVariant(ModModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(pOrientableTrapdoorBlock, texturemapping, this.modelOutput));
        ResourceLocation resourcelocation = ModModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(pOrientableTrapdoorBlock, texturemapping, this.modelOutput);
        MultiVariant multivariant1 = plainVariant(ModModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(pOrientableTrapdoorBlock, texturemapping, this.modelOutput));
        this.blockStateOutput.accept(createOrientableTrapdoor(pOrientableTrapdoorBlock, multivariant, plainVariant(resourcelocation), multivariant1));
        this.registerSimpleItemModel(pOrientableTrapdoorBlock, resourcelocation);
    }

    protected void createTrapdoor(Block pTrapdoorBlock) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(pTrapdoorBlock);
        MultiVariant multivariant = plainVariant(ModModelTemplates.TRAPDOOR_TOP.create(pTrapdoorBlock, texturemapping, this.modelOutput));
        ResourceLocation resourcelocation = ModModelTemplates.TRAPDOOR_BOTTOM.create(pTrapdoorBlock, texturemapping, this.modelOutput);
        MultiVariant multivariant1 = plainVariant(ModModelTemplates.TRAPDOOR_OPEN.create(pTrapdoorBlock, texturemapping, this.modelOutput));
        this.blockStateOutput.accept(createTrapdoor(pTrapdoorBlock, multivariant, plainVariant(resourcelocation), multivariant1));
        this.registerSimpleItemModel(pTrapdoorBlock, resourcelocation);
    }

    protected void createStairs(Block stairsBlock, Block baseBlock) {
        TextureMapping textureMapping = TextureMapping.cube(baseBlock);

        // Crea i modelli per le diverse varianti delle scale
        ResourceLocation innerModel = ModModelTemplates.STAIRS_INNER.create(stairsBlock, textureMapping, this.modelOutput);
        ResourceLocation straightModel = ModModelTemplates.STAIRS_STRAIGHT.create(stairsBlock, textureMapping, this.modelOutput);
        ResourceLocation outerModel = ModModelTemplates.STAIRS_OUTER.create(stairsBlock, textureMapping, this.modelOutput);

        // Crea le varianti multivarianti
        MultiVariant innerVariant = plainVariant(innerModel);
        MultiVariant straightVariant = plainVariant(straightModel);
        MultiVariant outerVariant = plainVariant(outerModel);

        // Genera lo stato del blocco per le scale
        this.blockStateOutput.accept(createStairs(stairsBlock, innerVariant, straightVariant, outerVariant));

        // Registra il modello dell'item
        this.registerSimpleItemModel(stairsBlock, straightModel);
    }

    protected void createSlab(Block slabBlock, Block baseBlock) {
        TextureMapping textureMapping = TextureMapping.cube(baseBlock);

        // Crea i modelli per le diverse varianti della slab
        ResourceLocation bottomModel = ModModelTemplates.SLAB_BOTTOM.create(slabBlock, textureMapping, this.modelOutput);
        ResourceLocation topModel = ModModelTemplates.SLAB_TOP.create(slabBlock, textureMapping, this.modelOutput);
        ResourceLocation doubleModel = ModModelTemplates.SLAB_DOUBLE.create(slabBlock, textureMapping, this.modelOutput);

        // Crea le varianti multivarianti
        MultiVariant bottomVariant = plainVariant(bottomModel);
        MultiVariant topVariant = plainVariant(topModel);
        MultiVariant doubleVariant = plainVariant(doubleModel);

        // Usa il metodo createSlab statico
        this.blockStateOutput.accept(createSlab(slabBlock, bottomVariant, topVariant, doubleVariant));

        // Registra il modello dell'item (usiamo il modello bottom per l'item)
        this.registerSimpleItemModel(slabBlock, bottomModel);
    }
    protected void createButton(Block buttonBlock, Block baseBlock) {
        TextureMapping textureMapping = TextureMapping.cube(baseBlock);

        // Crea i modelli per il button
        ResourceLocation buttonModel = ModModelTemplates.BUTTON.create(buttonBlock, textureMapping, this.modelOutput);
        ResourceLocation buttonPressedModel = ModModelTemplates.BUTTON_PRESSED.create(buttonBlock, textureMapping, this.modelOutput);
        ResourceLocation buttonInventoryModel = ModModelTemplates.BUTTON_INVENTORY.create(buttonBlock, textureMapping, this.modelOutput);

        MultiVariant buttonVariant = plainVariant(buttonModel);
        MultiVariant buttonPressedVariant = plainVariant(buttonPressedModel);

        // Genera lo stato del blocco
        this.blockStateOutput.accept(createButton(buttonBlock, buttonVariant, buttonPressedVariant));

        // Registra il modello dell'item (usando l'inventory model)
        this.registerSimpleItemModel(buttonBlock, buttonInventoryModel);
    }
    protected void createPressurePlate(Block pressurePlateBlock, Block baseBlock) {
        TextureMapping textureMapping = TextureMapping.cube(baseBlock);

        // Crea i modelli per la pressure plate
        ResourceLocation pressurePlateUpModel = ModModelTemplates.PRESSURE_PLATE_UP.create(pressurePlateBlock, textureMapping, this.modelOutput);
        ResourceLocation pressurePlateDownModel = ModModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlateBlock, textureMapping, this.modelOutput);

        MultiVariant pressurePlateUpVariant = plainVariant(pressurePlateUpModel);
        MultiVariant pressurePlateDownVariant = plainVariant(pressurePlateDownModel);

        // Genera lo stato del blocco
        this.blockStateOutput.accept(createPressurePlate(pressurePlateBlock, pressurePlateUpVariant, pressurePlateDownVariant));

        // Registra il modello dell'item
        this.registerSimpleItemModel(pressurePlateBlock, pressurePlateUpModel);
    }

    protected static BlockModelDefinitionGenerator modcreateWall(Block pBlock, MultiVariant pPost, MultiVariant pLowSide, MultiVariant pTallSide) {
        return MultiPartGenerator.multiPart(pBlock)
                // POST (quando c'è blocco sopra)
                .with(condition().term(BlockStateProperties.UP, true), pPost)

                // UNIONI NORD (con blocchi e con altri muri) - BASSE
                .with(condition().term(BlockStateProperties.NORTH_WALL, WallSide.LOW), pLowSide.with(UV_LOCK))
                // UNIONI NORD - ALTE
                .with(condition().term(BlockStateProperties.NORTH_WALL, WallSide.TALL), pTallSide.with(UV_LOCK))

                // UNIONI EST (con blocchi e con altri muri) - BASSE
                .with(condition().term(BlockStateProperties.EAST_WALL, WallSide.LOW), pLowSide.with(Y_ROT_90).with(UV_LOCK))
                // UNIONI EST - ALTE
                .with(condition().term(BlockStateProperties.EAST_WALL, WallSide.TALL), pTallSide.with(Y_ROT_90).with(UV_LOCK))

                // UNIONI SUD (con blocchi e con altri muri) - BASSE
                .with(condition().term(BlockStateProperties.SOUTH_WALL, WallSide.LOW), pLowSide.with(Y_ROT_180).with(UV_LOCK))
                // UNIONI SUD - ALTE
                .with(condition().term(BlockStateProperties.SOUTH_WALL, WallSide.TALL), pTallSide.with(Y_ROT_180).with(UV_LOCK))

                // UNIONI OVEST (con blocchi e con altri muri) - BASSE
                .with(condition().term(BlockStateProperties.WEST_WALL, WallSide.LOW), pLowSide.with(Y_ROT_270).with(UV_LOCK))
                // UNIONI OVEST - ALTE
                .with(condition().term(BlockStateProperties.WEST_WALL, WallSide.TALL), pTallSide.with(Y_ROT_270).with(UV_LOCK))

                // AGGIUNGI QUESTE: UNIONI TRA MURI QUANDO NON C'È BLOCCCO SOPRA
                .with(condition().term(BlockStateProperties.UP, false).term(BlockStateProperties.NORTH_WALL, WallSide.LOW), pLowSide.with(UV_LOCK))
                .with(condition().term(BlockStateProperties.UP, false).term(BlockStateProperties.EAST_WALL, WallSide.LOW), pLowSide.with(Y_ROT_90).with(UV_LOCK))
                .with(condition().term(BlockStateProperties.UP, false).term(BlockStateProperties.SOUTH_WALL, WallSide.LOW), pLowSide.with(Y_ROT_180).with(UV_LOCK))
                .with(condition().term(BlockStateProperties.UP, false).term(BlockStateProperties.WEST_WALL, WallSide.LOW), pLowSide.with(Y_ROT_270).with(UV_LOCK));
    }

    protected void createLamp(LampBlock lampBlock) {
        // Crea il modello per lo stato spento (usando la texture normale del blocco)
        MultiVariant offVariant = plainVariant(TexturedModel.CUBE.create(lampBlock, this.modelOutput));

        // Crea il modello per lo stato acceso (usando la texture _on)
        MultiVariant onVariant = plainVariant(this.createSuffixedVariant(lampBlock, "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube));

        // Genera il blockstate
        this.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(lampBlock)
                        .with(createBooleanModelDispatch(LampBlock.LIT, onVariant, offVariant))
        );
    }
    protected void createCropBlock(CropBlock pCropBlock, int... pAgeToVisualStageMapping) {
        this.registerSimpleFlatItemModel(pCropBlock.asItem());

        // Usa direttamente BlockStateProperties.AGE_7 come proprietà di default
        IntegerProperty ageProperty = BlockStateProperties.AGE_7;

        if (ageProperty.getPossibleValues().size() != pAgeToVisualStageMapping.length) {
            throw new IllegalArgumentException("Age stages mismatch for crop block");
        }

        Int2ObjectMap<ResourceLocation> int2objectmap = new Int2ObjectOpenHashMap<>();

        this.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(pCropBlock)
                        .with(
                                PropertyDispatch.initial(ageProperty)
                                        .generate(
                                                age -> {
                                                    int visualStage = pAgeToVisualStageMapping[age];
                                                    return plainVariant(
                                                            int2objectmap.computeIfAbsent(
                                                                    visualStage,
                                                                    stage -> this.createSuffixedVariant(
                                                                            pCropBlock,
                                                                            "_stage" + stage,
                                                                            ModelTemplates.CROP,
                                                                            TextureMapping::crop
                                                                    )
                                                            )
                                                    );
                                                }
                                        )
                        )
        );
    }

    protected void createBerryBush(Block bushBlock, Item fruitItem, IntegerProperty ageProperty, int... stages) {
        // registra il modello item del frutto
        this.registerSimpleFlatItemModel(fruitItem);

        // verifica che il numero di stadi corrisponda alla proprietà AGE
        if (ageProperty.getPossibleValues().size() != stages.length) {
            throw new IllegalArgumentException("Age stages mismatch for bush block: " + bushBlock);
        }

        Int2ObjectMap<ResourceLocation> stageModelMap = new Int2ObjectOpenHashMap<>();

        this.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(bushBlock)
                        .with(
                                PropertyDispatch.initial(ageProperty)
                                        .generate(age -> {
                                            int visualStage = stages[age];
                                            return plainVariant(
                                                    stageModelMap.computeIfAbsent(
                                                            visualStage,
                                                            stage -> this.createSuffixedVariant(
                                                                    bushBlock,
                                                                    "_stage" + stage,
                                                                    ModelTemplates.CROSS, // template cross per bush
                                                                    TextureMapping::cross
                                                            )
                                                    )
                                            );
                                        })
                        )
        );
    }

}