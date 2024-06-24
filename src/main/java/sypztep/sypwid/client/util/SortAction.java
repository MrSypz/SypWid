package sypztep.sypwid.client.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class SortAction {
    public static void deposit(ScreenHandler screenHandler) {
        int size = screenHandler.getSlot(0).inventory.size();
        for (int i = size; i < size + 27; ++i) {
            screenHandler.quickMove(null, i);
        }
    }
    public static void depositAll(ScreenHandler screenHandler) {
        int size = screenHandler.getSlot(0).inventory.size();
        for (int i = size; i < size + 36; ++i) {
            screenHandler.quickMove(null, i);
        }
    }

    public static void lootAll(ScreenHandler screenHandler) {
        int size = screenHandler.getSlot(0).inventory.size();
        for (int i = 0; i < size; ++i) {
            screenHandler.quickMove(null, i);
        }
    }

    public static void quickStack(ScreenHandler screenHandler) {
        Inventory container = screenHandler.getSlot(0).inventory;
        List<Slot> slots = screenHandler.slots;
        int size = slots.size();
        for (int i = size - 36; i < size - 9; ++i) {

            ItemStack playerInvStack = slots.get(i).getStack();
            if (container.containsAny(stack -> stack.getItem() == playerInvStack.getItem())) {
                screenHandler.quickMove(null, i);
            }
        }
    }

    public static void restock(ServerPlayerEntity player) {
        Inventory container = player.currentScreenHandler.getSlot(0).inventory;
        PlayerInventory inventory = player.getInventory();

        for (int i = 0; i < 36; ++i) {
            ItemStack stackOne = inventory.getStack(i);
            int needed = stackOne.getMaxCount() - stackOne.getCount();
            if (needed > 0) {
                for (int j = 0; j < container.size(); ++j) {
                    ItemStack stackTwo = container.getStack(j);
                    if (stackOne.getItem() == stackTwo.getItem()) {
                        int count = stackTwo.getCount();

                        if (needed <= count) {
                            inventory.insertStack(container.removeStack(j, needed));
                            needed = 0;
                        } else {
                            inventory.insertStack(container.removeStack(j, count));
                            needed -= count;
                        }
                    }
                }
            }
        }
    }
}
