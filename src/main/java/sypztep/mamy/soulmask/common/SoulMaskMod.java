package sypztep.mamy.soulmask.common;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sypztep.mamy.soulmask.common.init.ModItems;

public class SoulMaskMod implements ModInitializer {
    public static final String MODID = "soulmask";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }
    @Override
    public void onInitialize() {
        ModItems.init();
    }
}
