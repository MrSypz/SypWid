package sypztep.sypwid.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import sypztep.sypwid.client.SypWidClient;

import java.text.DecimalFormat;


public class HealthBar {
    private static final Identifier[] HEALTH_BARS = new Identifier[9];
    private static final Identifier[] SLATE_BARS = new Identifier[6];
    public static final DecimalFormat DECI_FORMAT = new DecimalFormat("#.##");

    public static void drawContainerBar(DrawContext context, int x, int y, boolean blinking, PlayerEntity player) {
        context.drawGuiTexture(getBarTexture(containerTexture(player), blinking), x, y + 1, 78, 8);
    }

    public static void drawCustomContainerBar(DrawContext context, int x, int y,int type) {
        context.drawGuiTexture(getBarTexture(type, false), x, y + 1, 78, 8);
    }

    public static void drawSlateBar(DrawContext context, int x, int y, PlayerEntity player, int width) {
        context.drawGuiTexture(getSlateTexture(slateTexture(player)), 78, 8, 0, 0, x, y + 1, width, 8);
    }

    public static void drawCustomSlateBar(DrawContext context, int x, int y,SlateType type, int width) {
        context.drawGuiTexture(getSlateTexture(type.getType()), 78, 8, 0, 0, x, y + 1, width, 8);
    }
    public static void drawCustomSlateBar(DrawContext context, int x, int y,int u,int v,SlateType type, int width,int height) {
        context.drawGuiTexture(getSlateTexture(type.getType()), u, v, 0, 0, x, y + 1, width, height);
    }

    private static int containerTexture(final PlayerEntity player) { //แสดง texture สีอื่นๆ
        return player.hasStatusEffect(StatusEffects.POISON) ? 2 : (player.hasStatusEffect(StatusEffects.WITHER) ? 4 : (player.isFrozen() ? 6 : 0));
    }

    private static int slateTexture(final PlayerEntity player) { //แสดง texture สีอื่นๆ
        return player.hasStatusEffect(StatusEffects.POISON) ? 1 : (player.hasStatusEffect(StatusEffects.WITHER) ? 2 : (player.isFrozen() ? 3 : 0));
    }
    public enum SlateType {
        NORMAL(0),
        POISON(1),
        WITHER(2),
        FROZEN(3),
        WHITE(4),
        ARMORTOUGHNESS(5);
        private final int type;
        SlateType(int index) {
            this.type = index;
        }
        public int getType() {
            return type;
        }
    }

    private static Identifier getBarTexture(int i, boolean blinking) {
        i %= HEALTH_BARS.length;
        if (i < 0) {
            i += HEALTH_BARS.length;
        }
        if (blinking) {
            i += HEALTH_BARS.length % 2; // Blink
        }
        return HEALTH_BARS[i];
    }

    private static Identifier getSlateTexture(int i) {
        i %= SLATE_BARS.length;
        if (i < 0) {
            i += SLATE_BARS.length;
        }
        return SLATE_BARS[i];
    }

    static {
        for (int i = 0; i < HEALTH_BARS.length; i++) {
            HEALTH_BARS[i] = SypWidClient.id("hud/bar/container/bar_" + i);
        }
        for (int i = 0; i < SLATE_BARS.length; i++) {
            SLATE_BARS[i] = SypWidClient.id("hud/bar/slate/slate_" + i);
        }
    }
}
