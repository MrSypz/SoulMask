package sypztep.mamy.soulmask.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import sypztep.mamy.soulmask.client.event.MaskHandleTick;
import sypztep.mamy.soulmask.common.init.ModEntityComponents;
import sypztep.mamy.soulmask.common.packetC2S.MaskEquipCDPacket;

public class VizardComponent implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity obj;
    private final int DEFAULT_DELAY = 600; // 30 Sec
    private int hogyoku = 0, delayUsemask = DEFAULT_DELAY, energy = 100;
    private boolean wasUnEquipMask = false, hasEquipMask = false ,unequipHandled = false;

    public VizardComponent(PlayerEntity obj) {
        this.obj = obj;
    }
    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hogyoku = tag.getInt("hogyoku");
        this.delayUsemask = tag.getInt("delayusemask");
        this.energy = tag.getInt("energy");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("hogyoku",this.hogyoku);
        tag.putInt("delayusemask",this.delayUsemask);
        tag.putInt("energy",this.energy);
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
    public boolean isWasUnEquipMask() {
        return wasUnEquipMask;
    }
    public static boolean HasEquipMask(PlayerEntity player) {
        return getVizard(player).hasEquipMask;
    }
    //Packet Hogyoku
    public void incHogyoku() {
        this.hogyoku += 1;
        ModEntityComponents.VIZARD.sync(this.obj);
    }
    //UI
    public int getDelayUsemask() {
        return delayUsemask;
    }
    public int getLastDelayUsemask() {
        return DEFAULT_DELAY;
    }

    @Override
    public void clientTick() {
        tick();
        wasUnEquipMask = MaskHandleTick.WasUnEquipMask();
        hasEquipMask = MaskHandleTick.isHasEquippedMask();
        if (energy == 0 && !unequipHandled) {
            MaskHandleTick.handleMaskUnequippingActions(obj, 1);
            unequipHandled = true;
        }
        if (wasUnEquipMask) {
            handle(this);
            MaskEquipCDPacket.send();
        } else {
            delayUsemask = DEFAULT_DELAY - energy - (80 * hogyoku);
            unequipHandled = false;
        }
    }
    @Override
    public void tick() {
        if (hasEquipMask) {
            if (obj.age % (5 + 2 * hogyoku) == 0 && energy > 0)
                energy--;
        } else {
            if (energy < 100 && obj.age % (20 - 3 * hogyoku) == 0)
                energy++;
        }
    }

    public void handle(VizardComponent component) {
        if (component.delayUsemask > 0) {
            component.delayUsemask--;
            if (component.delayUsemask == 0)
                MaskHandleTick.setWasUnEquipMask(false);
        } else component.delayUsemask = component.DEFAULT_DELAY;
    }
}
