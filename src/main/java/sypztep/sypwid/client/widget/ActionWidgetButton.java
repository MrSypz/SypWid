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

import java.util.List;

@Environment(EnvType.CLIENT)
public abstract class ActionWidgetButton extends ClickableWidget {
    private final Identifier buttonTexture;
    private final Identifier buttonHoverTexture;
    private final HandledScreen<?> screen;
    private final List<Text> tooltip;
    private final List<Text> shiftTooltip;

    public ActionWidgetButton(int x, int y, int width, int height, Text message, HandledScreen<?> screen,
                              Identifier buttonTexture, Identifier buttonHoverTexture, List<Text> tooltip, List<Text> shiftTooltip) {
        super(x, y, width, height, message);
        this.screen = screen;
        this.buttonTexture = buttonTexture;
        this.buttonHoverTexture = buttonHoverTexture;
        this.tooltip = tooltip;
        this.shiftTooltip = shiftTooltip;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(buttonTexture, getX(), getY(), getWidth(), getHeight());

        if (isHovered()) {
            context.drawGuiTexture(buttonHoverTexture, getX(), getY(), getWidth(), getHeight());

            boolean isShiftHeld = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT);
            if (isShiftHeld) {
                renderShiftTooltip(context, mouseX, mouseY);
            } else {
                renderTooltip(context, mouseX, mouseY);
            }
        }
    }
    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }
    protected void renderTooltip(DrawContext context, int mouseX, int mouseY) {
        if (!tooltip.isEmpty()) {
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, tooltip, mouseX, mouseY);
        }
    }

    protected void renderShiftTooltip(DrawContext context, int mouseX, int mouseY) {
        if (!shiftTooltip.isEmpty()) {
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, shiftTooltip, mouseX, mouseY);
        }
    }
}
