package io.github.entityplantt.kredstone.block_entities;

import io.github.entityplantt.kredstone.ModBlockEntities;
import io.github.entityplantt.kredstone.ModBlocks;
import io.github.entityplantt.kredstone.ModComponents;
import io.github.entityplantt.kredstone.network.BlockPosPayload;
import io.github.entityplantt.kredstone.screenhandlers.BurnerBlockScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BurnerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosPayload> {
	public static final String KEY_ROD = "rod", KEY_FUEL = "fuel", KEY_BURNLEFT = "burn_left", KEY_BURNTOTAL = "burn_total";
	public static final Text DISPLAY_NAME = Text.translatable(ModBlocks.BURNER.getTranslationKey());
	
	ItemStack rod = ItemStack.EMPTY, fuel = ItemStack.EMPTY;
	int burnLeft = 0, burnTotal = 0;

	public BurnerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.BURNER, pos, state);
	}

	public int rodCharge() {
		return rod.getOrDefault(ModComponents.FUEL, 0);
	}

	@Override
	protected void readData(ReadView view) {
		rod = view.read(KEY_ROD, ItemStack.CODEC).orElse(ItemStack.EMPTY);
		fuel = view.read(KEY_FUEL, ItemStack.CODEC).orElse(ItemStack.EMPTY);
		burnLeft = view.getInt(KEY_BURNLEFT, 0);
		burnTotal = view.getInt(KEY_BURNTOTAL, 0);
	}

	@Override
	protected void writeData(WriteView view) {
		view.put(KEY_ROD, ItemStack.CODEC, rod);
		view.put(KEY_FUEL, ItemStack.CODEC, fuel);
		view.putInt(KEY_BURNLEFT, burnLeft);
		view.putInt(KEY_BURNTOTAL, burnTotal);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(WrapperLookup registries) {
		return createNbt(registries);
	}

	public static void tick(World world, BlockPos pos, BlockState state, BurnerBlockEntity entity) {
		if (entity.burnLeft == 0 && !entity.fuel.isEmpty() && !entity.rod.isEmpty()) {
			int fuel = world.getFuelRegistry().getFuelTicks(entity.fuel);
			entity.fuel.decrement(1);
			entity.burnLeft = entity.burnTotal = fuel;
		}
		if (entity.burnLeft > 0) {
			if (!entity.rod.isEmpty())
				entity.rod.set(ModComponents.FUEL, entity.rod.getOrDefault(ModComponents.FUEL, 0) + 1);
			--entity.burnLeft;
			if (!state.get(Properties.LIT))
				world.setBlockState(pos, state.with(Properties.LIT, true));
		} else if (state.get(Properties.LIT))
			world.setBlockState(pos, state.with(Properties.LIT, false));
	}

	@Override
	public BlockPosPayload getScreenOpeningData(ServerPlayerEntity player) {
		return new BlockPosPayload(this.pos);
	}

	@Override
	public Text getDisplayName() {
		return DISPLAY_NAME;
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new BurnerBlockScreenHandler(syncId, inventory, this);
	}

	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
}