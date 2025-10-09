package io.github.entityplantt.kredstone;

import java.util.function.Function;

import io.github.entityplantt.kredstone.blocks.EncasedCapacitorBlock;
import io.github.entityplantt.kredstone.blocks.ExcitedBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {
	public static <T extends Block> T register(String name, Function<AbstractBlock.Settings, T> blockFactory,
			AbstractBlock.Settings settings, boolean hasItem) {
		RegistryKey<Block> blockKey = keyOfBlock(name);
		T block = blockFactory.apply(settings.registryKey(blockKey));
		if (hasItem) {
			RegistryKey<Item> itemKey = keyOfItem(name);
			BlockItem blockItem = new BlockItem(block,
					new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
			Registry.register(Registries.ITEM, itemKey, blockItem);
			ItemGroupEvents.modifyEntriesEvent(ModItems.ITEM_GROUP_KEY).register(i -> i.add(blockItem));
		}

		return Registry.register(Registries.BLOCK, blockKey, block);
	}

	public static RegistryKey<Block> keyOfBlock(String name) {
		return RegistryKey.of(RegistryKeys.BLOCK, KRedstone.id(name));
	}

	private static RegistryKey<Item> keyOfItem(String name) {
		return RegistryKey.of(RegistryKeys.ITEM, KRedstone.id(name));
	}

	public static final Block STEEL_BLOCK = register("steel_block", Block::new,
			Blocks.IRON_BLOCK.getSettings().mapColor(MapColor.GRAY), true);
	public static final Block MACHINE_CORE = register("machine_core", Block::new,
			Blocks.IRON_BLOCK.getSettings(), true);
	public static final Block EXCITED_BEDROCK = ExcitedBlock.register(Blocks.BEDROCK);
	public static final Block EXCITED_OBSIDIAN = ExcitedBlock.register(Blocks.OBSIDIAN);
	public static final Block EXCITED_CRYING_OBSIDIAN = ExcitedBlock.register(Blocks.CRYING_OBSIDIAN);
	public static final EncasedCapacitorBlock ENCASED_CAPACITOR = register("encased_capacitor",
			EncasedCapacitorBlock::new,
			AbstractBlock.Settings.create()
					.pistonBehavior(PistonBehavior.DESTROY).strength(0.5f).mapColor(MapColor.CLEAR)
					.nonOpaque().sounds(BlockSoundGroup.STONE).dynamicBounds(),
			true);

	public static void init() {
	}
}