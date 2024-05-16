package sypztep.sypwid.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sypztep.SypzalibMod;
import com.sypztep.util.TextUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.gui.HealthBarUtil;

import static sypztep.sypwid.gui.HealthBarUtil.HeartbarType.drawHeartBar;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public abstract class InGameHudMixin {
    @Shadow public abstract TextRenderer getTextRenderer();
    @Unique
    private int missingHealth = 0;
    @Inject(method = "renderHealthBar", at = @At("HEAD"), cancellable = true)
    private void rendernewHealthbar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        if (!SypWidClient.CONFIG.newHealthbar) {
            return;
        }
        int h = (int) Math.min(78 / player.getMaxHealth() * player.getHealth(), 78);
        if (missingHealth < h)
            missingHealth = h;
         else missingHealth--;

        RenderSystem.enableBlend();

        drawHeartBar(context, HealthBarUtil.HeartbarType.CONTAINER, x, y, HealthBarUtil.healthbarTexture(player), blinking);
        if (missingHealth != h)
            context.drawTexture(HealthBarUtil.GUI_HEALTH_BARS, x, y + 1, 156, 120, missingHealth, 8, 256, 128);

        if (absorption > 0)  // absorption render
            context.drawTexture(HealthBarUtil.GUI_HEALTH_BARS, x, y + 1, 0, 120, h, 8, 256, 128);

        context.drawTexture(HealthBarUtil.GUI_HEALTH_BARS, x, y + 1, 156, HealthBarUtil.healthbarTexture(player), h, 8, 256, 128); //main

        context.drawTexture(HealthBarUtil.GUI_HEALTH_ICONS, x, y, HealthBarUtil.healthiconTexture(player), 0, 9, 9, 256, 256);
        if (SypWidClient.CONFIG.textHealthNumber) {
            String healthbar = HealthBarUtil.HEALTH_FORMAT.format(player.getHealth() + player.getAbsorptionAmount()) + "/" + HealthBarUtil.HEALTH_FORMAT.format(player.getMaxHealth());
            TextUtil.drawText(context, this.getTextRenderer(), healthbar, x + 12, y + 1, 16777215, 0, false);
        }

        RenderSystem.disableBlend();
        ci.cancel();
    }
}
