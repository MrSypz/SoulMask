package sypztep.mamy.soulmask.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.init.ModEntityComponents;
import sypztep.mamy.soulmask.common.util.SoulMaskUtil;

public class MaskRenderEvent implements HudRenderCallback {
    private static final Identifier EQUIPMASKCD_TEXTURE = SoulMaskMod.id("textures/gui/mask.png");
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        ModEntityComponents.VIZARD.maybeGet(MinecraftClient.getInstance().cameraEntity).ifPresent(vizardComponent -> {
            if (vizardComponent.canUseMask2(MinecraftClient.getInstance().player)){
                if (MaskHandleTick.getEquipmaskcd() < 20) {
                    RenderSystem.enableBlend();
                    drawContext.drawTexture(EQUIPMASKCD_TEXTURE, (int) (drawContext.getScaledWindowWidth() / 2F) - 4, (int) (drawContext.getScaledWindowHeight() / 2F) + 18, 0, 8, 7, 8, 7, 16);
                    if (MaskHandleTick.getEquipmaskcd() < MaskHandleTick.getLastequipcd())
                        drawContext.drawTexture(EQUIPMASKCD_TEXTURE, (int) (drawContext.getScaledWindowWidth() / 2F) - 4, (int) (drawContext.getScaledWindowHeight() / 2F) + 18, 0, 0, (int) (7 - (MaskHandleTick.getEquipmaskcd() / (float) MaskHandleTick.getLastequipcd()) * 10), 8, 7, 16);
                    RenderSystem.disableBlend();
                } else
                if (SoulMaskUtil.hasEquippedMask(MinecraftClient.getInstance().player) && MaskHandleTick.getUnmaskcd() < 20) {
                    RenderSystem.enableBlend();
                    drawContext.drawTexture(EQUIPMASKCD_TEXTURE, (int) (drawContext.getScaledWindowWidth() / 2F) - 4, (int) (drawContext.getScaledWindowHeight() / 2F) + 18, 0, 8, 7, 8, 7, 16);
                    if (MaskHandleTick.getUnmaskcd() < MaskHandleTick.getLastunmaskcd())
                        drawContext.drawTexture(EQUIPMASKCD_TEXTURE, (int) (drawContext.getScaledWindowWidth() / 2F) - 4, (int) (drawContext.getScaledWindowHeight() / 2F) + 18, 0, 0, (int) (7 - (MaskHandleTick.getUnmaskcd() / (float) MaskHandleTick.getLastunmaskcd()) * 10), 8, 7, 16);
                    RenderSystem.disableBlend();
                }
            }
        });
    }
}
