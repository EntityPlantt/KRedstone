package io.github.entityplantt.kredstone.block_entities;

import io.github.entityplantt.kredstone.ModBlockEntities;
import io.github.entityplantt.kredstone.blocks.EncasedCapacitorBlock;
import io.github.entityplantt.kredstone.blocks.IPowerableBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class EncasedCapacitorBlockEntity extends AbstractFuelContainerBlockEntity {
	public EncasedCapacitorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ENCASED_CAPACITOR, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, BlockEntity _entity) {
		EncasedCapacitorBlockEntity e = (EncasedCapacitorBlockEntity) _entity;
		Direction d = state.get(EncasedCapacitorBlock.FACING);
		if (e.fuel > 0) {
			BlockPos neighborPos = pos.offset(d.getOpposite());
			BlockState neighbor = world.getBlockState(neighborPos);
			if (neighbor.getBlock() instanceof IPowerableBlock) {
				IPowerableBlock b = (IPowerableBlock) neighbor.getBlock();
				int requiredPower = b.powerNeeds(world, neighborPos, neighbor, d);
				if (requiredPower <= e.fuel) {
					e.fuel -= requiredPower;
					b.getPowered(world, neighborPos, neighbor, d);
				}
			}
			if (Math.random() < .1) {
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