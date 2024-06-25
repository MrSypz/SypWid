package sypztep.sypwid.mixin.sortinventory;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sypztep.sypwid.client.widget.DepositWidgetButton;
import sypztep.sypwid.client.widget.WithdrawWidgetButton;
import sypztep.sypwid.client.widget.SortWidgetButton;

@Mixin(GenericContainerScreen.class)
public abstract class GenericContainerScreenMixin extends HandledScreen<GenericContainerScreenHandler>  {
    @Unique
    private SortWidgetButton sortWidgetButton;
    @Unique
    private SortWidgetButton invSortWidgetButton;
    @Unique
    private DepositWidgetButton depositWidgetButton;
    @Unique
    private WithdrawWidgetButton withdrawWidgetButton;

    public GenericContainerScreenMixin(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void init() {
        super.init();

        int x = this.x + this.backgroundWidth - 15;
        int y = this.y + 4;
        int width = 9;
        int height = 9;

        sortWidgetButton = new SortWidgetButton(x, y, width, height, Text.literal("S"), 0, getScreenHandler().slots.size() - 37, this);
        invSortWidgetButton = new SortWidgetButton(x, y + this.backgroundHeight - 100, width, height, Text.literal("S"), getScreenHandler().slots.size() - 36,getScreenHandler().slots.size() - 10, this);
        depositWidgetButton = new DepositWidgetButton(x  - 12, y + this.backgroundHeight - 100, width, height, Text.literal("Deposit"), this);
        withdrawWidgetButton = new WithdrawWidgetButton(x - 24, y + this.backgroundHeight - 100, width, height, Text.literal("Loot"), this);

        this.addDrawableChild(sortWidgetButton);
        this.addDrawableChild(invSortWidgetButton);
        this.addDrawableChild(depositWidgetButton);
        this.addDrawableChild(withdrawWidgetButton);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (sortWidgetButton != null)
            sortWidgetButton.render(context, mouseX, mouseY, delta);

        if (invSortWidgetButton != null)
            invSortWidgetButton.render(context, mouseX, mouseY, delta);

        if (depositWidgetButton != null)
            depositWidgetButton.render(context, mouseX, mouseY, delta);

        if (withdrawWidgetButton != null)
            withdrawWidgetButton.render(context, mouseX, mouseY, delta);
    }
}
