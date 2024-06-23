package sypztep.sypwid.client.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.client.util.Sort;

public class SortWidgetButton extends ClickableWidget {

    private static final Identifier BUTTON_TEXTURE = SypWidClient.id("sort_button");
    private static final Identifier BUTTON_HOVER_TEXTURE = SypWidClient.id("sort_button_hover");

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
        DefaultedList<Slot> slots = screen.getScreenHandler().slots;
        assert client.player != null;
        int syncId = client.player.currentScreenHandler.syncId;

    }
}