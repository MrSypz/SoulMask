package sypztep.mamy.soulmask.common.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.packetC2S.HogyokuPacket;

import java.util.List;

public class HogyokuItem extends Item {
    public HogyokuItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int rank = VizardComponent.getHogyokuValue(user);
        if (world.isClient) {
            if (rank < 5) {
                if (!user.isCreative())
                    stack.decrement(1);
                HogyokuPacket.send();
                user.playSound(SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.PLAYERS, 0.8f, 1.45f);
            }
            return TypedActionResult.fail(stack);
        }
        if (rank < 5) {
            user.sendMessage(Text.translatable("soulmask.hogyoku.success").formatted(Formatting.GOLD), true);
            return TypedActionResult.success(stack);
        }
        user.sendMessage(Text.translatable("soulmask.hogyoku.limit_reached").formatted(Formatting.DARK_RED), true);
        return TypedActionResult.fail(stack);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendItemDescription(tooltip);
        super.appendTooltip(stack, world, tooltip, context);
    }

    private void appendItemDescription(List<Text> tooltip) {
        Item item = this;
        String translationKey = item.getTranslationKey();
        MutableText passive = (Text.translatable(translationKey + ".desc")).formatted(Formatting.GRAY);
        tooltip.add(passive);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
