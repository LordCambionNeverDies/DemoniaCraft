package net.lordcambion.demoniacraft;

import com.mojang.logging.LogUtils;
import net.lordcambion.demoniacraft.block.ModBlocks;
import net.lordcambion.demoniacraft.block.custom.Entities.ModBlockEntities;
import net.lordcambion.demoniacraft.block.custom.Entities.renderer.PedestalBlockEntityRenderer;
import net.lordcambion.demoniacraft.client.renderer.entity.EnderArrowRenderer;
import net.lordcambion.demoniacraft.component.ModDataComponentTypes;
import net.lordcambion.demoniacraft.effect.ModEffects;
import net.lordcambion.demoniacraft.enchantment.ModEnchantmentEffects;
import net.lordcambion.demoniacraft.entity.ModEntities;
import net.lordcambion.demoniacraft.entity.ModEntityDataSerializers;
import net.lordcambion.demoniacraft.entity.client.bonereaper.BoneReaperArmorModel;
import net.lordcambion.demoniacraft.entity.client.bonereaper.BoneReaperModel;
import net.lordcambion.demoniacraft.entity.client.bonereaper.BoneReaperRenderer;
import net.lordcambion.demoniacraft.entity.client.chair.ChairRenderer;
import net.lordcambion.demoniacraft.entity.client.hedgehog.HedgehogRenderer;
import net.lordcambion.demoniacraft.entity.custom.BoneReaperEntity;
import net.lordcambion.demoniacraft.init.ModEntityTypes;
import net.lordcambion.demoniacraft.item.ModCreativeModeTabs;
import net.lordcambion.demoniacraft.item.ModItems;
import net.lordcambion.demoniacraft.loot.ModLootModifiers;
import net.lordcambion.demoniacraft.potion.ModPotions;
import net.lordcambion.demoniacraft.sound.ModSound;
import net.lordcambion.demoniacraft.worldgen.biome.ModTerrablender;
import net.lordcambion.demoniacraft.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;
import terrablender.api.SurfaceRuleManager;


@Mod(Demoniacraft.MOD_ID)
public final class Demoniacraft {
    public static final String MOD_ID = "demoniacraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Demoniacraft(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);
        //MinecraftForge.EVENT_BUS.register(this);
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.DEMONVM_PORTAL_BLOCK.get(), ChunkSectionLayer.TRANSLUCENT);
        ModCreativeModeTabs.register(modBusGroup);
        ModItems.register(modBusGroup);
        ModBlocks.register(modBusGroup);
        ModDataComponentTypes.register(modBusGroup);
        ModSound.register(modBusGroup);
        ModEffects.register(modBusGroup);
        ModPotions.register(modBusGroup);
        ModLootModifiers.register(modBusGroup);
        ModBlockEntities.register(modBusGroup);
        //arrows
        ModEntityTypes.register(modBusGroup);
        //mobs
        ModEntities.register(modBusGroup);

        ModEnchantmentEffects.register(modBusGroup);

        //Biomes
        ModTerrablender.registerBiomes();

        BuildCreativeModeTabContentsEvent.getBus(modBusGroup).addListener(Demoniacraft::addCreative);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
                    ModEntityDataSerializers.register();
                    SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD,MOD_ID, ModSurfaceRules.makeRules());
                ComposterBlock.COMPOSTABLES.put(ModItems.TOMATO.get(),0.4f);
                ComposterBlock.COMPOSTABLES.put(ModItems.TOMATO_SEEDS.get(),0.15f);
                ComposterBlock.COMPOSTABLES.put(ModItems.POOP.get(),0.4f);
                ComposterBlock.COMPOSTABLES.put(ModItems.STRAWBERRY.get(),0.13f);
                    ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEDESTAL.get(), ChunkSectionLayer.CUTOUT);
        }
        );

    }



    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModItems.ARKADIUM_INGOT.get());
            event.accept(ModItems.RAW_ARKADIUM.get());
            event.accept(ModItems.PYRESTONE.get());
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.ARKADIUM_BLOCK.get());
        }
        if(event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS){
            event.accept(ModBlocks.GLUE_BLOCK.get());
        }
        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
            event.accept(ModItems.POOP.get());
        }
        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS){
            event.accept(ModBlocks.ARKADIUM_ORE.get());
            event.accept(ModBlocks.ARKADIUM_DEEPSLATE_ORE.get());
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.TOMATO_CROP.get(), ChunkSectionLayer.CUTOUT);
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.STRAWBERRY_BUSH.get(), ChunkSectionLayer.CUTOUT);
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.WALNUT_SAPLING.get(), ChunkSectionLayer.CUTOUT);
            });

            EntityRenderers.register(ModEntities.HEDGEHOG.get(), HedgehogRenderer::new);
            EntityRenderers.register(ModEntities.BONEREAPER.get(), BoneReaperRenderer::new);
            EntityRenderers.register(ModEntities.CHAIR.get(), ChairRenderer::new);

        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            // Registra il renderer per EnderArrowEntity
            event.registerEntityRenderer(ModEntityTypes.ENDER_ARROW.get(), EnderArrowRenderer::new);

        }
        @SubscribeEvent
        private static void registerEntityAttributes(EntityAttributeCreationEvent event) {
            event.put(ModEntities.BONEREAPER.get(), BoneReaperEntity.createAttributes().build());
            // Aggiungi qui altri entity attributes se necessario
        }
        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BoneReaperModel.LAYER_LOCATION, BoneReaperModel::createBodyLayer);
            event.registerLayerDefinition(BoneReaperModel.INNER_ARMOR, BoneReaperArmorModel::createInnerArmorLayer);
            event.registerLayerDefinition(BoneReaperModel.OUTER_ARMOR, BoneReaperArmorModel::createOuterArmorLayer);
        }
        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL_BE.get(), PedestalBlockEntityRenderer::new);
        }

    }
}
