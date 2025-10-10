package io.github.entityplantt.kredstone;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KRedstone implements ModInitializer {
	public static final String MOD_ID = "kredstone";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		ModItems.init();
		ModBlocks.init();
		ModBlockEntities.init();
		ModComponents.init();
	}
	public static Identifier id(String s) {
		return Identifier.of(MOD_ID, s);
	}
}