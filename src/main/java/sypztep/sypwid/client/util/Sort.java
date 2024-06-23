package sypztep.sypwid.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;

import java.util.HashMap;

/**
 * Abstract base class for sorting algorithms.
 */
public abstract class Sort {
    /**
     * Pre define a Sorting Style
     */
    public static final String NAME = "NAME";
    public static final String ID = "ID";
    public static final String CATEGORY = "CATEGORY";
    /**
     * HashMap to store different sorting algorithms by their name.
     */
    public static final HashMap<String, Sort> SORTINGS = new HashMap<>();

    /**
     * The name of the sorting algorithm.
     */
    public final String name;

    /**
     * Constructs a sorting algorithm with the specified name and registers it in SORTINGS.
     *
     * @param name The name of the sorting algorithm.
     */
    public Sort(String name) {
        this.name = name;
        SORTINGS.put(name, this);
    }

    /**
     * Performs the sorting algorithm on the specified slots within the given range.
     *
     * @param client The Minecraft client instance.
     * @param slots The list of slots to be sorted.
     * @param startIndex The starting index of the range to sort.
     * @param endIndex The ending index of the range to sort.
     */
    public abstract void doSort(MinecraftClient client, int syncId, DefaultedList<Slot> slots, int startIndex, int endIndex);

    /**
     * Allows specifying a sorting criteria dynamically.
     *
     * @param criteria The criteria for sorting (e.g., "NAME", "VALUE", etc.).
     * @return The Sorting instance configured with the specified criteria.
     */
    public Sorting by(String criteria) {
        return null; // Override in subclass
    }

    /**
     * Abstract subclass representing a specific sorting algorithm.
     */
    public static abstract class Sorting extends Sort {

        /**
         * Constructs a specific sorting algorithm with the specified name.
         *
         * @param name The name of the sorting algorithm.
         *
         */
        public Sorting(String name) {
            super(name);
        }

        /**
         * Compares two items based on the specified sorting criteria.
         *
         * @param item1 The first item to compare.
         * @param item2 The second item to compare.
         * @return A negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
         */
        protected abstract int compareItems(ItemStack item1, ItemStack item2);
        protected void collapseItems(MinecraftClient client, int syncId, DefaultedList<Slot> slots, int startIndex, int endIndex) {
            for (int i = endIndex; i > startIndex; i--) {
                ItemStack right = slots.get(i).getStack();
                ItemStack left = slots.get(i - 1).getStack();
                if (right.isOf(left.getItem())) { // Same Type of item
                    client.interactionManager.clickSlot(syncId, i, 0, SlotActionType.PICKUP, client.player);
                    client.interactionManager.clickSlot(syncId, i - 1, 0, SlotActionType.PICKUP, client.player);
                    client.interactionManager.clickSlot(syncId, i, 0, SlotActionType.PICKUP, client.player);
                }
            }
        }
    }
    public static final Sorting BUBBLE_SORT = new BubbleSort();
    /**
     * Concrete implementation of Bubble Sort algorithm.
     */
    public static class BubbleSort extends Sorting {

        /**
         * Constructs a Bubble Sort algorithm with the specified name.
         *
         */
        public BubbleSort() {
            super("Bubble_Sort");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void doSort(MinecraftClient client, int syncId, DefaultedList<Slot> slots, int startIndex, int endIndex) {
            for (int i = startIndex; i <= endIndex; i++) {
                for (int j = startIndex; j <= endIndex - 1; j++) {
                    if (compareItems(slots.get(j).getStack(), slots.get(j + 1).getStack()) > 0) { // Swap items
                        swapItems(client, syncId, j, j + 1);
                    }
                }
            }
            collapseItems(client, syncId, slots, startIndex, endIndex);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected int compareItems(ItemStack left, ItemStack right) {
            if (left.isEmpty() && !right.isEmpty()) {
                return 1;
            } else if (right.isEmpty() && !left.isEmpty()) {
                return -1;
            }
            return left.getName().getString().compareTo(right.getName().getString());
        }

        /**
         * Swaps two items in the slots using client interaction.
         *
         * @param client The Minecraft client instance.
         * @param syncId The synchronization ID of the screen handler.
         * @param slotIndex1 The index of the first slot to swap.
         * @param slotIndex2 The index of the second slot to swap.
         */
        private void swapItems(MinecraftClient client, int syncId, int slotIndex1, int slotIndex2) {
            client.interactionManager.clickSlot(syncId, slotIndex1, 0, SlotActionType.PICKUP, client.player);
            client.interactionManager.clickSlot(syncId, slotIndex2, 0, SlotActionType.PICKUP, client.player);
            client.interactionManager.clickSlot(syncId, slotIndex1, 0, SlotActionType.PICKUP, client.player);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Sorting by(String criteria) {
            switch (criteria.toUpperCase()) {
                case "NAME":
                    return new BubbleSortByName();
                case "ID":
                    return new BubbleSortById();
                case "CATEGORY":
                    return new BubbleSortByCategory();
                // Add more cases for additional criteria
                default:
                    return this; // Default to sorting by name
            }
        }

        /**
         * Sorting by item name.
         */
        private static class BubbleSortByName extends BubbleSort {
            public BubbleSortByName() {
                super();
            }
        }

        /**
         * Sorting by item ID.
         */
        private static class BubbleSortById extends BubbleSort {
            public BubbleSortById() {
                super();
            }

            @Override
            protected int compareItems(ItemStack left, ItemStack right) {
                // Replace with actual comparison logic by ID
                // Example: return Integer.compare(item1.getId(), item2.getId());

                return 0; // Placeholder
            }
        }

        /**
         * Sorting by item category.
         */
        private static class BubbleSortByCategory extends BubbleSort {
            public BubbleSortByCategory() {
                super();
            }

            @Override
            protected int compareItems(ItemStack left, ItemStack right) {
                // Replace with actual comparison logic by category
                // Example: return item1.getCategory().compareTo(item2.getCategory());
                return 0; // Placeholder
            }
        }
    }
}