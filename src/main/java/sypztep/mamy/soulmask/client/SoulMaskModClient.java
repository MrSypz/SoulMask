package sypztep.mamy.soulmask.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import sypztep.mamy.soulmask.client.event.MaskHandleTick;
import sypztep.mamy.soulmask.client.event.MaskRenderEvent;
import sypztep.mamy.soulmask.common.SoulMaskMod;

public class SoulMaskModClient implements ClientModInitializer {
    public static final KeyBinding EQUIPMASK_KEYBINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + SoulMaskMod.MODID + ".equipmask", GLFW.GLFW_KEY_V, "key.categories." + SoulMaskMod.MODID));

    @Override
    public void onInitializeClient() {
        MaskHandleTick.init();
        HudRenderCallback.EVENT.register(new MaskRenderEvent());
    }
}
