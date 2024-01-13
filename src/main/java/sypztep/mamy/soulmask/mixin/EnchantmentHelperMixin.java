package sypztep.mamy.soulmask.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sypztep.mamy.soulmask.common.init.ModEnchantments;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
	@Inject(method = "hasVanishingCurse", at = @At("HEAD"), cancellable = true)
	private static void hollowvanish(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (EnchantmentHelper.getLevel(ModEnchantments.HOLLOW_CURSE, stack)  > 0) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "hasBindingCurse", at = @At("HEAD"), cancellable = true)
	private static void hollowbind(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (EnchantmentHelper.getLevel(ModEnchantments.HOLLOW_CURSE, stack) > 0) {
			cir.setReturnValue(true);
		}
	}
}
