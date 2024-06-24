package sypztep.sypwid.client.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.screen.ScreenHandler;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.client.util.Action;

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
            switch (payload.select()) {
                case 0: Action.DEPOSITE.doAction(container); break;
                case 1: Action.DEPOSITEALL.doAction(container); break;
                case 2: Action.WITHDRAW.doAction(container); break;
            }
        }
    }
}
