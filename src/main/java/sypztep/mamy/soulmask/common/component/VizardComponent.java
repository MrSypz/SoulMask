package sypztep.mamy.soulmask.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import sypztep.mamy.soulmask.client.event.MaskHandleTick;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.init.ModEntityComponents;
import sypztep.mamy.soulmask.common.packetC2S.MaskEquipCDPacket;

public class VizardComponent implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity obj;
    private final int DEFAULT_DELAY = 600;
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
    private int getHogyoku() {
        return this.hogyoku;
    }

//    private void incHogyoku() {
//        this.hogyoku += 1;
//    }

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

    public boolean isWasEquipMask() {
        return wasEquipMask;
    }
    public static boolean WasEquipMask(PlayerEntity player) {
        return getHogyoku(player).isWasEquipMask();
    }

    //Packet Hogyoku
    public static void incHogyoku(PlayerEntity player) {
        getHogyoku(player).hogyoku += 1;
        ModEntityComponents.VIZARD.sync(player);
    }

    public int getDelayUsemask() {
        return delayUsemask;
    }

    @Override
    public void clientTick() {
        wasEquipMask = MaskHandleTick.isWasUnEquipMask();
        tick();
        if (wasEquipMask) {
            handle(this);
            MaskEquipCDPacket.send();
        } else delayUsemask = DEFAULT_DELAY;
    }

    @Override
    public void tick() {
        SoulMaskMod.LOGGER.info(String.valueOf(delayUsemask));
        SoulMaskMod.LOGGER.info(String.valueOf(wasEquipMask));
    }
    public static void handle(VizardComponent component) {
        if (component.delayUsemask > 0) {
            component.delayUsemask--;
            if (component.delayUsemask == 0)
                MaskHandleTick.setWasUnEquipMask(false);
        }
        else component.delayUsemask = component.DEFAULT_DELAY;
    }
}
