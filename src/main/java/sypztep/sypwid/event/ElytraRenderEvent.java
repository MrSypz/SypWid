package sypztep.sypwid.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import sypztep.sypwid.client.SypWidClient;
@Environment(EnvType.CLIENT)
public class ElytraRenderEvent implements HudRenderCallback {
    private static final Identifier ELYTRA_TEXTURE = SypWidClient.id("hud/bar/icon/elytra");
    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;

        int x = context.getScaledWindowWidth() / 10;
        int y = context.getScaledWindowHeight() - 49;
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        int elytraperc = (int) (100.0F * Math.max((float) chest.getDamage(), 0.0F) / (float) chest.getMaxDamage());
        if (chest.isOf(Items.ELYTRA)) {
            RenderSystem.enableBlend();
            context.drawGuiTexture(ELYTRA_TEXTURE, x - 14, y - 1, 10, 9);
            context.drawText(MinecraftClient.getInstance().textRenderer, "% " + elytraperc, x, y, 0xFFFFFF, true);
            RenderSystem.disableBlend();
        }
    }
}
