package sypztep.sypwid.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.List;

/**
 * Abstract base class for sorting algorithms.
 */
public abstract class Sort {

    /**
     * The name of the sorting algorithm.
     */
    public final String name;

    /**
     * Constructs a sorting algorithm with the specified name.
     *
     * @param name The name of the sorting algorithm.
     */
    public Sort(String name) {
        this.name = name;
    }

    /**
     * Performs the sorting algorithm on the specified list within the given range.
     *
     * @param client     The Minecraft client instance.
     * @param syncId     The synchronization ID of the screen handler.
     * @param slots      The list of slots to be sorted.
     * @param startIndex The starting index of the range to sort.
     * @param endIndex   The ending index of the range to sort.
     */
    public abstract void doSort(MinecraftClient client, int syncId, List<Slot> slots, int startIndex, int endIndex);

    /**
     * Abstract subclass representing a specific sorting algorithm.
     */
    public static abstract class Sorting extends Sort {

        /**
         * Constructs a specific sorting algorithm with the specified name.
         *
         * @param name The name of the sorting algorithm.
         */
        public Sorting(String name) {
            super(name);
        }

        /**
         * Compares two items based on the specified sorting criteria.
         *
         * @param left The first item to compare.
         * @param right The second item to compare.
         * @return A negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
         */
        protected abstract int compareItems(ItemStack left,  ItemStack right);
    }
}
