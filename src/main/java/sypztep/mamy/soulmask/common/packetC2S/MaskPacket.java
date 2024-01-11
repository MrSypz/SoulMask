package sypztep.mamy.soulmask.common.packetC2S;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import sypztep.mamy.soulmask.client.packetS2C.AddMaskParticlePacket;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.util.SoulMaskUtil;

public class MaskPacket {
    public static final Identifier ID = SoulMaskMod.id("mask");
    public static void send(int select) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(select);
        ClientPlayNetworking.send(ID, buf);
    }
//
    public static class Receiver implements ServerPlayNetworking.PlayChannelHandler {
        @Override
        public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            int select = buf.readInt();
            switch (select) {
                case 1: SoulMaskUtil.checkHelmet(player);
                    PlayerLookup.tracking(player).forEach(foundPlayer -> AddMaskParticlePacket.send(foundPlayer, player.getId()));
                break;
                case 2: SoulMaskUtil.unequipMask(player);
                break;
            }
        }
    }
}
