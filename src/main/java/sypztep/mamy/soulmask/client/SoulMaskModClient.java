package sypztep.mamy.soulmask.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.packetC2S.MaskPacket;
import sypztep.mamy.soulmask.common.packetC2S.UnMaskPacket;
import sypztep.mamy.soulmask.common.util.SoulMaskUtil;

public class SoulMaskModClient implements ClientModInitializer {
    public static final KeyBinding EQUIPMASK_KEYBINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + SoulMaskMod.MODID + ".equipmask", GLFW.GLFW_KEY_V, "key.categories." + SoulMaskMod.MODID));
    private final int DEFAULT_COOLDOWN = 20;
    private int equipmaskcd = DEFAULT_COOLDOWN,unmaskcd = DEFAULT_COOLDOWN;
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (minecraft.player != null) {
                SoulMaskMod.LOGGER.info(String.valueOf(equipmaskcd));
                if (!SoulMaskUtil.hasEquippedMask(minecraft.player) && EQUIPMASK_KEYBINDING.isPressed()) {
                    if (VizardComponent.canUseMask(minecraft.player)) {
                        equipmaskcd--;
                        if (equipmaskcd <= 0) {
                            MaskPacket.send();
                            equipmaskcd = DEFAULT_COOLDOWN;
                        }
                    }
                }
                else if (SoulMaskUtil.hasEquippedMask(minecraft.player) && EQUIPMASK_KEYBINDING.isPressed()) {
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
}
