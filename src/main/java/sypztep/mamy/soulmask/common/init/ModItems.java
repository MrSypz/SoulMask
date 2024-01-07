package sypztep.mamy.soulmask.common.init;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.item.HogyokuItem;
import sypztep.mamy.soulmask.common.item.HollowmaskItem;
import sypztep.mamy.soulmask.common.item.VastomaskItem;

import java.util.Set;

public class ModItems {
    public static final Set<HollowmaskItem> ALL_MASK = new ReferenceOpenHashSet<>();

    public static HollowmaskItem HALF_HOLLOW_MASK;
    public static HollowmaskItem HOLLOW_MASK_TIER1;
    public static HollowmaskItem HOLLOW_MASK_TIER2;
    public static HollowmaskItem HOLLOW_MASK_TIER3;
    public static HollowmaskItem HOLLOW_MASK_TIER4;
    public static VastomaskItem VASTO_MASK;

    public static Item HOGYOKU;

    public static void init() {
        //HOLLOW MASK
        HALF_HOLLOW_MASK = registerMaskItem("half_hollow_mask", new HollowmaskItem(new FabricItemSettings().maxDamage(15))); // 10 Second
        HOLLOW_MASK_TIER1 = registerMaskItem("hollow_mask_1", new HollowmaskItem(new FabricItemSettings().maxDamage(30))); // 30 Second
        HOLLOW_MASK_TIER2 = registerMaskItem("hollow_mask_2", new HollowmaskItem(new FabricItemSettings().maxDamage(60))); //1 Minute
        HOLLOW_MASK_TIER3 = registerMaskItem("hollow_mask_3", new HollowmaskItem(new FabricItemSettings().maxDamage(180))); // 3 Minute
        HOLLOW_MASK_TIER4 = registerMaskItem("hollow_mask_4", new HollowmaskItem(new FabricItemSettings().maxDamage(300))); // 5 Minute
        VASTO_MASK = registerMaskItem("vasto_mask", new VastomaskItem(new FabricItemSettings().maxDamage(900))); // 15 Minute

        HOGYOKU = registerItem("hogyoku", new HogyokuItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));

        itemGroup();
    }
    public static <T extends Item> T registerMaskItem(String name, T item) {
        Registry.register(Registries.ITEM, SoulMaskMod.id(name), item);
        ALL_MASK.add((HollowmaskItem) item);
        return item;
    }
    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registries.ITEM, SoulMaskMod.id(name), item);
        return item;
    }
    public static void itemGroup() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.add(ModItems.HALF_HOLLOW_MASK);
            content.add(ModItems.HOLLOW_MASK_TIER1);
            content.add(ModItems.HOLLOW_MASK_TIER2);
            content.add(ModItems.HOLLOW_MASK_TIER3);
            content.add(ModItems.HOLLOW_MASK_TIER4);
            content.add(ModItems.VASTO_MASK);
            content.add(ModItems.HOGYOKU);
        });
    }
}
