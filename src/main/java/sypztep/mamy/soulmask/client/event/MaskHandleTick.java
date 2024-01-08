package sypztep.mamy.soulmask.client.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import sypztep.mamy.soulmask.client.SoulMaskModClient;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.packetC2S.MaskPacket;
import sypztep.mamy.soulmask.common.util.SoulMaskUtil;

public class MaskHandleTick {
    private static final int DEFAULT_COOLDOWN = 20;
    private static int equipmaskcd = DEFAULT_COOLDOWN, unmaskcd = DEFAULT_COOLDOWN;
    private static boolean wasUnEquipMask = false;

    public static void init () {
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (minecraft.player != null) {
                if (!SoulMaskUtil.hasEquippedMask(minecraft.player) && SoulMaskModClient.EQUIPMASK_KEYBINDING.isPressed() && VizardComponent.canUseMask(minecraft.player) && !VizardComponent.WasEquipMask(minecraft.player)) {
                    equipmaskcd--;
                    if (equipmaskcd <= 0) {
                        MaskPacket.send(1);
                        equipmaskcd = DEFAULT_COOLDOWN;
                    }
                } else if (SoulMaskUtil.hasEquippedMask(minecraft.player) && SoulMaskModClient.EQUIPMASK_KEYBINDING.isPressed()) {
                    unmaskcd--;
                    if (unmaskcd <= 0) {
                        MaskPacket.send(2);
                        wasUnEquipMask = true;
                        unmaskcd = DEFAULT_COOLDOWN;
                    }
                } else {
                    unmaskcd = DEFAULT_COOLDOWN;
                    equipmaskcd = DEFAULT_COOLDOWN;
                }
            }
        });
    }

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

    public static boolean isWasUnEquipMask() {
        return wasUnEquipMask;
    }

    public static void setWasUnEquipMask(boolean wasUnEquipMask) {
        MaskHandleTick.wasUnEquipMask = wasUnEquipMask;
    }
}
