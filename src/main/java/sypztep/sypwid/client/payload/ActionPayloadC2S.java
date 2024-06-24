package sypztep.sypwid.client.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.screen.ScreenHandler;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.client.util.ActionContainer;

public record ActionPayloadC2S(byte select) implements CustomPayload {
    public static final Id<ActionPayloadC2S> ID = new Id<>(SypWidClient.id("action_payload"));
    public static final PacketCodec<PacketByteBuf, ActionPayloadC2S> CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE,
            ActionPayloadC2S::select,
            ActionPayloadC2S::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(byte select) {
        ClientPlayNetworking.send(new ActionPayloadC2S(select));
    }
    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<ActionPayloadC2S> {
        @Override
        public void receive(ActionPayloadC2S payload, ServerPlayNetworking.Context context) {
            ScreenHandler container = context.player().currentScreenHandler;
            if (payload.select() == 0)
                ActionContainer.deposit(container);
            else if (payload.select() == 1)
                ActionContainer.depositAll(container);
            else if (payload.select() == 2)
                ActionContainer.lootAll(container);
        }
    }
}
