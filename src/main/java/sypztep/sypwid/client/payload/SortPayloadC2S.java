package sypztep.sypwid.client.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.screen.slot.Slot;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.client.util.Sort;

import java.util.List;

public record SortPayloadC2S(int syncId,int startIndex, int endIndex, byte algorithm) implements CustomPayload {
    public static final Id<SortPayloadC2S> ID = new Id<>(SypWidClient.id("sort_payload"));
    public static final PacketCodec<PacketByteBuf, SortPayloadC2S> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            SortPayloadC2S::syncId,
            PacketCodecs.VAR_INT,
            SortPayloadC2S::startIndex,
            PacketCodecs.VAR_INT,
            SortPayloadC2S::endIndex,
            PacketCodecs.BYTE,
            SortPayloadC2S::algorithm,
            SortPayloadC2S::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(int syncId,int startIndex, int endIndex, byte algorithm) {
        ClientPlayNetworking.send(new SortPayloadC2S(syncId,startIndex,endIndex, algorithm));
    }
    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SortPayloadC2S> {
        @Override
        public void receive(SortPayloadC2S payload, ServerPlayNetworking.Context context) {
            var player = context.player();
            List<Slot> slots = context.player().currentScreenHandler.slots;
            switch (payload.algorithm) {
                case 0: Sort.MERGESORT.doSort(player, payload.syncId(), slots, payload.startIndex(), payload.endIndex());
                break;
                case 1: Sort.BUBBLE_SORT.doSort(player, payload.syncId(), slots, payload.startIndex(), payload.endIndex());
            }
        }
    }
}
