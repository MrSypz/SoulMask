package sypztep.mamy.soulmask.client.render.model;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.init.ModItems;
import sypztep.mamy.soulmask.common.item.SoulMaskFuncItem;

public class HollowMaskModel extends GeoModel<SoulMaskFuncItem>{
    @Override
    public Identifier getModelResource(SoulMaskFuncItem animatable) {
        return SoulMaskMod.id("geo/hollowmask.geo.json");
    }
    @Override
    public Identifier getTextureResource(SoulMaskFuncItem animatable) {
        ItemStack stack = new ItemStack(animatable);
        if (stack.isOf(ModItems.HOLLOW_MASK_TIER1))
            return SoulMaskMod.id("textures/armor/hollow_mask1_armor.png");
         else if (stack.isOf(ModItems.HOLLOW_MASK_TIER2))
            return SoulMaskMod.id("textures/armor/hollow_mask2_armor.png");
        else if (stack.isOf(ModItems.HOLLOW_MASK_TIER3))
            return SoulMaskMod.id("textures/armor/hollow_mask3_armor.png");
        else if (stack.isOf(ModItems.HOLLOW_MASK_TIER4))
            return SoulMaskMod.id("textures/armor/hollow_mask4_armor.png");
         else
            return SoulMaskMod.id("textures/armor/half_hollow_mask_armor.png");
    }

    @Override
    public Identifier getAnimationResource(SoulMaskFuncItem animatable) {
        return null;
    }
}
