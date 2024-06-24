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


    @ConfigEntry.Category("speedometer")
    public boolean enableSpeedOmeter = true;
    @ConfigEntry.Category("speedometer")
    public boolean showVertical = false;
    @ConfigEntry.Category("speedometer")
    public boolean changeColors = true;
    @ConfigEntry.Category("speedometer")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 1)
    @ConfigEntry.Gui.Tooltip(count = 2)
    public int tickInterval = 15;
    @ConfigEntry.Category("speedometer")
    @ConfigEntry.ColorPicker
    public int textColor = 0xFFFFFF;
    @ConfigEntry.Category("speedometer")
    @ConfigEntry.ColorPicker
    public int acceleratingColor = 0x00FF00;
    @ConfigEntry.Category("speedometer")
    @ConfigEntry.ColorPicker
    public int deceleratingColor = 0xFF0000;
    @ConfigEntry.Category("speedometer")
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public Position position = Position.BOTTOM_LEFT;
    @ConfigEntry.Category("speedometer")
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public SpeedUnit speedUnit = SpeedUnit.BLOCKS_PER_SECOND;
    @ConfigEntry.Category("sort")
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.DROPDOWN)
    public SortAlgorithm sortAlgorithm = SortAlgorithm.BUBBLE_SORT;
    @ConfigEntry.Category("sort")
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

    public enum Position {
        BOTTOM_LEFT, BOTTOM_RIGHT, TOP_LEFT, TOP_RIGHT
    }

    public enum SpeedUnit {
        BLOCKS_PER_SECOND, KILOMETERS_PER_HOUR
    }
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
