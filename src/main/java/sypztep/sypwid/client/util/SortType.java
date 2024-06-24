package sypztep.sypwid.client.util;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Comparator;
import java.util.List;

/**
 * Utility class containing various static Comparator implementations for sorting ItemStacks based on different criteria.
 */
public class SortType {

    /**
     * Comparator to place BlockItems before non-BlockItems.
     */
    public static final Comparator<ItemStack> BLOCKS = (lhs, rhs) ->
            lhs.getItem() instanceof BlockItem ? -1 : (rhs.getItem() instanceof BlockItem ? 1 : 0);

    /**
     * Comparator to place non-BlockItems before BlockItems.
     */
    public static final Comparator<ItemStack> ITEMS = (lhs, rhs) ->
            !(lhs.getItem() instanceof BlockItem) ? -1 : (!(rhs.getItem() instanceof BlockItem) ? 1 : 0);

    /**
     * Comparator to place stackable items (maxCount > 1) before non-stackable items.
     */
    public static final Comparator<ItemStack> STACKABLES = (lhs, rhs) ->
            lhs.getMaxCount() > 1 ? -1 : (rhs.getMaxCount() > 1 ? 1 : 0);

    /**
     * Comparator to place non-stackable items (maxCount == 1) before stackable items.
     */
    public static final Comparator<ItemStack> UNSTACKABLES = (lhs, rhs) ->
            lhs.getMaxCount() == 1 ? -1 : (rhs.getMaxCount() == 1 ? 1 : 0);

    /**
     * Comparator to compare ItemStacks based on the damage value (ascending order).
     */
    public static final Comparator<ItemStack> DAMAGE = Comparator.comparingInt(ItemStack::getDamage);

    /**
     * Comparator to compare ItemStacks based on the count (descending order).
     */
    public static final Comparator<ItemStack> COUNT = Comparator.comparingInt(ItemStack::getCount).reversed();

    /**
     * Creates a Comparator that compares ItemStacks based on their identity.
     *
     * @param id Identifier of the item to compare against.
     * @return A Comparator for comparing ItemStacks based on identity.
     */
    public static Comparator<ItemStack> item(Identifier id) {
        return (lhs, rhs) -> {
            Item item = Registries.ITEM.get(id);
            return lhs.isOf(item) ? -1 : (rhs.isOf(item) ? 1 : 0);
        };
    }

    /**
     * Creates a Comparator that compares ItemStacks based on whether they belong to a specific tag.
     *
     * @param id Identifier of the tag to compare against.
     * @return A Comparator for comparing ItemStacks based on tags.
     */
    public static Comparator<ItemStack> itemTag(Identifier id) {
        return (lhs, rhs) -> lhs.isIn(TagKey.of(RegistryKeys.ITEM, id)) ? -1 : (rhs.isIn(TagKey.of(RegistryKeys.ITEM, id)) ? 1 : 0);
    }

    /**
     * Creates a Comparator that compares ItemStacks based on whether they belong to a specific block tag.
     *
     * @param id Identifier of the block tag to compare against.
     * @return A Comparator for comparing ItemStacks based on block tags.
     */
    public static Comparator<ItemStack> blockTag(Identifier id) {
        return (lhs, rhs) -> {
            Item lItem = lhs.getItem();
            Item rItem = rhs.getItem();

            boolean lBlockTag = lItem instanceof BlockItem && Block.getBlockFromItem(lItem).getDefaultState().isIn(TagKey.of(RegistryKeys.BLOCK, id));
            boolean rBlockTag = rItem instanceof BlockItem && Block.getBlockFromItem(rItem).getDefaultState().isIn(TagKey.of(RegistryKeys.BLOCK, id));

            return lBlockTag ? -1 : (rBlockTag ? 1 : 0);
        };
    }

    /**
     * Creates a Comparator that compares ItemStacks based on whether they belong to a specific item group.
     *
     * @param id Identifier of the item group to compare against.
     * @return A Comparator for comparing ItemStacks based on item groups.
     */
    public static Comparator<ItemStack> itemGroup(Identifier id) {
        return (lhs, rhs) -> {
            ItemGroup group = Registries.ITEM_GROUP.get(id);
            if (group == null) return 0;
            return group.contains(lhs) ? -1 : (group.contains(rhs) ? 1 : 0);
        };
    }

    /**
     * Creates a Comparator that compares ItemStacks based on their order in a specific item group.
     *
     * @param id Identifier of the item group to compare against.
     * @return A Comparator for comparing ItemStacks based on item group order.
     */
    public static Comparator<ItemStack> itemGroupOrder(Identifier id) {
        return (lhs, rhs) -> {
            ItemGroup group = Registries.ITEM_GROUP.get(id);
            if (group == null) return 0;
            List<ItemStack> tabStacks = Lists.newArrayList(group.getSearchTabStacks());
            for (int i = 0; i < tabStacks.size(); ++i) {
                Item item = tabStacks.get(i).getItem();
                if (lhs.isOf(item)) return -1;
                if (rhs.isOf(item)) return 1;
            }
            return 0;
        };
    }
}
