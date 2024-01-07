package sypztep.mamy.soulmask.client.render.entity;

import software.bernie.geckolib.renderer.GeoArmorRenderer;
import sypztep.mamy.soulmask.client.render.model.HollowMaskModel;
import sypztep.mamy.soulmask.common.item.SoulMaskFuncItem;

public class HollowmaskRenderer extends GeoArmorRenderer<SoulMaskFuncItem> {
    public HollowmaskRenderer() {
        super(new HollowMaskModel());
    }
}
