package sypztep.sypwid.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.Arrays;
import java.util.List;

@Config(name = SypWidClient.MODID)
@Config.Gui.Background("minecraft:textures/block/dirt.png")

public class SypWidConfig implements ConfigData {
    @Comment("New Healthbar")
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean newHealthbar = true;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int xoffsethealthbar = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int yoffsethealthbar = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean newArmor = true;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int xoffsetarmor = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int yoffsetarmor = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean newAir = true;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int xoffair = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int yoffair = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean textHealthNumber = true;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int xoffsettexthealthbar = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int yoffsettexthealthbar = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean textArmorNumber = true;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int xoffsettextarmor = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int yoffsettextarmor = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public boolean textArmorToughnessNumber = true;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int xoffsettextarmorToughness = 0;
    @ConfigEntry.Category("visual")
    @ConfigEntry.Gui.Tooltip
    public int yoffsettextarmorToughness = 0;

    @ConfigEntry.Category("sort")
    @ConfigEntry.Gui.Tooltip(count = 1)
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.DROPDOWN)
    public SortAlgorithm sortAlgorithm = SortAlgorithm.BUBBLE_SORT;
    @ConfigEntry.Category("sort")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public List<String> sortOrder = Arrays.asList(
            "armor",
            "tools",
            "name",
            "blocks",
            "items",
            "stackables",
            "unstackables"
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
