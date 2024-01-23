package sypztep.mamy.soulmask.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import sypztep.mamy.soulmask.common.component.VizardComponent;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity{

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * High Risk with High Reward
     * Player Take Damage more 15% from any source
     */
    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float soulmask$HollowCurse(float amount) {
        PlayerEntity player = PlayerEntity.class.cast(this);
        if (VizardComponent.getHogyokuValue(player) > 0 && !VizardComponent.HasEquipMask(player))
            return amount * 1.15f; //15% more Damage
        return amount;
    }
}
