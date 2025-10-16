package io.github.entityplantt.kredstone.screens;

import io.github.entityplantt.kredstone.KRedstone;
import io.github.entityplantt.kredstone.screenhandlers.BurnerBlockScreenHandler;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BurnerBlockScreen extends HandledScreen<BurnerBlockScreenHandler> {
	private static final Identifier TEXTURE = KRedstone.id("textures/gui/container/burner.png");
	private static final Identifier BURN_TEXTURE = Identifier
			.ofVanilla("textures/gui/sprites/container/furnace/burn_progress.png");

	public BurnerBlockScreen(BurnerBlockScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	protected void init() {
		super.init();
		titleX = backgroundWidth - textRenderer.getWidth(title) >> 1;
		backgroundWidth = 176;
		backgroundHeight = 132;
	}

	@Override
	protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
		context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight,
				backgroundWidth,
				backgroundHeight);
		var entity = handler.getEntity();
		context.drawTexture(RenderPipelines.GUI_TEXTURED, BURN_TEXTURE, x + 76, y + 22, 0, 0,
				entity.burnTotal == 0 ? 0 : 24 - entity.burnLeft * 24 / entity.burnTotal, 16, 24, 16);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
		drawMouseoverTooltip(context, mouseX, mouseY);
	}
}