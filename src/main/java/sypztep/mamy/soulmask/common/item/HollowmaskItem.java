package sypztep.mamy.soulmask.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.init.ModItems;
import sypztep.mamy.soulmask.common.init.ModSoundEvents;

import static sypztep.mamy.soulmask.common.init.ModStatusEffects.HOLLOW_POWER;

public class HollowmaskItem extends SoulMaskFuncItem {

    public HollowmaskItem(Settings settings) {
        super(ArmorMaterials.NETHERITE,Type.HELMET, settings);
    }

    @Override
    public SoundEvent getEquipSound() {
        return ModSoundEvents.ENTITY_GENERIC_CHARGE2;
    }
    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }
    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            ItemStack headSlot = player.getEquippedStack(EquipmentSlot.HEAD);
            boolean hasPowerHollow = player.hasStatusEffect(HOLLOW_POWER);
            if (headSlot.isOf(this)) {
                if (!hasPowerHollow) {
                    int amp = headSlot.isOf(ModItems.VASTO_MASK) ? 4 : headSlot.isOf(ModItems.HOLLOW_MASK_TIER4) ? 3 : headSlot.isOf(ModItems.HOLLOW_MASK_TIER3) ? 2 : headSlot.isOf(ModItems.HOLLOW_MASK_TIER2) ? 1 : 0;
                    int amp2 = headSlot.isOf(ModItems.VASTO_MASK) ? 4 : headSlot.isOf(ModItems.HOLLOW_MASK_TIER4) ? 3 : headSlot.isOf(ModItems.HOLLOW_MASK_TIER3) ? 2 : headSlot.isOf(ModItems.HOLLOW_MASK_TIER2) ? 1 : 0;
                    player.addStatusEffect(new StatusEffectInstance(HOLLOW_POWER, 100, amp2, false, false, false));
                    if (!headSlot.isOf(ModItems.HALF_HOLLOW_MASK))
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, amp, false, false, false));
                }
            }
            if (VizardComponent.getHogyokuValue(player) < 1 && headSlot.isOf(this)) {
                player.equipStack(EquipmentSlot.HEAD, new ItemStack(ItemStack.EMPTY.getItem()));
                player.damage(player.getWorld().getDamageSources().playerAttack(player),player.getHealth() * 0.5f);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}
