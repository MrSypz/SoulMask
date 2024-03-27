package sypztep.mamy.soulmask.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.init.ModEntityComponents;
import sypztep.mamy.soulmask.common.utils.SoulMaskUtil;

public class MaskRenderEvent implements HudRenderCallback {
    private static final Identifier EQUIPMASKCD_TEXTURE = SoulMaskMod.id("textures/gui/mask.png");
    private static final Identifier MASKCD_TEXTURE = SoulMaskMod.id("textures/gui/cd.png");
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        ModEntityComponents.VIZARD.maybeGet(minecraft.cameraEntity).ifPresent(vizardComponent -> {
            int windowWidth = drawContext.getScaledWindowWidth();
            int windowHeight = drawContext.getScaledWindowHeight();
            if (VizardComponent.canUseMask(minecraft.player)) {

                if (MaskHandleTick.getEquipmaskcd() < 20)
                    renderCooldownBar(drawContext, windowWidth / 2 - 16, windowHeight / 2 + 18, MaskHandleTick.getEquipmaskcd(), MaskHandleTick.getLastequipcd());
                 else if (SoulMaskUtil.hasEquippedMask(minecraft.player) && MaskHandleTick.getUnmaskCooldown() < 20)
                    renderCooldownBar(drawContext, windowWidth / 2 - 16, windowHeight / 2 + 18, MaskHandleTick.getUnmaskCooldown(), MaskHandleTick.getLastunmaskcd());

            }

            if (vizardComponent.isWasUnEquipMask()) {
                RenderSystem.enableBlend();
                drawContext.drawTexture(MASKCD_TEXTURE, windowWidth / 9 - 25, windowHeight / 2 + 80, 0, 0, 18, 18, 18, 36);
                if (vizardComponent.getDelayUsemask() < vizardComponent.getLastDelayUsemask())
                    drawContext.drawTexture(MASKCD_TEXTURE, windowWidth / 9 - 25, windowHeight / 2 + 80, 0, 18, 18, (int) (19 - (vizardComponent.getDelayUsemask() / (float) vizardComponent.getLastDelayUsemask()) * 18), 18, 36);
                RenderSystem.disableBlend();
            }
        });
    }

    private void renderCooldownBar(DrawContext drawContext, int x, int y, int currentCooldown, int lastCooldown) {
        RenderSystem.enableBlend();
        drawContext.drawTexture(MaskRenderEvent.EQUIPMASKCD_TEXTURE, x, y, 0, 5, 32, 5, 32, 10);
        if (currentCooldown < lastCooldown)
            drawContext.drawTexture(MaskRenderEvent.EQUIPMASKCD_TEXTURE, x, y, 0, 0, (int) (33 - (currentCooldown / (float) lastCooldown) * 32), 5, 32, 10);
        RenderSystem.disableBlend();
    }

    public static void drawtextcustom(DrawContext context, TextRenderer textRenderer,String text,int x,int y ,int color,int board, boolean shadow){
        context.drawText(textRenderer,text,x + 1,y,board,shadow);
        context.drawText(textRenderer,text,x - 1,y,board,shadow);
        context.drawText(textRenderer,text,x ,y + 1,board,shadow);
        context.drawText(textRenderer,text,x ,y - 1,board,shadow);
        context.drawText(textRenderer,text,x,y,color,shadow);
    }
}
