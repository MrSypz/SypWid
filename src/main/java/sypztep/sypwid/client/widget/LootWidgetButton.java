package sypztep.sypwid.client.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.client.payload.ActionPayloadC2S;

@Environment(EnvType.CLIENT)
public class LootWidgetButton extends ClickableWidget {
    private static final Identifier BUTTON_TEXTURE = SypWidClient.id("hud/bar/container/loot");
    private static final Identifier BUTTON_HOVER_TEXTURE = SypWidClient.id("hud/bar/container/loot_hover");
    private HandledScreen<?> screen;

    public LootWidgetButton(int x, int y, int width, int height, Text message, HandledScreen<?> screen) {
        super(x, y, width, height, message);
        this.screen = screen;
    }
    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(BUTTON_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight());

        if (this.isHovered())
            context.drawGuiTexture(BUTTON_HOVER_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        //Voice thing Bruh
    }
    @Override
    public void onClick(double mouseX, double mouseY) {
        ActionPayloadC2S.send((byte) 2);
    }
}