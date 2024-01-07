package sypztep.mamy.soulmask.common.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.init.ModItems;
import sypztep.mamy.soulmask.common.item.HollowmaskItem;

public class SoulMaskUtil {
    private static ItemStack getHeadSlot(LivingEntity living) {
        return living.getEquippedStack(EquipmentSlot.HEAD);
    }
    private static boolean MaskItemChecked(ItemStack stack) {
        return stack != null && stack.getItem() instanceof HollowmaskItem;
    }
    public static boolean hasEquippedMask(LivingEntity living) {
        return MaskItemChecked(getHeadSlot(living));
    }
    public static void UseMask(PlayerEntity user) {
        boolean itemToCheck = hasAnyMask(user);
        ItemStack headslot = getHeadSlot(user);
        if (headslot.isEmpty())
            equipMask(user);
        else {
            if (!itemToCheck) {
                int emptySlot = user.getInventory().getEmptySlot();
                if (emptySlot >= 0)
                    user.getInventory().setStack(emptySlot, headslot);
                else
                    user.dropItem(headslot, false);
                equipMask(user);
            }
        }
    }
    public static boolean hasAnyMask(PlayerEntity player) {
        for (ItemStack stack : player.getInventory().armor) {
            for (HollowmaskItem mask : ModItems.ALL_MASK) {
                if (stack.getItem() == mask) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void equipMask(PlayerEntity user) {
        if (user.getWorld().isClient()) {
            return;
        }
        int getrank = VizardComponent.getHogyokuValue(user);
        ItemStack Hollowmask = getItemStack(getrank);
//        Hollowmask.addEnchantment(ModEnchantments.HOLLOW_CURSE, 1);
        user.equipStack(EquipmentSlot.HEAD, Hollowmask);
//        user.damage(user.getWorld().getDamageSources().create(ModDamageTypes.MASKIMPACT, user), user.getHealth() * 0.5f);
//        OrbitalEntity orbitalEntity = new OrbitalEntity(user.getWorld(),user);
//        if (baseValue > 4)
//            user.getWorld().spawnEntity(orbitalEntity);
    }
    @NotNull
    private static ItemStack getItemStack(int baseValue) {
        ItemStack Hollowmask;
        if (baseValue == 1)
            Hollowmask = new ItemStack(ModItems.HALF_HOLLOW_MASK);
        else if (baseValue == 2)
            Hollowmask = new ItemStack(ModItems.HOLLOW_MASK_TIER1);
        else if (baseValue == 3)
            Hollowmask = new ItemStack(ModItems.HOLLOW_MASK_TIER2);
        else if (baseValue == 4)
            Hollowmask = new ItemStack(ModItems.HOLLOW_MASK_TIER3);
        else if (baseValue == 5)
            Hollowmask = new ItemStack(ModItems.HOLLOW_MASK_TIER4);
        else if (baseValue == 6)
            Hollowmask = new ItemStack(ModItems.VASTO_MASK);
        else Hollowmask = new ItemStack(ModItems.HALF_HOLLOW_MASK);
        return Hollowmask;
    }
}