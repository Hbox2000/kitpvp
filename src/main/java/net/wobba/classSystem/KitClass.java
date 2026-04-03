package net.wobba.classSystem;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public abstract class KitClass {

    public abstract String getName();
    public abstract List<ItemStack> getItems(ServerPlayer player); // items shown in preview
    public abstract ItemStack getIcon(); // the item shown in the GUI and added to the hotbar
    public abstract ItemStack[] getArmor();      // helmet, chestplate, leggings, boots
    public abstract ItemStack getOffhand();
    public abstract void applyStats(Player player); // speed, health etc

    // Called when player receives the class
    public void apply(ServerPlayer player) {
        // Clear inventory first
        player.getInventory().clearContent();

        // Apply armor
        ItemStack[] armor = getArmor();
        player.setItemSlot(EquipmentSlot.HEAD, armor[0]);
        player.setItemSlot(EquipmentSlot.CHEST, armor[1]);
        player.setItemSlot(EquipmentSlot.LEGS, armor[2]);
        player.setItemSlot(EquipmentSlot.FEET, armor[3]);

        // Apply offhand
        ItemStack offhand = getOffhand();
        player.setItemSlot(EquipmentSlot.OFFHAND, offhand);

        // Apply items
        List<ItemStack> items = getItems(player);
        for (int i = 0; i < items.size(); i++) {
            player.getInventory().setItem(i, items.get(i));
        }

        // Apply stats
        applyStats(player);
    }

    // Class to enchant an item OH MY FUCKING GOD THIS WAS SO BAD TO FIGURE OUT
    public static ItemStack enchantItem(ItemStack item, ResourceKey<Enchantment> enchantment, int level, ServerPlayer player)
    {
        Holder<Enchantment> holder = player.level().registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(enchantment);
        item.enchant(holder, level);

        return item;
    }
}