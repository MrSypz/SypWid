package sypztep.sypwid.client.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.client.payload.ActionPayloadC2S;

import java.util.Collections;

@Environment(EnvType.CLIENT)
public final class WithdrawWidgetButton extends ActionWidgetButton {
    private static final Identifier BUTTON_TEXTURE = SypWidClient.id("hud/bar/container/loot");
    private static final Identifier BUTTON_HOVER_TEXTURE = SypWidClient.id("hud/bar/container/loot_hover");

    public WithdrawWidgetButton(int x, int y, int width, int height, Text message, HandledScreen<?> screen) {
        super(x, y, width, height, message, screen,  BUTTON_TEXTURE, BUTTON_HOVER_TEXTURE,
                Collections.singletonList(Text.literal("Withdraw items")),
                Collections.singletonList(Text.literal("")));
    }
    @Override
    public void onClick(double mouseX, double mouseY) {
        ActionPayloadC2S.send((byte) 2);
    }
}