package io.github.entityplantt.kredstone.entity_rendering;

import io.github.entityplantt.kredstone.ModBlocks;
import io.github.entityplantt.kredstone.block_entities.EncasedCapacitorBlockEntity;
import io.github.entityplantt.kredstone.blocks.EncasedCapacitorBlock;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.command.ModelCommandRenderer.CrumblingOverlayCommand;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class EncasedCapacitorBlockEntityRenderer
		implements BlockEntityRenderer<EncasedCapacitorBlockEntity, EncasedCapacitorBlockEntityRenderer.State> {
	private BlockEntityRendererFactory.Context ctx;

	public EncasedCapacitorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.ctx = ctx;
	}

	public static class State extends BlockEntityRenderState {
		public int fuel = 0, maxFuel = 1, alpha = 0;
		public Direction d = Direction.UP;
		public boolean shouldRender = true;
	}

	@Override
	public State createRenderState() {
		return new State();
	}

	@Override
	public void render(State state, MatrixStack matrices, OrderedRenderCommandQueue queue,
			CameraRenderState cameraState) {
		if (!state.shouldRender)
			return;
		var text = Text.literal(Integer.toString(state.fuel) + "/" + Integer.toString(state.maxFuel)).asOrderedText();
		matrices.push();
		matrices.translate(.5, .5, .5);
		matrices.multiply(state.d.getRotationQuaternion());
		matrices.translate(0, -.437, .125);
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
		matrices.scale(.005f, .005f, .005f);
		queue.submitText(matrices, ctx.textRenderer().getWidth(text) / -2, 0, text, false, TextLayerType.NORMAL,
				state.lightmapCoordinates,
				(state.fuel == state.maxFuel ? 0x00ff00 : state.fuel == 0 ? 0xff0000 : 0xffffff)
						+ state.alpha * 0x1000000,
				0, 0);
		matrices.pop();
	}

	@Override
	public void updateRenderState(EncasedCapacitorBlockEntity blockEntity, State state, float tickProgress,
			Vec3d cameraPos, CrumblingOverlayCommand crumblingOverlay) {
		var dist = cameraPos.distanceTo(new Vec3d(blockEntity.getPos()));
		if (!(state.shouldRender = dist < 10))
			return;
		state.alpha = Math.clamp(255 - (int) (dist * 51), 0, 255);
		BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
		state.maxFuel = blockEntity.getMaxFuel();
		state.fuel = blockEntity.getFuel();
		var blockState = blockEntity.getWorld().getBlockState(blockEntity.getPos());
		if (!blockState.isOf(ModBlocks.ENCASED_CAPACITOR))
			state.shouldRender = false;
		else
			state.d = blockState.get(EncasedCapacitorBlock.FACING);
	}
}