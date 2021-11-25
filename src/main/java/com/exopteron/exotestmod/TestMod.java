package com.exopteron.exotestmod;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.fmllegacy.common.registry.GameRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

import com.exopteron.exotestmod.entities.EntitySetup;
import com.exopteron.exotestmod.items.ItemMagicWand;
import com.exopteron.exotestmod.items.ItemSetup;
import com.exopteron.exotestmod.keybinds.KeyBinds;
import com.exopteron.exotestmod.network.PacketHandler;
import com.exopteron.exotestmod.recipe.RecipeWand;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TestMod.MODID)
public class TestMod {
    public static final String MODID = "exotestmod";
    public static final ExoCreativeTab CREATIVE_TAB = new ExoCreativeTab("exoCreativeTab");
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister
            .create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    public static final RegistryObject<RecipeSerializer<?>> WAND_RECIPE = RECIPES.register("wand_recipe",
            RecipeWand::new);
    public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("testblock",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistryObject<Item> TEST_BLOCK_ITEM = ITEMS.register("testblock",
            () -> new BlockItem(TEST_BLOCK.get(), new BlockItem.Properties()));
    public static final RegistryObject<Item> NETHERITE_STICK = ITEMS.register("netherite_stick", () -> {
        Item.Properties properties = new Item.Properties();
        properties.fireResistant();
        properties.stacksTo(64);
        properties.tab(CREATIVE_TAB);
        return new Item(properties);
    });
    public static TestMod INSTANCE = null;
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public TestMod() {
        INSTANCE = this;
        new ItemSetup(this);
        new EntitySetup(this);
        new PacketHandler(this);
        RECIPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Register the setup method for modloading
        // MinecraftForge.EVENT_BUS.register(new ItemMagicWand.ItemEventListener());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(KeyBinds::setup);
        // Register the enqueueIMC method for modloading
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    /*
     * private void enqueueIMC(final InterModEnqueueEvent event) { // some example
     * code to dispatch IMC to another mod //InterModComms.sendTo("examplemod",
     * "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return
     * "Hello world";}); }
     */
    /*
     * private void processIMC(final InterModProcessEvent event) { // some example
     * code to receive and process InterModComms from other mods
     * LOGGER.info("Got IMC {}", event.getIMCStream().
     * map(m->m.messageSupplier().get()). collect(Collectors.toList())); }
     */
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    // You can use EventBusSubscriber to automatically subscribe events on the
    // contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            // BlockBehaviour.Properties behavior =
            // BlockBehaviour.Properties.of(Material.METAL);
            // blockRegistryEvent.getRegistry().register(new Block(behavior));
            LOGGER.info("HELLO from Register Block");
        }
    }
}
