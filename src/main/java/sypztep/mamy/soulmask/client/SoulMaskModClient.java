package sypztep.mamy.soulmask.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.packetC2S.MaskPacket;
import sypztep.mamy.soulmask.common.util.SoulMaskUtil;

public class SoulMaskModClient implements ClientModInitializer {
    public static final KeyBinding EQUIPMASK_KEYBINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + SoulMaskMod.MODID + ".equipmask", GLFW.GLFW_KEY_V, "key.categories." + SoulMaskMod.MODID));

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (minecraft.player != null) {
                if (EQUIPMASK_KEYBINDING.wasPressed())
                    if (VizardComponent.getHogyokuValue(minecraft.player) > 0)
                        MaskPacket.send();
            }
        });
    }
}
