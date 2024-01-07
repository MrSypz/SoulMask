package sypztep.mamy.soulmask.client.render.model;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.item.SoulMaskFuncItem;


public class VastoMaskModel extends GeoModel<SoulMaskFuncItem>{
    @Override
    public Identifier getModelResource(SoulMaskFuncItem animatable) {
        return SoulMaskMod.id( "geo/vastomask.geo.json");
    }
    @Override
    public Identifier getTextureResource(SoulMaskFuncItem animatable) {
        return SoulMaskMod.id( "textures/armor/vasto_mask_armor.png");
    }

    @Override
    public Identifier getAnimationResource(SoulMaskFuncItem animatable) {
        return null;
    }
}
