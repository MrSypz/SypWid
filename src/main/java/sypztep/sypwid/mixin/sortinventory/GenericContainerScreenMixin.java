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
import sypztep.sypwid.client.widget.SortWidgetButton;

@Mixin(GenericContainerScreen.class)
public abstract class GenericContainerScreenMixin extends HandledScreen<GenericContainerScreenHandler>  {
    @Unique
    private SortWidgetButton sortWidgetButton;

    public GenericContainerScreenMixin(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void init() {
        super.init();

        int x = this.x + this.backgroundWidth - 20;
        int y = this.y + 4;
        int width = 12;
        int height = 12;
        sortWidgetButton = new SortWidgetButton(x, y, width, height, Text.literal("S"), 0, getScreenHandler().slots.size() - 37, this);

        this.addDrawableChild(sortWidgetButton);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (sortWidgetButton != null) {
            sortWidgetButton.render(context, mouseX, mouseY, delta);
        }
    }
}
