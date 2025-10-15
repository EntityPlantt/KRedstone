package io.github.entityplantt.kredstone;

import io.github.entityplantt.kredstone.network.BlockPosPayload;
import io.github.entityplantt.kredstone.screenhandlers.*;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;

public class ModScreenHandlers {
	public static <T extends ScreenHandler, P extends CustomPayload> ExtendedScreenHandlerType<T, P> register(
			String name, ExtendedScreenHandlerType.ExtendedFactory<T, P> factory,
			PacketCodec<? super RegistryByteBuf, P> codec) {
		return Registry.register(Registries.SCREEN_HANDLER, KRedstone.id(name),
				new ExtendedScreenHandlerType<>(factory, codec));
	}

	public static ExtendedScreenHandlerType<BurnerBlockScreenHandler, BlockPosPayload> BURNER = register("burner",
			BurnerBlockScreenHandler::new, BlockPosPayload.CODEC);

	public static void init() {
	}
}