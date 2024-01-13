package sypztep.mamy.soulmask.client.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import sypztep.mamy.soulmask.client.SoulMaskModClient;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.packetC2S.MaskPacket;
import sypztep.mamy.soulmask.common.utils.SoulMaskUtil;

public class MaskHandleTick {
    private static final int DEFAULT_COOLDOWN = 20;
    private static int equipmaskcd = DEFAULT_COOLDOWN, unmaskcd = DEFAULT_COOLDOWN,presscd = 10;
    private static boolean wasUnEquipMask = false, hasEquippedMask = false;

    public static void init () {
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (minecraft.player != null) {
                hasEquippedMask = SoulMaskUtil.hasEquippedMask(minecraft.player);
                if (!hasEquippedMask && SoulMaskModClient.EQUIPMASK_KEYBINDING.isPressed() && VizardComponent.canUseMask(minecraft.player) && !VizardComponent.WasEquipMask(minecraft.player) && presscd <= 0) {

                    if (minecraft.player.age % 5 == 0) {
                        SoulMaskUtil.addChargeParticle(minecraft.player);
                        MaskPacket.send(3);
                    }
                    equipmaskcd--;

                    if (equipmaskcd <= 0) {
                        presscd = 10;
                        MaskPacket.send(1);
                        SoulMaskUtil.addUseMaskParticle(minecraft.player);
                        equipmaskcd = DEFAULT_COOLDOWN;
                    }
                } else if (hasEquippedMask && SoulMaskModClient.EQUIPMASK_KEYBINDING.isPressed() && presscd <= 0) {

                    if (minecraft.player.age % 5 == 0) {
                        SoulMaskUtil.addChargeParticle(minecraft.player);
                        MaskPacket.send(3);
                    }
                    unmaskcd--;
                    if (unmaskcd <= 0) {
                        presscd = 10;
                        MaskPacket.send(2);
                        wasUnEquipMask = true;
                        SoulMaskUtil.addUseMaskParticle(minecraft.player);
                        unmaskcd = DEFAULT_COOLDOWN;
                    }
                } else {
                    unmaskcd = DEFAULT_COOLDOWN;
                    equipmaskcd = DEFAULT_COOLDOWN;
                }
                if (presscd > 0)
                    presscd--;
            }
        });
    }
    /**
     * Render UI part
     */
    public static int getEquipmaskcd() {
        return equipmaskcd;
    }
    public static int getUnmaskcd() {
        return unmaskcd;
    }
    public static int getLastequipcd() {
        return DEFAULT_COOLDOWN;
    }
    public static int getLastunmaskcd() {
        return DEFAULT_COOLDOWN;
    }

    /**
     * End Render UI part
     */

    public static boolean WasUnEquipMask() {
        return wasUnEquipMask;
    }

    public static boolean isHasEquippedMask() {
        return hasEquippedMask;
    }

    public static void setWasUnEquipMask(boolean wasUnEquipMask) {
        MaskHandleTick.wasUnEquipMask = wasUnEquipMask;
    }
}
