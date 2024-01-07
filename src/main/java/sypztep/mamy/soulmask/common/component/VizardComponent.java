package sypztep.mamy.soulmask.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import sypztep.mamy.soulmask.client.SoulMaskModClient;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.init.ModEntityComponents;
import sypztep.mamy.soulmask.common.packetC2S.MaskPacket;
import sypztep.mamy.soulmask.common.util.SoulMaskUtil;

import static sypztep.mamy.soulmask.client.SoulMaskModClient.EQUIPMASK_KEYBINDING;

public class VizardComponent implements AutoSyncedComponent {
    private final PlayerEntity obj;
    private int hogyoku = 0;

    public VizardComponent(PlayerEntity obj) {
        this.obj = obj;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hogyoku = tag.getInt("hogyoku");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("hogyoku",this.hogyoku);
    }
    private int getHogyoku() {
        return this.hogyoku;
    }
    private void incHogyoku() {
        this.hogyoku += 1;
    }
    /**
     this method is just simpify to use aka short from
     */
    private static VizardComponent getHogyoku(PlayerEntity player) {
        return ModEntityComponents.VIZARD.get(player);
    }
    //Get Hogyoku value
    public static int getHogyokuValue(PlayerEntity player) {
        return getHogyoku(player).getHogyoku();
    }
    public static boolean canUseMask(PlayerEntity player) {
        return getHogyokuValue(player) > 0;
    }
    //Packet Hogyoku
    public static void incHogyoku(PlayerEntity player) {
        getHogyoku(player).incHogyoku();
        ModEntityComponents.VIZARD.sync(player);
    }
}
