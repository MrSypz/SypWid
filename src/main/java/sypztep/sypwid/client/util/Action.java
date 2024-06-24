package sypztep.sypwid.client.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class Action {
    abstract static class ActionHandler {
        protected abstract void doAction(ScreenHandler screenHandler);
//        public abstract void doAction(ServerPlayerEntity player);
    }
    public static DepositAction DEPOSITE = new DepositAction();
    public static class DepositAction extends ActionHandler {

        @Override
        public void doAction(ScreenHandler screenHandler) {
            int size = screenHandler.getSlot(0).inventory.size();
            for (int i = size; i < size + 27; ++i) {
                screenHandler.quickMove(null, i);
            }
        }
    }
    public static DepositAllAction DEPOSITEALL = new DepositAllAction();
    public static class DepositAllAction extends ActionHandler {

        @Override
        public void doAction(ScreenHandler screenHandler) {
            int size = screenHandler.getSlot(0).inventory.size();
            for (int i = size; i < size + 36; ++i) {
                screenHandler.quickMove(null, i);
            }
        }
    }
    public static WithdrawAction WITHDRAW = new WithdrawAction();
    public static class WithdrawAction extends ActionHandler {

        @Override
        public void doAction(ScreenHandler screenHandler) {
            int size = screenHandler.getSlot(0).inventory.size();
            for (int i = 0; i < size; ++i) {
                screenHandler.quickMove(null, i);
            }
        }
    }
    public static QuickDepositAction QUICKDEPOSIT = new QuickDepositAction();
    public static class QuickDepositAction extends ActionHandler {
        @Override
        protected void doAction(ScreenHandler screenHandler) {
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
    }
    public static RestockAction RESTOCK = new RestockAction();
    public static class RestockAction implements PlayerActionHandler {
        @Override
        public void doAction(ServerPlayerEntity player) {
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
    interface PlayerActionHandler {
        void doAction(ServerPlayerEntity player);
    }
}
