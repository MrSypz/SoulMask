package sypztep.mamy.soulmask.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sypztep.mamy.soulmask.common.init.ModEnchantments;
import sypztep.mamy.soulmask.common.init.ModItems;
import sypztep.mamy.soulmask.common.init.ModParticles;
import sypztep.mamy.soulmask.common.init.ModStatusEffects;
import sypztep.mamy.soulmask.common.packetC2S.HogyokuPacket;
import sypztep.mamy.soulmask.common.packetC2S.MaskEquipCDPacket;
import sypztep.mamy.soulmask.common.packetC2S.MaskPacket;

public class SoulMaskMod implements ModInitializer {
    public static final String MODID = "soulmask";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }
    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(MaskPacket.ID, new MaskPacket.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(MaskEquipCDPacket.ID, new MaskEquipCDPacket.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(HogyokuPacket.ID, new HogyokuPacket.Receiver());

        ModItems.init();
        ModParticles.init();
        ModEnchantments.init();
        ModStatusEffects.init();
    }
}
