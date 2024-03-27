package sypztep.mamy.soulmask.client.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import sypztep.mamy.soulmask.client.SoulMaskModClient;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.packetC2S.MaskPacket;
import sypztep.mamy.soulmask.common.utils.SoulMaskUtil;

public class MaskHandleTick {
    private static final int DEFAULT_COOLDOWN = 20;
    private static int equipmaskcd = DEFAULT_COOLDOWN, unmaskCooldown = DEFAULT_COOLDOWN, pressCooldown = 10;
    private static boolean wasUnEquipMask = false, hasEquippedMask = false;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (minecraft.player == null)
                return;
            hasEquippedMask = SoulMaskUtil.hasEquippedMask(minecraft.player);

            if (!hasEquippedMask && SoulMaskModClient.EQUIPMASK_KEYBINDING.isPressed() && VizardComponent.canUseMask(minecraft.player) && !wasUnEquipMask && pressCooldown <= 0) {
                handleMaskEquipping(minecraft);
            } else if (hasEquippedMask && SoulMaskModClient.EQUIPMASK_KEYBINDING.isPressed() && pressCooldown <= 0) {
                handleMaskUnequipping(minecraft);
            } else {
                resetCooldowns();
            }
            if (pressCooldown > 0)
                pressCooldown--;
        });
    }

    private static void handleMaskEquipping(MinecraftClient minecraft) {
        if (minecraft.player == null)
            return;
        if (minecraft.player.age % 5 == 0) {
            SoulMaskUtil.addChargeParticle(minecraft.player);
            MaskPacket.send(3);
        }
        equipmaskcd--;

        if (equipmaskcd <= 0) {
            pressCooldown = 10;
            MaskPacket.send(1);
            SoulMaskUtil.addUseMaskParticle(minecraft.player);
            equipmaskcd = DEFAULT_COOLDOWN;
        }
    }

    private static void handleMaskUnequipping(MinecraftClient minecraft) {
        if (minecraft.player == null)
            return;
        if (minecraft.player.age % 5 == 0) {
            SoulMaskUtil.addChargeParticle(minecraft.player);
            MaskPacket.send(3);
        }
        unmaskCooldown--;
        if (unmaskCooldown <= 0) {
            pressCooldown = 10;
            handleMaskUnequippingActions(minecraft.player,0);
            unmaskCooldown = DEFAULT_COOLDOWN;
        }
    }

    public static void handleMaskUnequippingActions(PlayerEntity player,int select) {
        switch (select) {
            case 0 :
                MaskPacket.send(2);
                wasUnEquipMask = true;
                SoulMaskUtil.addUseMaskParticle(player);
                break;
            case 1 :
                MaskPacket.send(2);
                wasUnEquipMask = true;
                break;
        }
    }

    private static void resetCooldowns() {
        unmaskCooldown = DEFAULT_COOLDOWN;
        equipmaskcd = DEFAULT_COOLDOWN;
    }

    // UI rendering part
    public static int getEquipmaskcd() {
        return equipmaskcd;
    }

    public static int getUnmaskCooldown() {
        return unmaskCooldown;
    }

    public static int getLastequipcd() {
        return DEFAULT_COOLDOWN;
    }

    public static int getLastunmaskcd() {
        return DEFAULT_COOLDOWN;
    }
    // End UI rendering part

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
