package net.wobba.classSystem;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class KitClass {

    public abstract String getName();
    public abstract List<ItemStack> getDisplayItems(); // items shown in preview
    public abstract ItemStack getIcon(); // the item shown in the GUI
    public abstract ItemStack[] getArmor();      // helmet, chestplate, leggings, boots
    public abstract ItemStack getOffhand();
    public abstract ItemStack[] getItems();      // items to put in hotbar
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
        ItemStack[] items = getItems();
        for (int i = 0; i < items.length; i++) {
            player.getInventory().setItem(i, items[i]);
        }

        // Apply stats
        applyStats(player);
    }
}