package sypztep.sypwid.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import sypztep.sypwid.client.payload.SortPayloadC2S;

public class SypWid implements ModInitializer {
    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(SortPayloadC2S.ID, SortPayloadC2S.CODEC); // Client to Server

        ServerPlayNetworking.registerGlobalReceiver(SortPayloadC2S.ID, new SortPayloadC2S.Receiver());
    }
}
