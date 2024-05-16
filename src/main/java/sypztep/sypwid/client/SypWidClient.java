package sypztep.sypwid.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class SypWidClient implements ClientModInitializer {
    public static final String MODID = "sypwid";
    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }
    public static SypWidConfig CONFIG = new SypWidConfig();
    @Override
    public void onInitializeClient() {
        AutoConfig.register(SypWidConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(SypWidConfig.class).getConfig();
    }
}
