package sypztep.sypwid.mixin.healthgui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.client.util.HealthBar;


@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public abstract class InGameHudMixin {
    @Unique
    private static final Identifier ARMOR_TEXTURE =  Identifier.ofVanilla("hud/armor_full");
    @Unique
    private static final Identifier AIR_TEXTURE =  Identifier.ofVanilla("hud/air");
    @Unique
    private static final Identifier AIR_BURSTING_TEXTURE =  Identifier.ofVanilla("hud/air_bursting");

    @Shadow
    public abstract TextRenderer getTextRenderer();

    @Shadow
    @Nullable
    protected abstract PlayerEntity getCameraPlayer();

    @Shadow
    @Final
    private MinecraftClient client;
    @Unique
    private int missingHealth = 0;

    @Inject(method = "renderHealthBar", at = @At("HEAD"), cancellable = true)
    private void rendernewHealthbar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        if (!SypWidClient.CONFIG.newHealthbar) return;
        InGameHud.HeartType heartType = InGameHud.HeartType.fromPlayerState(player);//
        boolean hardcore = player.getWorld().getLevelProperties().isHardcore();
        int newHealth = (int) Math.min(78 / player.getMaxHealth() * player.getHealth(), 78);
        if (missingHealth < newHealth) missingHealth = newHealth;
        else missingHealth--;

        RenderSystem.enableBlend();
        HealthBar.drawContainerBar(context, x, y, blinking, player); // เหี้ยอะไรเนี่ย อ๋อ ทุกอย่างอยู่ใน Healthbar.java

        if (missingHealth != newHealth)
            HealthBar.drawCustomSlateBar(context, x, y, HealthBar.SlateType.WHITE, missingHealth);

        if (absorption > 0) HealthBar.drawCustomContainerBar(context, x, y, 8);

        HealthBar.drawSlateBar(context, x, y, player, newHealth);
        /*        ใช้ของ vanilla         */
        this.drawHeart(context, InGameHud.HeartType.CONTAINER, x, y, hardcore, blinking);
        this.drawHeart(context, heartType, x, y, hardcore, false);
        if (SypWidClient.CONFIG.textHealthNumber) {
            String healthbar = HealthBar.DECI_FORMAT.format(player.getHealth() + player.getAbsorptionAmount()) + "/" + HealthBar.DECI_FORMAT.format(player.getMaxHealth());
            context.drawTextWithShadow(this.getTextRenderer(), healthbar, x + 12, y + 1, 0xFFFFFF);
        }
        context.setShaderColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
        ci.cancel();
    }

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    private static void renderNewArmor(DrawContext context, PlayerEntity player, int i, int j, int k, int x, CallbackInfo ci) {
        if (!SypWidClient.CONFIG.newArmor) return;
        int y = i - 10;
        int armortoughness = MathHelper.floor(player.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
        int armor = player.getArmor(), armorbar = (int) Math.min(armor * 3.9f, 78), armortoughnessbar = (int) Math.min(armortoughness * 6.3, 78);
        if (armor <= 0) {
            return;
        }
        RenderSystem.enableBlend();
        HealthBar.drawCustomContainerBar(context, x, y, 0); // เหี้ยอะไรเนี่ย อ๋อ ทุกอย่างอยู่ใน Healthbar.java
        HealthBar.drawCustomSlateBar(context, x, y, HealthBar.SlateType.WHITE, armorbar);// แผ่นเกราะ
        context.drawGuiTexture(ARMOR_TEXTURE, x, y, 9, 9);// icon เกราะ
        if (SypWidClient.CONFIG.textArmorNumber)
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, String.valueOf(armor), x + 12, y + 1, 0xFFFFFF);
        if (armortoughness > 0) {
            HealthBar.drawCustomContainerBar(context, x + 101, y, 0); // เหี้ยอะไรเนี่ย อ๋อ ทุกอย่างอยู่ใน Healthbar.java
            HealthBar.drawCustomSlateBar(context, x + 101, y, HealthBar.SlateType.WHITE, armortoughnessbar); // แผ่นเกราะ
            HealthBar.drawCustomSlateBar(context, x + 101, y - 1, 9, 9, HealthBar.SlateType.ARMORTOUGHNESS, 9, 9); // icon เกราะ
            if (SypWidClient.CONFIG.textArmorToughnessNumber)
                context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, String.valueOf(armortoughness), x + 112, y + 1, 0xFFFFFF);
        }
        RenderSystem.disableBlend();
        ci.cancel();
    }

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getMaxAir()I"), cancellable = true)
    private void airandcoolstuff(DrawContext context, CallbackInfo ci) {
        if (!SypWidClient.CONFIG.newAir) return;
        PlayerEntity player = this.getCameraPlayer();
        int x = context.getScaledWindowWidth() / 2 - 7;
        int y = context.getScaledWindowHeight() - 49;
        int airLevel = (int) (100.0F * Math.max((float) player.getAir(), 0.0F) / (float) player.getMaxAir());
        RenderSystem.enableBlend();
        if (player.isSubmergedIn(FluidTags.WATER) || airLevel < 100) {
            if (airLevel != 0) context.drawGuiTexture(AIR_TEXTURE, x + 2, y, 9, 9);
            else context.drawGuiTexture(AIR_BURSTING_TEXTURE, x + 2, y, 9, 9);
            context.drawText(this.client.textRenderer, "% " + airLevel, x, y - 10, 0xFFFFFF, true);
        }
        RenderSystem.disableBlend();
        this.client.getProfiler().pop();
        ci.cancel();
    }

    @Unique
    private void drawHeart(DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking) {
        RenderSystem.enableBlend();
        context.drawGuiTexture(type.getTexture(hardcore, false, blinking), x, y, 9, 9);
        RenderSystem.disableBlend();
    }
}
