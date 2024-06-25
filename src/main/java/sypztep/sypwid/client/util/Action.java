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
    interface PlayerActionHandler {
        void doAction(ServerPlayerEntity player);
    }
}
