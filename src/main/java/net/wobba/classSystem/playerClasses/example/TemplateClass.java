package net.wobba.classSystem.playerClasses.example;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.wobba.classSystem.playerClasses.classSystem.KitClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemplateClass extends KitClass {

    // IMPORTANT: Class will not show up in game unless you register it in ClassManager.

    @Override
    public String getName() {
        // IMPORTANT: Class will not show up in menu if its name isn't unique.
        return "TemplateClass";
    }

    @Override
    public List<ItemStack> getItems(ServerPlayer player) {
        List<ItemStack> items = new ArrayList<>();

        // This function is the items for the display preview GUI and the players hotbar when the class is selected.
        // Add items in order of what slot you want them to appear in
        // Add items here to the list of items called items:
        // e.g. items.add(new ItemStack(Items.ARROW, 32))

        // If you want to enchant items use the function:
        // enchantItems({ItemStack you want to enchant}, {Enchantment you want on the item}, {Level of enchantment}, player)
        // e.g. items.add(enchantItem((new ItemStack(Items.CROSSBOW)), Enchantments.MULTISHOT, 1, player));



        return items;
    }

    @Override
    public ItemStack getIcon() {
        // What you want the icon for the class to be in the menus.
        ItemStack icon = new ItemStack(Items.OAK_SIGN);

        // Change the name and color of the icon in the menus.
        icon.set(DataComponents.CUSTOM_NAME, Component.literal("Template Class").withStyle(ChatFormatting.GOLD));

        // Change the description and color of the icon in the menus.
        icon.set(DataComponents.LORE, new ItemLore(List.of(
                Component.literal("Template class to be copied to make new classes").withStyle(ChatFormatting.GRAY)
        )));

        return icon;
    }

    @Override
    public ItemStack[] getArmor() {

        // The armor you want the class to be wearing.
        // IMPORTANT: Make sure you add the armor in order from head to toe.
        // IMPORTANT: Make sure all 4 slots have something in them, or it will not work, if you want there to be nothing in a slot, use Items.AIR.
        return new ItemStack[]{
                new ItemStack(Items.AIR), // Helmet
                new ItemStack(Items.AIR), // Chestplate
                new ItemStack(Items.AIR), // Leggings
                new ItemStack(Items.AIR) // Boots
        };
    }

    @Override
    public ItemStack getOffhand() {
        // The offhand slot of the class.
        // IMPORTANT: Make sure there is Items.AIR here if you want nothing.
        return new ItemStack(Items.AIR);
    }

    @Override
    public void applyStats(Player player) {
        // Any attributes you want the class to have changed.
        // Attributes will be automatically reset when changing class so don't worry about that.

        // Makes the player faster than normal.
        Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.15);

        // Set max health
        Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(16.0);
        // Updates the players health
        player.setHealth(16.0f);
    }
}