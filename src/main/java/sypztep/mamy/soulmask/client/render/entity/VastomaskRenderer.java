package sypztep.mamy.soulmask.client.render.entity;

import software.bernie.geckolib.renderer.GeoArmorRenderer;
import sypztep.mamy.soulmask.client.render.model.VastoMaskModel;
import sypztep.mamy.soulmask.common.item.SoulMaskFuncItem;

public class VastomaskRenderer extends GeoArmorRenderer<SoulMaskFuncItem> {
    public VastomaskRenderer() {
        super(new VastoMaskModel());
    }
}
