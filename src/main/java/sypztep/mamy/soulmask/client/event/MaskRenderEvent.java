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
import sypztep.mamy.soulmask.common.util.SoulMaskUtil;

public class MaskRenderEvent implements HudRenderCallback {
    private static final Identifier EQUIPMASKCD_TEXTURE = SoulMaskMod.id("textures/gui/mask.png");
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        ModEntityComponents.VIZARD.maybeGet(MinecraftClient.getInstance().cameraEntity).ifPresent(vizardComponent -> {
            if (VizardComponent.canUseMask(MinecraftClient.getInstance().player)){
                if (MaskHandleTick.getEquipmaskcd() < 20) {
                    RenderSystem.enableBlend();
                    drawContext.drawTexture(EQUIPMASKCD_TEXTURE, (int) (drawContext.getScaledWindowWidth() / 2F) - 16, (int) (drawContext.getScaledWindowHeight() / 2F) + 18, 0, 5, 32, 5, 32, 10);
                    if (MaskHandleTick.getEquipmaskcd() < MaskHandleTick.getLastequipcd())
                        drawContext.drawTexture(EQUIPMASKCD_TEXTURE, (int) (drawContext.getScaledWindowWidth() / 2F) - 16, (int) (drawContext.getScaledWindowHeight() / 2F) + 18, 0, 0, (int) (33-(MaskHandleTick.getEquipmaskcd() / (float) MaskHandleTick.getLastequipcd()) * 32), 5, 32, 10);
                    RenderSystem.disableBlend();
                } else
                if (SoulMaskUtil.hasEquippedMask(MinecraftClient.getInstance().player) && MaskHandleTick.getUnmaskcd() < 20) {
                    RenderSystem.enableBlend();
                    drawContext.drawTexture(EQUIPMASKCD_TEXTURE, (int) (drawContext.getScaledWindowWidth() / 2F) - 16, (int) (drawContext.getScaledWindowHeight() / 2F) + 18, 0, 5, 32, 5, 32, 10);
                    if (MaskHandleTick.getUnmaskcd() < MaskHandleTick.getLastunmaskcd())
                        drawContext.drawTexture(EQUIPMASKCD_TEXTURE, (int) (drawContext.getScaledWindowWidth() / 2F) - 16, (int) (drawContext.getScaledWindowHeight() / 2F) + 18, 0, 0, (int) (33-(MaskHandleTick.getUnmaskcd() / (float) MaskHandleTick.getLastunmaskcd()) * 32), 5, 32, 10);
                    RenderSystem.disableBlend();
                }
            }
            if (vizardComponent.isWasEquipMask()) {
                RenderSystem.enableBlend();
                drawtextcustom(drawContext,MinecraftClient.getInstance().textRenderer, String.valueOf(vizardComponent.getDelayUsemask()),drawContext.getScaledWindowWidth() / 2,drawContext.getScaledWindowHeight() / 3,16777215,0,false);
                drawtextcustom(drawContext,MinecraftClient.getInstance().textRenderer, String.valueOf(vizardComponent.isWasEquipMask()),drawContext.getScaledWindowWidth() / 2, 10 + drawContext.getScaledWindowHeight() / 3,16777215,0,false);
                RenderSystem.disableBlend();
            }
        });
    }
    public static void drawtextcustom(DrawContext context, TextRenderer textRenderer,String text,int x,int y ,int color,int board, boolean shadow){
        context.drawText(textRenderer,text,x + 1,y,board,false);
        context.drawText(textRenderer,text,x - 1,y,board,false);
        context.drawText(textRenderer,text,x ,y + 1,board,false);
        context.drawText(textRenderer,text,x ,y - 1,board,false);
        context.drawText(textRenderer,text,x,y,color,false);
    }
}
