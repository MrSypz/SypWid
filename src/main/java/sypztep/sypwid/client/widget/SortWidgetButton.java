package sypztep.sypwid.client.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.client.payload.SortPayloadC2S;
@Environment(EnvType.CLIENT)
public class SortWidgetButton extends ClickableWidget {
    private static final Identifier BUTTON_TEXTURE = SypWidClient.id("hud/bar/container/sort");
    private static final Identifier BUTTON_HOVER_TEXTURE = SypWidClient.id("hud/bar/container/sort_hover");

    private int startIndex, endIndex;
    private HandledScreen<?> screen;

    public SortWidgetButton(int x, int y, int width, int height, Text message, int startIndex, int endIndex, HandledScreen<?> screen) {
        super(x, y, width, height, message);

        this.startIndex = startIndex;
        this.endIndex = endIndex;
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
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        int syncId = client.player.currentScreenHandler.syncId;
        byte algorithm = SypWidClient.CONFIG.sortAlgorithm.getByteValue();

        SortPayloadC2S.send(syncId,startIndex,endIndex,algorithm);
    }
}