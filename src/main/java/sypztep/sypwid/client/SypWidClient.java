package sypztep.sypwid.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.util.Identifier;
import sypztep.sypwid.client.event.ElytraRenderEvent;
import sypztep.sypwid.client.event.SpeedOmeterRenderEvent;
import sypztep.sypwid.client.util.SortType;

public class SypWidClient implements ClientModInitializer {
    public static final String MODID = "sypwid";
    public static Identifier id(String id) {
        return Identifier.of(MODID, id);
    }
    public static SypWidConfig CONFIG = new SypWidConfig();
    @Override
    public void onInitializeClient() {
        AutoConfig.register(SypWidConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(SypWidConfig.class).getConfig();

        HudRenderCallback.EVENT.register(new ElytraRenderEvent());
        HudRenderCallback.EVENT.register(new SpeedOmeterRenderEvent());
    }
}
