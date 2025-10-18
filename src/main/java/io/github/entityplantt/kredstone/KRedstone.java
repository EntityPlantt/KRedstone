package io.github.entityplantt.kredstone;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.entityplantt.kredstone.block_entities.BurnerBlockEntity;

public class KRedstone implements ModInitializer {
	public static final String MOD_ID = "kredstone";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Direction[] DALL = { Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH,
			Direction.EAST, Direction.WEST };

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		ModItems.init();
		ModBlocks.init();
		ModBlockEntities.init();
		ModComponents.init();
		ModScreenHandlers.init();

		ItemStorage.SIDED.registerForBlockEntity(BurnerBlockEntity::getItemStorage, ModBlockEntities.BURNER);
	}

	public static Identifier id(String s) {
		return Identifier.of(MOD_ID, s);
	}
}