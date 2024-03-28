package sypztep.mamy.soulmask.common.init;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.statuseffects.EmptyStatusEffect;

public class ModStatusEffects {
    public static final StatusEffect HOLLOW_POWER = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL,0)
            .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,"52f38a34-b1a7-4a65-928e-8a30608b7432",0.12D, EntityAttributeModifier.Operation.MULTIPLY_BASE)
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,"90c7a8a9-2b36-43ce-a28d-08d29c1ebead",0.02D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    public static void init() {
        Registry.register(Registries.STATUS_EFFECT, SoulMaskMod.id("hollowpower"), HOLLOW_POWER);
    }
}
