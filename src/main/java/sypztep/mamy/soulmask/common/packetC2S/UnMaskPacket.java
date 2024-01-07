package sypztep.mamy.soulmask.common.packetC2S;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.util.SoulMaskUtil;

public class UnMaskPacket {
    public static final Identifier ID = SoulMaskMod.id("unmask");
    public static void send() {
        ClientPlayNetworking.send(ID, new PacketByteBuf(Unpooled.buffer()));
    }
//
    public static class Receiver implements ServerPlayNetworking.PlayChannelHandler {
        @Override
        public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            SoulMaskUtil.unequipMask(player);
        }
    }
}
