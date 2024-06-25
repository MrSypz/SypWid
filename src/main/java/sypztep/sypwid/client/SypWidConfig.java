package sypztep.sypwid.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Config(name = SypWidClient.MODID)
@Config.Gui.Background("minecraft:textures/block/dirt.png")

public class SypWidConfig implements ConfigData {
    @Comment("New Healthbar")
    @ConfigEntry.Category("visual")
    public boolean newHealthbar = true;
    @ConfigEntry.Category("visual")
    public boolean newArmor = true;
    @ConfigEntry.Category("visual")
    public boolean newAir = true;
    @ConfigEntry.Category("visual")
    public boolean textHealthNumber = true;
    @ConfigEntry.Category("visual")
    public boolean textArmorNumber = true;
    @ConfigEntry.Category("visual")
    public boolean textArmorToughnessNumber = true;

    @ConfigEntry.Category("sort")
    @ConfigEntry.Gui.Tooltip(count = 1)
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.DROPDOWN)
    public SortAlgorithm sortAlgorithm = SortAlgorithm.BUBBLE_SORT;
    @ConfigEntry.Category("sort")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public List<String> sortOrder = Arrays.asList(
            "armor",
            "tools",
            "blocks",
            "items",
            "stackables",
            "unstackables",
            "item_group_order/minecraft:search",
            "damage",
            "count"
    );

    public enum SortAlgorithm {
        MERGE_SORT((byte) 0),
        BUBBLE_SORT((byte) 1);

        private final byte byteValue;

        SortAlgorithm(byte byteValue) {
            this.byteValue = byteValue;
        }

        public byte getByteValue() {
            return byteValue;
        }
    }

}
