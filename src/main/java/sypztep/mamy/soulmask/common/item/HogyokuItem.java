package sypztep.mamy.soulmask.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import sypztep.mamy.soulmask.common.component.VizardComponent;
import sypztep.mamy.soulmask.common.packetC2S.HogyokuPacket;

public class HogyokuItem extends Item {
    public HogyokuItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int rank = VizardComponent.getHogyokuValue(user);
        if (world.isClient) {
            if (rank < 5)
                HogyokuPacket.send();
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
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
