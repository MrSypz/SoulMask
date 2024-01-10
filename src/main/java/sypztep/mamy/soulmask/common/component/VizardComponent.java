package sypztep.mamy.soulmask.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import sypztep.mamy.soulmask.client.event.MaskHandleTick;
import sypztep.mamy.soulmask.common.init.ModEntityComponents;
import sypztep.mamy.soulmask.common.packetC2S.MaskEnergyPacket;
import sypztep.mamy.soulmask.common.packetC2S.MaskEquipCDPacket;
import sypztep.mamy.soulmask.common.packetC2S.MaskPacket;

public class VizardComponent implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity obj;
    private final int DEFAULT_DELAY = 600; // 30 Sec
    private int hogyoku = 0, delayUsemask = DEFAULT_DELAY, soulEnergy = DEFAULT_DELAY,maxsoulEnergy = 0;
    private boolean wasEquipMask = false, hasEquipMask = false;

    public VizardComponent(PlayerEntity obj) {
        this.obj = obj;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hogyoku = tag.getInt("hogyoku");
        this.delayUsemask = tag.getInt("delayusemask");
        this.soulEnergy = tag.getInt("soulenergy");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("hogyoku",this.hogyoku);
        tag.putInt("delayusemask",this.delayUsemask);
        tag.putInt("soulenergy",this.soulEnergy);
    }
    /**
     this method is just simpify to use aka short from
     */
    private static VizardComponent getVizard(PlayerEntity player) {
        return ModEntityComponents.VIZARD.get(player);
    }
    //Get Hogyoku value
    public static int getHogyokuValue(PlayerEntity player) {
        return getVizard(player).hogyoku;
    }
    public static boolean canUseMask(PlayerEntity player) {
        return getHogyokuValue(player) > 0;
    }
    public static boolean WasEquipMask(PlayerEntity player) {
        return getVizard(player).wasEquipMask;
    }
    public static void setMaxSoulEnergy(PlayerEntity player) {
        getVizard(player).maxsoulEnergy = getVizard(player).DEFAULT_DELAY * getVizard(player).hogyoku;
    }
    public int getMaxsoulEnergy() {
        return maxsoulEnergy;
    }

    public int getSoulEnergy() {
        return soulEnergy;
    }

    //Packet Hogyoku
    public static void incHogyoku(PlayerEntity player) {
        getVizard(player).hogyoku += 1;
        ModEntityComponents.VIZARD.sync(player);
    }
    /**
     * UI PART
     */
    public boolean isWasEquipMask() {
        return wasEquipMask;
    }

    public boolean isHasEquipMask() {
        return hasEquipMask;
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
        hasEquipMask = MaskHandleTick.HasEquippedMask();
        if (hasEquipMask) {
            if (soulEnergy > 0)
                soulEnergy--;
            ModEntityComponents.VIZARD.sync(obj);
            handleEnergy(this);
            MaskEnergyPacket.send();
        } else if (soulEnergy < maxsoulEnergy)
            soulEnergy++;
        if (wasEquipMask) {
            handle(this);
            MaskEquipCDPacket.send();
        } else delayUsemask = DEFAULT_DELAY;
    }
    @Override
    public void tick() {
        setMaxSoulEnergy(obj);
    }
    public static void handle(VizardComponent component) {
        if (component.delayUsemask > 0) {
            component.delayUsemask--;
            if (component.delayUsemask == 0)
                MaskHandleTick.setWasUnEquipMask(false);
        } else component.delayUsemask = component.DEFAULT_DELAY;
    }
    public static void handleEnergy(VizardComponent component) {
        if (component.soulEnergy == 0) {
            MaskPacket.send(2);
            MaskHandleTick.setWasUnEquipMask(true);
        }
    }
}
