package sypztep.sypwid.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import sypztep.sypwid.client.payload.ActionPayloadC2S;
import sypztep.sypwid.client.payload.SortPayloadC2S;

public class SypWid implements ModInitializer {
    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(SortPayloadC2S.ID, SortPayloadC2S.CODEC); // Client to Server
        PayloadTypeRegistry.playC2S().register(ActionPayloadC2S.ID, ActionPayloadC2S.CODEC); // Client to Server

        ServerPlayNetworking.registerGlobalReceiver(SortPayloadC2S.ID, new SortPayloadC2S.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(ActionPayloadC2S.ID, new ActionPayloadC2S.Receiver());
    }
}
