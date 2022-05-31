package com.github.blacksabin.orphic;

import com.github.blacksabin.orphic.anima.ItemAnimaCore;
import com.github.blacksabin.orphic.blocks.*;
import com.github.blacksabin.orphic.items.ItemArdentSeed;
import com.github.blacksabin.orphic.anima.ItemCoreExtractor;
import com.github.blacksabin.orphic.anima.ItemVitalicOriel;
import com.github.blacksabin.orphic.anima.components.HeartOrphicSource;
import com.github.blacksabin.orphic.anima.screens.ScreenHandlerAnimaModifier;
import com.github.blacksabin.orphic.items.ItemAtuCharm;
import com.github.blacksabin.orphic.screens.ScreenHandlerMineralSynthesizer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrphicInit implements ModInitializer {

	public static final String MOD_ID = "orphic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	public static final Block BLOCK_ABSTRACT_STONE = new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f));
	public static final Block BLOCK_MINERAL_SYNTHESIZER = new BlockMineralSynthesizer(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool());
	public static final Block BLOCK_DEEP_SENTINEL = new BlockDeepSentinel(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool());
	public static final Block BLOCK_DEEP_TOTEM = new BlockDeepTotem(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool());

	public static BlockEntityType<BlockEntityMineralSynthesizer> BLOCK_ENTITY_MINERAL_SYNTHESIZER;
	public static BlockEntityType<BlockEntityDeepSentinel> BLOCK_ENTITY_DEEP_SENTINEL;
	public static BlockEntityType<BlockEntityDeepTotem> BLOCK_ENTITY_DEEP_TOTEM;

	public static final ItemArdentSeed ITEM_ARDENT_SEED = new ItemArdentSeed(new FabricItemSettings().group(ItemGroup.MISC));
	public static final ItemAnimaCore ITEM_ANIMA_CORE = new ItemAnimaCore(new FabricItemSettings().group(ItemGroup.MISC));
	public static final ItemCoreExtractor ITEM_CORE_EXTRACTOR = new ItemCoreExtractor(new FabricItemSettings().group(ItemGroup.MISC));
	public static final ItemVitalicOriel ITEM_VITALIC_ORIEL = new ItemVitalicOriel(new FabricItemSettings().group(ItemGroup.MISC));
	public static final HeartOrphicSource ITEM_ORPHIC_HEART = new HeartOrphicSource(new FabricItemSettings().group(ItemGroup.MISC));
	public static final ItemAtuCharm ITEM_ATU_CHARM = new ItemAtuCharm(new FabricItemSettings().group(ItemGroup.MISC));


	public static final ScreenHandlerType<ScreenHandlerAnimaModifier> SCREEN_HANDLER_ANIMA_MODIFIER = ScreenHandlerRegistry.registerExtended(id("anima_modifier"), ScreenHandlerAnimaModifier::new);
	public static final ScreenHandlerType<ScreenHandlerMineralSynthesizer> SCREEN_HANDLER_MACHINE = ScreenHandlerRegistry.registerSimple(id("mineral_synthesizer"), ScreenHandlerMineralSynthesizer::new);

	public static final TagKey<Item> STONE_KEY = TagKey.of(Registry.ITEM_KEY, new Identifier("c","stone"));
	public static final TagKey<Item> ORE_KEY = TagKey.of(Registry.ITEM_KEY, new Identifier("c","ore"));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initializing Orphic");


		// BLOCKS - SIMPLE
		Registry.register(Registry.BLOCK, id("abstract_stone"), BLOCK_ABSTRACT_STONE);
		Registry.register(Registry.ITEM, id("abstract_stone"), new BlockItem(BLOCK_ABSTRACT_STONE, new FabricItemSettings().group(ItemGroup.MISC)));



		// ITEMS
		Registry.register(Registry.ITEM, id("ardent_seed"), ITEM_ARDENT_SEED);
		Registry.register(Registry.ITEM, id("anima_core"), ITEM_ANIMA_CORE);
		Registry.register(Registry.ITEM, id("core_extractor"), ITEM_CORE_EXTRACTOR);
		Registry.register(Registry.ITEM, id("vitalic_oriel"), ITEM_VITALIC_ORIEL);
		Registry.register(Registry.ITEM, id("orphic_heart"), ITEM_ORPHIC_HEART);
		Registry.register(Registry.ITEM, id("atu_charm"), ITEM_ATU_CHARM);

		// BLOCKS - COMPLEX
		Registry.register(Registry.BLOCK, id("mineral_synthesizer"), BLOCK_MINERAL_SYNTHESIZER);
		Registry.register(Registry.ITEM, id("mineral_synthesizer"), new BlockItem(BLOCK_MINERAL_SYNTHESIZER, new FabricItemSettings().group(ItemGroup.MISC)));
		BLOCK_ENTITY_MINERAL_SYNTHESIZER = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("mineral_synthesizer"), FabricBlockEntityTypeBuilder.create(BlockEntityMineralSynthesizer::new, BLOCK_MINERAL_SYNTHESIZER).build(null));

		Registry.register(Registry.BLOCK, id("deep_sentinel"), BLOCK_DEEP_SENTINEL);
		Registry.register(Registry.ITEM, id("deep_sentinel"), new BlockItem(BLOCK_DEEP_SENTINEL, new FabricItemSettings().group(ItemGroup.MISC)));
		BLOCK_ENTITY_DEEP_SENTINEL = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("deep_sentinel"), FabricBlockEntityTypeBuilder.create(BlockEntityDeepSentinel::new, BLOCK_DEEP_SENTINEL).build(null));

		Registry.register(Registry.BLOCK, id("deep_totem"), BLOCK_DEEP_TOTEM);
		Registry.register(Registry.ITEM, id("deep_totem"), new BlockItem(BLOCK_DEEP_TOTEM, new FabricItemSettings().group(ItemGroup.MISC)));
		BLOCK_ENTITY_DEEP_TOTEM = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("deep_totem"), FabricBlockEntityTypeBuilder.create(BlockEntityDeepTotem::new, BLOCK_DEEP_TOTEM).build(null));

		// DIMENSIONS

		// FUELS
		FuelRegistry.INSTANCE.add(ITEM_ARDENT_SEED, 300);


	}

	public static Identifier id(String name){
		return new Identifier(MOD_ID, name);
	}


}
