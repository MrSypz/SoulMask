package sypztep.mamy.soulmask.client.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import sypztep.mamy.soulmask.client.SoulMaskModClient;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.packetC2S.MaskPacket;
import sypztep.mamy.soulmask.common.packetC2S.UnMaskPacket;
import sypztep.mamy.soulmask.common.util.SoulMaskUtil;

public class MaskHandleTick {
    private static final int DEFAULT_COOLDOWN = 20;
    private static int equipmaskcd = DEFAULT_COOLDOWN,lastequipcd = DEFAULT_COOLDOWN, unmaskcd = DEFAULT_COOLDOWN,lastunmaskcd = DEFAULT_COOLDOWN;

    public static void init () {
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (minecraft.player != null) {
                if (!SoulMaskUtil.hasEquippedMask(minecraft.player) && SoulMaskModClient.EQUIPMASK_KEYBINDING.isPressed()) {
                    if (VizardComponent.canUseMask(minecraft.player)) {
                        equipmaskcd--;
                        if (equipmaskcd <= 0) {
                            MaskPacket.send();
                            equipmaskcd = DEFAULT_COOLDOWN;
                        }
                    }
                }
                else if (SoulMaskUtil.hasEquippedMask(minecraft.player) && SoulMaskModClient.EQUIPMASK_KEYBINDING.isPressed()) {
                    unmaskcd--;
                    if (unmaskcd <= 0) {
                        UnMaskPacket.send();
                        unmaskcd = DEFAULT_COOLDOWN;
                    }
                }
                else {
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
        return lastequipcd;
    }

    public static int getLastunmaskcd() {
        return lastunmaskcd;
    }
}
