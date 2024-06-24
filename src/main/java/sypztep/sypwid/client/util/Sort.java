package sypztep.sypwid.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import sypztep.sypwid.client.SypWidClient;

import java.util.*;

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
    public abstract void doSort(ServerPlayerEntity client, int syncId, List<Slot> slots, int startIndex, int endIndex);

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
         * Compares two ItemStacks based on specific criteria:
         * - Empty status
         * - Type (block vs. non-block)
         * - Stackability
         * - Item name (for items of the same type)
         *
         * @param left  The left ItemStack to compare.
         * @param right The right ItemStack to compare.
         * @return A negative integer, zero, or a positive integer as the left ItemStack is less than, equal to, or greater than the right ItemStack.
         */
        protected int compareItems(ItemStack left, ItemStack right) {
            // Check if both items are empty
            if (left.isEmpty() && right.isEmpty()) {
                return 0;
            }
            // Check if only the left item is empty
            if (left.isEmpty()) {
                return 1;
            }
            // Check if only the right item is empty
            if (right.isEmpty()) {
                return -1;
            }

            // Initialize comparators based on sortOrder
            List<Comparator<ItemStack>> comparators = new ArrayList<>();
            for (String sort : SypWidClient.CONFIG.sortOrder) {
                switch (sort) {
                    case "blocks":
                        comparators.add(SortType.BLOCKS);
                        break;
                    case "items":
                        comparators.add(SortType.ITEMS);
                        break;
                    case "stackables":
                        comparators.add(SortType.STACKABLES);
                        break;
                    case "unstackables":
                        comparators.add(SortType.UNSTACKABLES);
                        break;
                    case "damage":
                        comparators.add(SortType.DAMAGE);
                        break;
                    case "count":
                        comparators.add(SortType.COUNT);
                        break;
                    case "armor":
                        comparators.add(SortType.ARMOR);
                        break;
                    case "tools":
                        comparators.add(SortType.TOOLS);
                        break;
                    default:
                        String[] split = sort.split("[/:]");
                        if (split.length == 1) {
                            continue;
                        }
                        Identifier id = Identifier.of(split[1], split[2]);
                        switch (split[0]) {
                            case "item_group_order":
                                comparators.add(SortType.itemGroupOrder(id));
                                break;
                            default:
                                break;
                        }
                        break;
                }
            }
            // Compare using the initialized comparators
            for (Comparator<ItemStack> comparator : comparators) {
                int result = comparator.compare(left, right);
                if (result != 0) {
                    return result;
                }
            }
            // If all comparators return 0 (items are considered equal by all criteria)
            return 0;
        }

        /**
         * @param slots      slots in the container
         * @param startIndex Arrays
         * @param endIndex   Arrays
         */
        protected void collapseItems(List<Slot> slots, int startIndex, int endIndex) {
            // Traverse from end to start to check and merge stackable items
            for (int i = endIndex; i >= startIndex; i--) {
                ItemStack currentStack = slots.get(i).getStack();
                if (currentStack.isEmpty()) continue;

                for (int j = i - 1; j >= startIndex; j--) {
                    ItemStack compareStack = slots.get(j).getStack();
                    if (compareStack.isEmpty()) continue;

                    if (currentStack.isOf(compareStack.getItem())) {
                        int maxStackSize = currentStack.getMaxCount();
                        int currentCount = currentStack.getCount();
                        int compareCount = compareStack.getCount();
                        int combinedCount = currentCount + compareCount;

                        if (combinedCount <= maxStackSize) {
                            currentStack.setCount(combinedCount);
                            compareStack.setCount(0);
                            slots.get(j).setStack(ItemStack.EMPTY);
                        } else {
                            currentStack.setCount(maxStackSize);
                            compareStack.setCount(combinedCount - maxStackSize);
                        }

                        slots.get(i).setStack(currentStack);
                        slots.get(j).setStack(compareStack);
                    }
                }
            }

            // Move items to fill empty slots
            int emptySlot = startIndex;
            for (int i = startIndex; i <= endIndex; i++) {
                ItemStack stack = slots.get(i).getStack();
                if (!stack.isEmpty()) {
                    if (i != emptySlot) {
                        slots.get(emptySlot).setStack(stack);
                        slots.get(i).setStack(ItemStack.EMPTY);
                    }
                    emptySlot++;
                }
            }
        }

        /**
         * Plays a sound after sorting.
         *
         * @param player The player to whom the sound will be played.
         */
        protected void sortSound(ServerPlayerEntity player) {
            player.playSoundToPlayer(SoundEvents.ITEM_BRUSH_BRUSHING_SAND, SoundCategory.PLAYERS, 1.0f, 1.2f);
        }
        /**
         * Checks if all slots from startIndex to endIndex are empty.
         *
         * @param slots      The list of slots to check.
         * @param startIndex The starting index of the range to check.
         * @param endIndex   The ending index of the range to check.
         * @return true if all slots are empty, false otherwise.
         */
        protected boolean allSlotsEmpty(List<Slot> slots, int startIndex, int endIndex) {
            for (int i = startIndex; i <= endIndex; i++) {
                if (!slots.get(i).getStack().isEmpty()) {
                    return false;
                }
            }
            return true;
        }
    }

    public static MergeSort MERGESORT = new MergeSort();

    /**
     * Sorting algorithm that implements merge sort for sorting ItemStacks in Minecraft slots.
     * Items are sorted based on specific criteria: empty status, type (block vs. non-block), stackability,
     * and item name for items of the same type.
     */
    public static class MergeSort extends Sorting {

        /**
         * Constructs a merge sort algorithm with the specified name.
         */
        public MergeSort() {
            super("MergeSort");
        }

        /**
         * Performs the merge sort algorithm on the specified list of slots within the given range.
         *
         * @param player     The server player performing the sort.
         * @param syncId     The synchronization ID of the screen handler.
         * @param slots      The list of slots to be sorted.
         * @param startIndex The starting index of the range to sort.
         * @param endIndex   The ending index of the range to sort.
         */
        @Override
        public void doSort(ServerPlayerEntity player, int syncId, List<Slot> slots, int startIndex, int endIndex) {
            ItemStack[] temp = new ItemStack[endIndex - startIndex + 1];
            long start = System.nanoTime();

            // Perform sorting only if there are non-empty slots
            if (!allSlotsEmpty(slots, startIndex, endIndex)) {
                mergeSort(slots, startIndex, endIndex, temp);
            }

            long end = System.nanoTime();

            double elapsedTimeMillis = (end - start) / 1_000_000.0; // Convert nanoseconds to milliseconds with decimal

            player.sendMessageToClient(Text.literal(MERGESORT.name + " " + elapsedTimeMillis + " milliseconds"), true);

            // Clear remaining slots if sorting was performed
            if (!allSlotsEmpty(slots, startIndex, endIndex)) {
                int index = startIndex;
                for (ItemStack stack : temp) {
                    slots.get(index).setStack(stack);
                    index++;
                }

                // Clear remaining slots
                for (int i = index; i <= endIndex; i++) {
                    collapseItems(slots, startIndex, endIndex);
                    slots.get(i).setStack(ItemStack.EMPTY);
                }
            }

            sortSound(player);
        }

        /**
         * Sorts the sublist of slots using merge sort.
         *
         * @param slots      The list of slots to be sorted.
         * @param startIndex The starting index of the range to sort.
         * @param endIndex   The ending index of the range to sort.
         * @param temp       The temporary array used for merging.
         */
        private void mergeSort(List<Slot> slots, int startIndex, int endIndex, ItemStack[] temp) {
            if (startIndex < endIndex) {
                int mid = (startIndex + endIndex) / 2;
                mergeSort(slots, startIndex, mid, temp);  // Sort left half
                mergeSort(slots, mid + 1, endIndex, temp); // Sort right half
                merge(slots, startIndex, mid, endIndex, temp); // Merge the sorted halves
            }
        }

        /**
         * Merges two sorted halves of the array into a single sorted array.
         *
         * @param slots     The list of slots containing items to be merged.
         * @param leftStart The starting index of the left half.
         * @param leftEnd   The ending index of the left half.
         * @param rightEnd  The ending index of the right half.
         * @param temp      The temporary array used for merging.
         */
        private void merge(List<Slot> slots, int leftStart, int leftEnd, int rightEnd, ItemStack[] temp) {
            int leftIndex = leftStart;
            int rightIndex = leftEnd + 1;
            int index = leftStart;

            while (leftIndex <= leftEnd && rightIndex <= rightEnd) {
                if (compareItems(slots.get(leftIndex).getStack(), slots.get(rightIndex).getStack()) <= 0) {
                    temp[index - leftStart] = slots.get(leftIndex).getStack().copy();
                    leftIndex++;
                } else {
                    temp[index - leftStart] = slots.get(rightIndex).getStack().copy();
                    rightIndex++;
                }
                index++;
            }

            while (leftIndex <= leftEnd) {
                temp[index - leftStart] = slots.get(leftIndex).getStack().copy();
                leftIndex++;
                index++;
            }

            while (rightIndex <= rightEnd) {
                temp[index - leftStart] = slots.get(rightIndex).getStack().copy();
                rightIndex++;
                index++;
            }

            for (int i = leftStart; i <= rightEnd; i++) {
                slots.get(i).setStack(temp[i - leftStart]);
            }
        }
    }

    public static BubbleSort BUBBLE_SORT = new BubbleSort();

    public static class BubbleSort extends Sorting {
        /**
         * Constructs a specific sorting algorithm with the specified name.
         */
        public BubbleSort() {
            super("BubbleSort");
        }

        /**
         * @param player     The Minecraft client instance.
         * @param syncId     The synchronization ID of the screen handler.
         * @param slots      The list of slots to be sorted.
         * @param startIndex The starting index of the range to sort.
         * @param endIndex   The ending index of the range to sort.
         */
        @Override
        public void doSort(ServerPlayerEntity player, int syncId, List<Slot> slots, int startIndex, int endIndex) {
            int n = endIndex - startIndex + 1; // Number of elements to sort
            int[] indices = new int[n]; // Array to hold indices of slots

            // Initialize indices array with the indices of slots
            for (int i = 0; i < n; i++) {
                indices[i] = startIndex + i;
            }

            // Implementing optimized Bubble Sort
            long start = System.nanoTime();
            boolean swapped;
            for (int i = 0; i < n - 1; i++) {
                swapped = false;
                for (int j = 0; j < n - i - 1; j++) {
                    int slotIndex1 = indices[j];
                    int slotIndex2 = indices[j + 1];
                    if (compareItems(slots.get(slotIndex1).getStack(), slots.get(slotIndex2).getStack()) > 0) {
                        // Swap items (directly in the slots list)
                        swapItems(slots, slotIndex1, slotIndex2);

                        // Update indices array to reflect the swapped positions
                        int temp = indices[j];
                        indices[j] = indices[j + 1];
                        indices[j + 1] = temp;

                        swapped = true;
                    }
                }
                // If no elements were swapped, break out of the loop
                if (!swapped) {
                    collapseItems(slots, startIndex, endIndex);
                    break;
                }
            }


            long end = System.nanoTime();
            double elapsedTimeMillis = (end - start) / 1_000_000.0; // Convert nanoseconds to milliseconds with decimal

            player.sendMessageToClient(Text.literal(BUBBLE_SORT.name + " " + elapsedTimeMillis + " milliseconds"), true);
            sortSound(player);
        }

        /**
         * Swaps two items in the slots using client interaction.
         *
         * @param slotIndex1 The index of the first slot to swap.
         * @param slotIndex2 The index of the second slot to swap.
         */
        private void swapItems(List<Slot> slots, int slotIndex1, int slotIndex2) {
            Slot slot1 = slots.get(slotIndex1);
            Slot slot2 = slots.get(slotIndex2);
            // Swap the ItemStacks in the slots
            ItemStack temp = slot1.getStack();
            slot1.setStack(slot2.getStack());
            slot2.setStack(temp);
        }
    }
}

