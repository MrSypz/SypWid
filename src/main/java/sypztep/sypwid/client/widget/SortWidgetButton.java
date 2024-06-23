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
import sypztep.sypwid.client.SypWidConfig;
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
    public void onClick(double mouseX, double mouseY) {
        MinecraftClient client = MinecraftClient.getInstance();
        DefaultedList<Slot> slots = screen.getScreenHandler().slots;
        assert client.player != null;
        int syncId = client.player.currentScreenHandler.syncId;
        sortInventory(client, slots, syncId);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        //Voice thing Bruh
    }

    private void sortInventory(MinecraftClient client, DefaultedList<Slot> slots, int syncId) {
        switch (SypWidClient.CONFIG.sortAlgorithm) {
            case BUBBLE_SORT:
                sortUsingBubbleSort(client, slots, syncId, SypWidClient.CONFIG.sortBy);
                break;
//            case SELECTION_SORT:
//                sortUsingSelectionSort(client, slots, syncId, SypWidClient.CONFIG.sortBy);
//                break;
//            case MERGE_SORT:
//                sortUsingMergeSort(client, slots, syncId, SypWidClient.CONFIG.sortBy);
//                break;
//            case QUICK_SORT:
//                sortUsingQuickSort(client, slots, syncId, SypWidClient.CONFIG.sortBy);
//                break;
            default:
                // Default to bubble sort by name if an unknown algorithm is provided
                sortUsingBubbleSort(client, slots, syncId, SypWidConfig.SortBy.NAME);
                break;
        }
    }

    private void sortUsingBubbleSort(MinecraftClient client, DefaultedList<Slot> slots, int syncId, SypWidConfig.SortBy sortBy) {
        switch (sortBy) {
            case NAME:
                Sort.BUBBLE_SORT.by("NAME").doSort(client, syncId, slots, startIndex, endIndex);
                break;
            case ID:
                Sort.BUBBLE_SORT.by("ID").doSort(client, syncId, slots, startIndex, endIndex);
                break;
            case CATEGORY:
                Sort.BUBBLE_SORT.by("CATEGORY").doSort(client, syncId, slots, startIndex, endIndex);
                break;
            default:
                // Default to sorting by name if an unknown sortBy value is provided
                Sort.BUBBLE_SORT.by("NAME").doSort(client, syncId, slots, startIndex, endIndex);
                break;
        }
    }

//    private void sortUsingSelectionSort(MinecraftClient client, DefaultedList<Slot> slots, int syncId,  SypWidConfig.SortBy sortBy) {
//        switch (sortBy) {
//            case NAME:
//                Sort.SELECTION_SORT.by("NAME").doSort(client, slots, startIndex, endIndex);
//                break;
//            case ID:
//                Sort.SELECTION_SORT.by("ID").doSort(client, slots, startIndex, endIndex);
//                break;
//            case CATEGORY:
//                Sort.SELECTION_SORT.by("CATEGORY").doSort(client, slots, startIndex, endIndex);
//                break;
//            default:
//                // Default to sorting by name if an unknown sortBy value is provided
//                Sort.SELECTION_SORT.by("NAME").doSort(client, slots, startIndex, endIndex);
//                break;
//        }
//    }

//    private void sortUsingMergeSort(MinecraftClient client, DefaultedList<Slot> slots, int syncId, SypWidConfig.SortBy sortBy) {
//        switch (sortBy) {
//            case NAME:
//                Sort.MERGE_SORT.by("NAME").doSort(client, slots, startIndex, endIndex);
//                break;
//            case ID:
//                Sort.MERGE_SORT.by("ID").doSort(client, slots, startIndex, endIndex);
//                break;
//            case CATEGORY:
//                Sort.MERGE_SORT.by("CATEGORY").doSort(client, slots, startIndex, endIndex);
//                break;
//            default:
//                // Default to sorting by name if an unknown sortBy value is provided
//                Sort.MERGE_SORT.by("NAME").doSort(client, slots, startIndex, endIndex);
//                break;
//        }
//    }
//
//    private void sortUsingQuickSort(MinecraftClient client, DefaultedList<Slot> slots, int syncId, SypWidConfig.SortBy sortBy) {
//        switch (sortBy) {
//            case NAME:
//                Sort.QUICK_SORT.by("NAME").doSort(client, slots, startIndex, endIndex);
//                break;
//            case ID:
//                Sort.QUICK_SORT.by("ID").doSort(client, slots, startIndex, endIndex);
//                break;
//            case CATEGORY:
//                Sort.QUICK_SORT.by("CATEGORY").doSort(client, slots, startIndex, endIndex);
//                break;
//            default:
//                // Default to sorting by name if an unknown sortBy value is provided
//                Sort.QUICK_SORT.by("NAME").doSort(client, slots, startIndex, endIndex);
//                break;
//        }
}