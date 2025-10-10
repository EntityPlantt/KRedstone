package io.github.entityplantt.kredstone;

import io.github.entityplantt.kredstone.block_entities.EncasedCapacitorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {
	private static <T extends BlockEntity> BlockEntityType<T> register(
			String name,
			FabricBlockEntityTypeBuilder.Factory<? extends T> factory,
			Block... blocks) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, KRedstone.id(name),
				FabricBlockEntityTypeBuilder.<T>create(factory, blocks).build());
	}

	public static final BlockEntityType<EncasedCapacitorBlockEntity> ENCASED_CAPACITOR = register("encased_capacitor",
			EncasedCapacitorBlockEntity::new, ModBlocks.ENCASED_CAPACITOR);

	public static void init() {
		KRedstone.LOGGER.info("Registering mod block entities");
	}
}