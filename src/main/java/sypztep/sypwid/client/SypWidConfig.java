package sypztep.sypwid.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = SypWidClient.MODID)
@Config.Gui.Background("minecraft:textures/block/dirt.png")

public class SypWidConfig implements ConfigData {
    @Comment("Get anyslot but not for offhand.")
    @ConfigEntry.Category("gameplay")
    public boolean newHealthbar = true;
    @ConfigEntry.Category("gameplay")
    public boolean textHealthNumber = true;
}
