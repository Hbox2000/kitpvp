package net.wobba.classSystem.playerClasses;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.wobba.classSystem.KitClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Archer extends KitClass {

    @Override
    public String getName() { return "Archer"; }

    @Override
    public List<ItemStack> getDisplayItems() {
        List<ItemStack> items = new ArrayList<>();

        // Add items with tooltips as ability descriptions
        items.add(new ItemStack(Items.BOW));

        items.add(new ItemStack(Items.ARROW, 64));

        items.add(new ItemStack(Items.AMETHYST_SHARD, 32));
        return items;
    }

    @Override
    public ItemStack getIcon() {
        ItemStack icon = new ItemStack(Items.BOW);
        icon.set(DataComponents.CUSTOM_NAME, Component.literal("Archer").withStyle(ChatFormatting.GOLD));
        icon.set(DataComponents.LORE, new ItemLore(List.of(
                Component.literal("Avoid enemies with speed and fire from a distance").withStyle(ChatFormatting.GRAY)
        )));
        return icon;
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Items.LEATHER_HELMET),
                new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS),
                new ItemStack(Items.LEATHER_BOOTS)
        };
    }

    @Override
    public ItemStack getOffhand() {
        return new ItemStack(Items.SHIELD);
    }

    @Override
    public ItemStack[] getItems() {
        ItemStack bow = new ItemStack(Items.BOW);
        ItemStack arrows = new ItemStack(Items.ARROW, 64);
        return new ItemStack[]{ bow, arrows };
    }

    @Override
    public void applyStats(Player player) {
        // Apply speed boost
        Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.15);
        // Set max health
        Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(16.0); // 8 hearts
        player.setHealth(16.0f);
    }
}