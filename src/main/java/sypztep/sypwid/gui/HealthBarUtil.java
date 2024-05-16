package sypztep.sypwid.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import sypztep.sypwid.client.SypWidClient;

import java.text.DecimalFormat;

public class HealthBarUtil {
    public static final Identifier GUI_HEALTH_BARS = SypWidClient.id("textures/gui/health_bars.png");
    public static final Identifier GUI_HEALTH_ICONS = SypWidClient.id("textures/gui/icon2.png");
    public static final DecimalFormat HEALTH_FORMAT = new DecimalFormat("#.##");

    @Environment(value = EnvType.CLIENT)
    public static enum HeartbarType {
        CONTAINER(0, false);
        private final int textureIndex;
        private final boolean hasBlinkingTexture;
        private HeartbarType(int textureIndex, boolean hasBlinkingTexture) {
            this.textureIndex = textureIndex;
            this.hasBlinkingTexture = hasBlinkingTexture;
        }
        public int getU(boolean blinking) {
            int i;
            if (this == CONTAINER) {
                i = blinking ? 1 : 0;
            } else {
                i = this.hasBlinkingTexture && blinking ? 2 : 0;
            }
            return (this.textureIndex + i) * 78;
        }
        public static void drawHeartBar(DrawContext context, HeartbarType type, int x, int y, int v, boolean blinking) {
            context.drawTexture(GUI_HEALTH_BARS, x, y + 1, type.getU(blinking), v, 78, 8, 256, 128);
        }
    }
    public static int healthbarTexture(final PlayerEntity player) { //แสดง texture สีอื่นๆ
        return player.hasStatusEffect(StatusEffects.POISON) ? 8 : (player.hasStatusEffect(StatusEffects.WITHER) ? 16 : (player.isFrozen() ? 24 : 0 ));
    }
    public static int healthiconTexture(final PlayerEntity player) {
        return player.hasStatusEffect(StatusEffects.POISON) ? 9 : (player.hasStatusEffect(StatusEffects.WITHER) ? 18 : player.isFrozen() ? 27 : player.hasStatusEffect(StatusEffects.ABSORPTION) ? 45 : 0);
    }
}
