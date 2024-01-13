package sypztep.mamy.soulmask.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.enchantment.HollowCurseEnchantment;

public class ModEnchantments {
    public static Enchantment HOLLOW_CURSE = new HollowCurseEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR, EquipmentSlot.HEAD);
    public static void register() {
        register("hollowcurse",HOLLOW_CURSE);
    }
    public static void register(String name, Enchantment enchantment){
        Registry.register(Registries.ENCHANTMENT, SoulMaskMod.id(name), enchantment);
    }
}
