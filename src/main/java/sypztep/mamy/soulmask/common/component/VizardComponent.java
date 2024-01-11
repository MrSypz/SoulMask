package sypztep.mamy.soulmask.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import sypztep.mamy.soulmask.client.event.MaskHandleTick;
import sypztep.mamy.soulmask.common.init.ModEntityComponents;
import sypztep.mamy.soulmask.common.packetC2S.MaskEquipCDPacket;
import sypztep.mamy.soulmask.common.packetC2S.MaskPacket;

public class VizardComponent implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity obj;
    private final int DEFAULT_DELAY = 600; // 30 Sec
    private int hogyoku = 0, delayUsemask = DEFAULT_DELAY;
    private boolean wasEquipMask = false;

    public VizardComponent(PlayerEntity obj) {
        this.obj = obj;
    }
    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hogyoku = tag.getInt("hogyoku");
        this.delayUsemask = tag.getInt("delayusemask");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("hogyoku",this.hogyoku);
        tag.putInt("delayusemask",this.delayUsemask);
    }
    private int getVizard() {
        return this.hogyoku;
    }
    /**
     this method is just simpify to use aka short from
     */
    private static VizardComponent getVizard(PlayerEntity player) {
        return ModEntityComponents.VIZARD.get(player);
    }
    //Get Hogyoku value
    public static int getHogyokuValue(PlayerEntity player) {
        return getVizard(player).getVizard();
    }
    public static boolean canUseMask(PlayerEntity player) {
        return getHogyokuValue(player) > 0;
    }
    public boolean isWasEquipMask() {
        return wasEquipMask;
    }
    public static boolean WasEquipMask(PlayerEntity player) {
        return getVizard(player).isWasEquipMask();
    }
    //Packet Hogyoku
    public static void incHogyoku(PlayerEntity player) {
        getVizard(player).hogyoku += 1;
        ModEntityComponents.VIZARD.sync(player);
    }
    public int getDelayUsemask() {
        return delayUsemask;
    }
    public int getLastDelayUsemask() {
        return DEFAULT_DELAY;
    }

    @Override
    public void clientTick() {
        tick();
        wasEquipMask = MaskHandleTick.WasUnEquipMask();
        if (wasEquipMask) {
            handle(this);
            MaskEquipCDPacket.send();
        } else delayUsemask = DEFAULT_DELAY;
    }
    @Override
    public void tick() {
    }
    public static void handle(VizardComponent component) {
        if (component.delayUsemask > 0) {
            component.delayUsemask--;
            if (component.delayUsemask == 0)
                MaskHandleTick.setWasUnEquipMask(false);
        } else component.delayUsemask = component.DEFAULT_DELAY;
    }
}
