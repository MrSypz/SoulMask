package sypztep.mamy.soulmask.common.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import org.jetbrains.annotations.NotNull;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.init.ModEnchantments;
import sypztep.mamy.soulmask.common.init.ModItems;
import sypztep.mamy.soulmask.common.init.ModParticles;
import sypztep.mamy.soulmask.common.item.HollowmaskItem;

import java.util.Random;

public class SoulMaskUtil {
    private static MinecraftClient getClient() {
        return MinecraftClient.getInstance();
    }

    private static ItemStack getHeadSlot(LivingEntity living) {
        return living.getEquippedStack(EquipmentSlot.HEAD);
    }

    private static boolean isMaskItem(ItemStack stack) {
        return stack.getItem() instanceof HollowmaskItem;
    }

    public static boolean hasEquippedMask(LivingEntity living) {
        return isMaskItem(getHeadSlot(living));
    }

    private static boolean hasAnyMask(PlayerEntity user) {
        for (ItemStack stack : user.getInventory().armor) {
            if (ModItems.ALL_MASK.contains(stack.getItem())) {
                return true;
            }
        }
        return false;
    }

    public static void checkHelmet(PlayerEntity user) {
        boolean itemToCheck = hasAnyMask(user);
        ItemStack headSlot = getHeadSlot(user);
        if (headSlot.isEmpty()) {
            equipMask(user);
        } else {
            if (!itemToCheck) { // if item isn't a mask, replace it
                int emptySlot = user.getInventory().getEmptySlot();
                if (emptySlot >= 0) {
                    user.getInventory().setStack(emptySlot, headSlot);
                } else {
                    user.dropItem(headSlot, false);
                }
                equipMask(user);
            }
        }
    }
    public static void unequipMask(PlayerEntity user) {
        user.equipStack(EquipmentSlot.HEAD,ItemStack.EMPTY);
    }
    public static void equipMask(PlayerEntity user) {
        if (user.getWorld().isClient()) {
            addUseMaskParticle(user);
            return;
        }
        int rank = VizardComponent.getHogyokuValue(user);
        ItemStack Hollowmask = getItemStack(rank);
        //TODO: add cool effect like red pillar from sky
        Hollowmask.addEnchantment(ModEnchantments.HOLLOW_CURSE, 1);
        user.equipStack(EquipmentSlot.HEAD, Hollowmask);
//        user.damage(user.getWorld().getDamageSources().create(ModDamageTypes.MASKIMPACT, user), user.getHealth() * 0.5f);
//        OrbitalEntity orbitalEntity = new OrbitalEntity(user.getWorld(),user);
//        if (baseValue > 4)
//            user.getWorld().spawnEntity(orbitalEntity);
    }
    public static void addUseMaskParticle(PlayerEntity player) { //Client Packet
        if (getClient().gameRenderer.getCamera().isThirdPerson() || player != getClient().cameraEntity) {
            double radius = 1.0 + VizardComponent.getHogyokuValue(player);
            for (int i = 0; i < 360; i += 8) { // Increase the step for a smoother rotation
                double circle = Math.toRadians(i);
                double x = radius * 0.2 * Math.cos(circle) * 1.5d;
                double z = radius * 0.2 * Math.sin(circle) * 1.5d;

                double xVec = x * 0.25d;
                double zVec = z * 0.25d;

                player.getWorld().addParticle(ParticleTypes.SOUL, player.getX() + x, player.getEyeY() + z , player.getZ() + z, xVec,0,zVec);
                player.getWorld().addParticle(ParticleTypes.SOUL, player.getX() + x, player.getEyeY() + (z * -1) , player.getZ() + z, xVec,0,zVec);
                player.getWorld().addParticle(ParticleTypes.SOUL, player.getX() + x, player.getEyeY() + ((z * 2) * -1) , player.getZ() + z, xVec,0,zVec);
                player.getWorld().addParticle(ParticleTypes.SOUL, player.getX() + x, player.getEyeY() + ((z * 2)) , player.getZ() + z, xVec,0,zVec);
            }
            player.getWorld().addParticle(ModParticles.BLOODWAVE, player.getX(), player.getY(), player.getZ(), 0.0, 0.0, 0.0);
        }
    }
    public static void addChargeParticle(PlayerEntity player) { //Client Packet
        if (getClient().gameRenderer.getCamera().isThirdPerson() || player != getClient().cameraEntity) {
            Random random = new Random();
            double range = 1.0 + VizardComponent.getHogyokuValue(player);
            int numParticles = 32;
            for (int i = 0; i < numParticles; i ++) { // Increase the step for a smoother rotation
                double angle = (double) i / numParticles * Math.PI * 2.0;
                double radius = range + (i * 0.01f) * random.nextFloat(); // Varying radius for each particle
                double yOffset = random.nextFloat() * 64.0; // Increased y-axis offset

                double xOffset = radius * Math.cos(angle);
                double zOffset = radius * Math.sin(angle);

                double posX = player.getPos().x + xOffset;
                double posY = player.getPos().y + yOffset;
                double posZ = player.getPos().z + zOffset;

                player.getWorld().addParticle(ParticleTypes.SONIC_BOOM ,true, posX, posY, posZ, 0, 0, 0);
            }
        }
    }
    @NotNull
    private static ItemStack getItemStack(int baseValue) {
        Item maskItem = switch (baseValue) {
            case 2 -> ModItems.HOLLOW_MASK_TIER1;
            case 3 -> ModItems.HOLLOW_MASK_TIER2;
            case 4 -> ModItems.HOLLOW_MASK_TIER3;
            case 5 -> ModItems.HOLLOW_MASK_TIER4;
            case 6 -> ModItems.VASTO_MASK;
            default -> ModItems.HALF_HOLLOW_MASK;
        };
        return new ItemStack(maskItem);
    }
}
