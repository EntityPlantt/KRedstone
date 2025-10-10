package io.github.entityplantt.kredstone;

import com.mojang.serialization.Codec;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModComponents {
	public static final ComponentType<Integer> FUEL = Registry.register(
			Registries.DATA_COMPONENT_TYPE,
			KRedstone.id("fuel"),
			ComponentType.<Integer>builder().codec(Codec.INT).build());

	public static void init() {
		KRedstone.LOGGER.info("Registering mod components");
	}
}