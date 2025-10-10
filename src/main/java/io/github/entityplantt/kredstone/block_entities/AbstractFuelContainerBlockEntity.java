package io.github.entityplantt.kredstone.block_entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;

public class AbstractFuelContainerBlockEntity extends BlockEntity {
	protected int maxfuel = 5000;
	protected int fuel = 0;
	protected static final String KEY_FUEL = "fuel";

	public AbstractFuelContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup lookup) {
		return createNbt(lookup);
	}

	@Override
	protected void readData(ReadView view) {
		super.readData(view);
		fuel = view.getInt(KEY_FUEL, 0);
	}

	@Override
	protected void writeData(WriteView view) {
		view.putInt(KEY_FUEL, fuel);
		super.writeData(view);
	}

	public int getFuel() {
		return fuel;
	}
	public void changeFuel(int delta) {
		fuel = Math.clamp(fuel + delta, 0, this.maxfuel);
	}
}