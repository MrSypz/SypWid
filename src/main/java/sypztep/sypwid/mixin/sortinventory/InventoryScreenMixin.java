package sypztep.sypwid.mixin.sortinventory;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sypztep.sypwid.client.widget.SortWidgetButton;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends HandledScreen<PlayerScreenHandler> {
    @Unique
    private SortWidgetButton inventorySortButton;

    public InventoryScreenMixin(PlayerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        int width = 12;
        int height = 12;

        inventorySortButton = new SortWidgetButton(0, 0, width, height, Text.literal("S"), 9, 35, this);

        setButtonCoordinates();

        this.addDrawableChild(inventorySortButton);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (inventorySortButton != null) {
            inventorySortButton.setX(this.x + this.backgroundWidth - 20);
            inventorySortButton.setY(this.height / 2 - 15);

            inventorySortButton.render(context, mouseX, mouseY, delta);
        }
    }

    @Unique
    private void setButtonCoordinates() {
        inventorySortButton.setX(this.x + this.backgroundWidth - 20);
        inventorySortButton.setY(this.height / 2 - 15);
    }
}
