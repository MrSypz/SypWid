package sypztep.sypwid.client.util;

import com.google.common.collect.Lists;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Comparator;

import static net.minecraft.item.ArmorMaterials.*;
import static net.minecraft.item.ArmorMaterials.NETHERITE;

/**
 * Utility class containing various static Comparator implementations for sorting ItemStacks based on different criteria.
 */
public class SortType {

    // ---------- Item Type Comparators ----------

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

    // ---------- Attribute Comparators ----------

    /**
     * Comparator to compare ItemStacks based on the damage value (ascending order).
     */
    public static final Comparator<ItemStack> DAMAGE = Comparator.comparingInt(ItemStack::getDamage);

    /**
     * Comparator to compare ItemStacks based on the count (descending order).
     */
    public static final Comparator<ItemStack> COUNT = Comparator.comparingInt(ItemStack::getCount).reversed();

    // ---------- Category Comparators ----------

    /**
     * Comparator to place ToolItems based on their material strength, from lowest to highest.
     */
    public static final Comparator<ItemStack> TOOLS = (lhs, rhs) -> {
        ToolItem leftTool = lhs.getItem() instanceof ToolItem ? (ToolItem) lhs.getItem() : null;
        ToolItem rightTool = rhs.getItem() instanceof ToolItem ? (ToolItem) rhs.getItem() : null;

        // Handle cases where either left or right ItemStack is not a ToolItem
        if (leftTool == null && rightTool == null) return 0;
        if (leftTool == null) return 1;
        if (rightTool == null) return -1;

        // Compare ToolMaterials based on their strength index
        int leftMaterial = getToolIndex(leftTool.getMaterial());
        int rightMaterial = getToolIndex(rightTool.getMaterial());

        return Integer.compare(leftMaterial, rightMaterial);
    };

    /**
     * Comparator to place ArmorItems based on their material strength, from lowest to highest.
     */
    public static final Comparator<ItemStack> ARMOR = (lhs, rhs) -> {
        ArmorItem leftArmor = lhs.getItem() instanceof ArmorItem ? (ArmorItem) lhs.getItem() : null;
        ArmorItem rightArmor = rhs.getItem() instanceof ArmorItem ? (ArmorItem) rhs.getItem() : null;

        // Handle cases where either left or right ItemStack is not an ArmorItem
        if (leftArmor == null && rightArmor == null) return 0;
        if (leftArmor == null) return 1;
        if (rightArmor == null) return -1;

        // Compare ArmorMaterials based on their strength index
        int leftCritChance = getArmorIndex(leftArmor.getMaterial());
        int rightCritChance = getArmorIndex(rightArmor.getMaterial());

        return Integer.compare(leftCritChance, rightCritChance);
    };

    public static Comparator<ItemStack> itemGroupOrder(Identifier id)
    {
        return (lhs, rhs) ->
        {
            ItemGroup group = Registries.ITEM_GROUP.get(id);

            if (group == null)
            {
                return 0;
            }

            ItemStack[] arr = group.getSearchTabStacks().toArray(new ItemStack[0]);
            var foo = Lists.newArrayList(group.getSearchTabStacks());

            int l = 0;
            int r = 0;

            for (int i = 0; i < foo.size(); ++i)
            {
                Item item = foo.get(i).getItem();

                if (lhs.isOf(item))
                {
                    l = i;
                }
                if (rhs.isOf(item))
                {
                    r = i;
                }
            }

            return l - r;
        };
    }
    // ---------- Helper Methods ----------

    private static int getToolIndex(ToolMaterial toolMaterial) {
        if (toolMaterial.equals(ToolMaterials.WOOD)) {
            return 5;
        } else if (toolMaterial.equals(ToolMaterials.STONE)) {
            return 4;
        } else if (toolMaterial.equals(ToolMaterials.IRON)) {
            return 3;
        } else if (toolMaterial.equals(ToolMaterials.GOLD)) {
            return 2;
        } else if (toolMaterial.equals(ToolMaterials.DIAMOND)) {
            return 1;
        } else if (toolMaterial.equals(ToolMaterials.NETHERITE)) {
            return 0;
        } else return -999;
    }

    private static int getArmorIndex(RegistryEntry<ArmorMaterial> armorMaterial) {
        if (armorMaterial.equals(LEATHER)) {
            return 5;
        } else if (armorMaterial.equals(IRON)) {
            return 4;
        } else if (armorMaterial.equals(GOLD)) {
            return 3;
        } else if (armorMaterial.equals(CHAIN)) {
            return 2;
        } else if (armorMaterial.equals(DIAMOND)) {
            return 1;
        } else if (armorMaterial.equals(NETHERITE)) {
            return 0;
        } else return -999;
    }
}