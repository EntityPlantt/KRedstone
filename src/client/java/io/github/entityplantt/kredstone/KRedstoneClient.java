package io.github.entityplantt.kredstone;

import io.github.entityplantt.kredstone.screens.BurnerBlockScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;

public class KRedstoneClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT, ModBlocks.ENCASED_CAPACITOR);
		HandledScreens.register(ModScreenHandlers.BURNER, BurnerBlockScreen::new);
	}
}