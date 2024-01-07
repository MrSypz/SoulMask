package sypztep.mamy.soulmask.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.init.ModEntityComponents;
import sypztep.mamy.soulmask.common.util.SoulMaskUtil;

public class VizardComponent implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity obj;
    private boolean hasmask = false;
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
    @Override
    public void tick() {
        hasmask = SoulMaskUtil.hasEquippedMask(obj);
    }
    private int getHogyoku() {
        return this.hogyoku;
    }
    private void incHogyoku() {
        this.hogyoku += 1;
    }
    public static VizardComponent getHogyoku(PlayerEntity player) {
        return ModEntityComponents.VIZARD.get(player);
    }
    //Get Hogyoku value
    public static int getHogyokuValue(PlayerEntity player) {
        return ModEntityComponents.VIZARD.get(player).getHogyoku();
    }
    //Packet Hogyoku
    public static void incHogyoku(PlayerEntity player) {
        getHogyoku(player).incHogyoku();
        SoulMaskMod.LOGGER.info(player.getEntityName() + ":" + VizardComponent.getHogyokuValue(player));
        ModEntityComponents.VIZARD.sync(player);
    }


}
