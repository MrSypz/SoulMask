package sypztep.mamy.soulmask.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import sypztep.mamy.soulmask.common.enchantment.HollowCurseEnchantment;

public class ModEnchantments {
    public static Enchantment HOLLOW_CURSE = new HollowCurseEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR, EquipmentSlot.HEAD);
    public static void init() {
        Registry.register(Registries.ENCHANTMENT,"hollowcurse",HOLLOW_CURSE);
    }
}
