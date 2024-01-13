package sypztep.mamy.soulmask.common.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.init.ModItems;
import sypztep.mamy.soulmask.common.init.ModParticles;
import sypztep.mamy.soulmask.common.item.HollowmaskItem;

public class SoulMaskUtil {
    private static MinecraftClient getClient(){
        return MinecraftClient.getInstance();
    }
    private static ItemStack getHeadSlot(LivingEntity living) {
        return living.getEquippedStack(EquipmentSlot.HEAD);
    }
    private static boolean MaskItemChecked(ItemStack stack) {
        return stack != null && stack.getItem() instanceof HollowmaskItem;
    }
    public static boolean hasEquippedMask(LivingEntity living) {
        return MaskItemChecked(getHeadSlot(living));
    }
    public static void checkHelmet(PlayerEntity user) {
        boolean itemToCheck = hasAnyMask(user);
        ItemStack headslot = getHeadSlot(user);
        if (headslot.isEmpty())
            equipMask(user);
        else {
            if (!itemToCheck) { // if item isn't a mask replace it
                int emptySlot = user.getInventory().getEmptySlot();
                if (emptySlot >= 0)
                    user.getInventory().setStack(emptySlot, headslot);
                else
                    user.dropItem(headslot, false);
                equipMask(user);
            }
        }
    }
    private static boolean hasAnyMask(PlayerEntity user) {
        for (ItemStack stack : user.getInventory().armor)
            if (ModItems.ALL_MASK.contains(stack.getItem())) //if has mask return
                return true;
        return false;
    }
    public static void unequipMask(PlayerEntity user) {
        user.equipStack(EquipmentSlot.HEAD,ItemStack.EMPTY);
    }
    public static void equipMask(PlayerEntity user) {
        if (user.getWorld().isClient()) {
            addUseMaskParticle(user);
            return;
        }
        int getrank = VizardComponent.getHogyokuValue(user);
        ItemStack Hollowmask = getItemStack(getrank);
            //TODO: Add Feature to take off and HOLLOW CURSE ENCHANTMENT And add cool effect like red pillar from sky
//        Hollowmask.addEnchantment(ModEnchantments.HOLLOW_CURSE, 1);
        user.equipStack(EquipmentSlot.HEAD, Hollowmask);
//        user.damage(user.getWorld().getDamageSources().create(ModDamageTypes.MASKIMPACT, user), user.getHealth() * 0.5f);
//        OrbitalEntity orbitalEntity = new OrbitalEntity(user.getWorld(),user);
//        if (baseValue > 4)
//            user.getWorld().spawnEntity(orbitalEntity);
    }
    public static void addUseMaskParticle(PlayerEntity player) { //Client Packet
        if (getClient().gameRenderer.getCamera().isThirdPerson() || player != getClient().cameraEntity)
            player.getWorld().addParticle(ModParticles.BLOODWAVE, player.getX(), player.getY(), player.getZ(), 0.0, 0.0, 0.0);
    }
    public static void addChargeParticle(PlayerEntity player) { //Client Packet
        if (getClient().gameRenderer.getCamera().isThirdPerson() || player != getClient().cameraEntity) {
            double radius = 1.0 + VizardComponent.getHogyokuValue(player);
            for (int i = 0; i < 360; i += 10) { // Increase the step for a smoother rotation
                double angle = Math.toRadians(i);
                double xOffset = radius * MathHelper.cos((float) angle);
                double zOffset = radius * MathHelper.sin((float) angle);
                player.getWorld().addParticle(ParticleTypes.SOUL, player.getX() + xOffset, player.getY(), player.getZ() + zOffset, 0.0, 0.0, 0.0);
            }
        }
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