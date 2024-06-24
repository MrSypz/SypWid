package sypztep.sypwid.client.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.client.payload.ActionPayloadC2S;

import java.util.Collections;

@Environment(EnvType.CLIENT)
public final class DepositWidgetButton extends ActionWidgetButton {
    private static final Identifier BUTTON_TEXTURE = SypWidClient.id("hud/bar/container/deposit");
    private static final Identifier BUTTON_HOVER_TEXTURE = SypWidClient.id("hud/bar/container/deposit_hover");

    public DepositWidgetButton(int x, int y, int width, int height, Text message, HandledScreen<?> screen) {
        super(x, y, width, height, message, screen, BUTTON_TEXTURE, BUTTON_HOVER_TEXTURE,
                Collections.singletonList(Text.literal("Deposit items")),
                Collections.singletonList(Text.literal("Deposit all items")));
    }
    @Override
    public void onClick(double mouseX, double mouseY) {
        boolean isShiftHeld = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT);
        if (isShiftHeld)
            ActionPayloadC2S.send((byte) 1);
         else
            ActionPayloadC2S.send((byte) 0);
    }
}