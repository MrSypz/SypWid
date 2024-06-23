package sypztep.sypwid.event;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import sypztep.sypwid.client.util.SpeedConvert;
import sypztep.sypwid.client.SypWidClient;
import sypztep.sypwid.client.SypWidConfig;

public class SpeedOmeterRenderEvent implements HudRenderCallback {

    private int color = SypWidClient.CONFIG.textColor;
    private int vertColor = SypWidClient.CONFIG.textColor;
    private double lastFrameSpeed = 0.0;
    private double lastFrameVertSpeed = 0.0;
//    private float tickCounter = 0.0f;

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter counter){
        if (!SypWidClient.CONFIG.enableSpeedOmeter)
            return;
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        // Calculating Speed
        Vec3d playerPosVec = client.player.getPos();
        double travelledX = playerPosVec.x - client.player.prevX;
        double travelledZ = playerPosVec.z - client.player.prevZ;
        double currentSpeed = MathHelper.sqrt((float)(travelledX * travelledX + travelledZ * travelledZ));
        double currentVertSpeed = playerPosVec.y - client.player.prevY;

        if (SypWidClient.CONFIG.changeColors) {
            // Every tick determine if speeds are increasing or decreasing and set color accordingly
//            tickCounter += counter.getTickDelta(true);
            if (counter.getLastDuration() >= (float)SypWidClient.CONFIG.tickInterval) {
                if (currentSpeed < lastFrameSpeed) {
                    color = SypWidClient.CONFIG.deceleratingColor;
                } else if (currentSpeed > lastFrameSpeed) {
                    color = SypWidClient.CONFIG.acceleratingColor;
                } else {
                    color = SypWidClient.CONFIG.textColor;
                }

                if (currentVertSpeed < lastFrameVertSpeed) {
                    vertColor = SypWidClient.CONFIG.deceleratingColor;
                } else if (currentVertSpeed > lastFrameVertSpeed) {
                    vertColor = SypWidClient.CONFIG.acceleratingColor;
                } else {
                    vertColor = SypWidClient.CONFIG.textColor;
                }

                lastFrameSpeed = currentSpeed;
                lastFrameVertSpeed = currentVertSpeed;
            }
        }

        String currentVertSpeedText = "";
        String currentSpeedText = "";
        // Convert speeds to text
        if (SypWidClient.CONFIG.showVertical) {
            currentVertSpeedText = String.format("Vertical: %s", SpeedConvert.speedText(currentVertSpeed, SypWidClient.CONFIG.speedUnit));
            currentSpeedText = String.format("Horizontal: %s", SpeedConvert.speedText(currentSpeed, SypWidClient.CONFIG.speedUnit));
        } else {
            currentSpeedText = SpeedConvert.speedText(currentSpeed, SypWidClient.CONFIG.speedUnit);
        }
        // Calculate text position
        int horizWidth = textRenderer.getWidth(currentSpeedText);
        int vertWidth = textRenderer.getWidth(currentVertSpeedText);
        int height = textRenderer.fontHeight;
        int paddingX = 2;
        int paddingY = 2;
        int marginX = 4;
        int marginY = 4;
        int left = marginX;
        int vertLeft = marginX;
        int top = marginY;
        int realHorizWidth = horizWidth + paddingX * 2 - 1;
        int realVertWidth = vertWidth + paddingX * 2 - 1;
        int realHeight = height + paddingY * 2 - 1;

        if (SypWidClient.CONFIG.position == SypWidConfig.Position.BOTTOM_LEFT) {
            top += client.getWindow().getScaledHeight() - marginY * 2 - realHeight;

            left += paddingX;
            vertLeft += paddingX;
            top += paddingY;
        }

        if (SypWidClient.CONFIG.position == SypWidConfig.Position.BOTTOM_RIGHT) {
            top += client.getWindow().getScaledHeight() - marginY * 2 - realHeight;
            left += client.getWindow().getScaledWidth() - marginX * 2 - realHorizWidth;
            vertLeft += client.getWindow().getScaledWidth() - marginX * 2 - realVertWidth;

            left += paddingX;
            vertLeft += paddingX;
            top += paddingY;
        }

        if (SypWidClient.CONFIG.position == SypWidConfig.Position.TOP_LEFT) {
            left += paddingX;
            vertLeft += paddingX;
            top += paddingY;

            if (SypWidClient.CONFIG.showVertical) {
                top += 10;
            }
        }

        if (SypWidClient.CONFIG.position == SypWidConfig.Position.TOP_RIGHT) {
            left += client.getWindow().getScaledWidth() - marginX * 2 - realHorizWidth;
            vertLeft += client.getWindow().getScaledWidth() - marginX * 2 - realVertWidth;

            left += paddingX;
            vertLeft += paddingX;
            top += paddingY;

            if (SypWidClient.CONFIG.showVertical) {
                top += 10;
            }
        }
        // Render the text
        context.drawTextWithShadow(textRenderer, currentVertSpeedText, vertLeft, top - 10, vertColor);
        context.drawTextWithShadow(textRenderer, currentSpeedText, left, top, color);
    }
}
