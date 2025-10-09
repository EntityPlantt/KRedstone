package io.github.entityplantt.kredstone.block_entities;

import io.github.entityplantt.kredstone.ModBlockEntities;
import io.github.entityplantt.kredstone.blocks.EncasedCapacitorBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class EncasedCapacitorBlockEntity extends BlockEntity {
	private int fuel = 0;
	private static final String KEY_FUEL = "fuel";

	public EncasedCapacitorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ENCASED_CAPACITOR, pos, state);
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

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup lookup) {
		return createNbt(lookup);
	}

	public static void tick(World world, BlockPos pos, BlockState state, BlockEntity _entity) {
		EncasedCapacitorBlockEntity e = (EncasedCapacitorBlockEntity) _entity;
		if (e.fuel > 0) {
			e.fuel--;
			if (Math.random() < .1) {
				Direction d = state.get(EncasedCapacitorBlock.FACING);
				world.addParticleClient(ParticleTypes.ELECTRIC_SPARK,
						pos.getX() + .5 - d.getOffsetX() * .25,
						pos.getY() + .5 - d.getOffsetY() * .25,
						pos.getZ() + .5 - d.getOffsetZ() * .25,
						Math.random() * .2 - .1,
						Math.random() * .2 - .1,
						Math.random() * .2 - .1);
			}
		} else
			e.fuel = 0;
	}
}