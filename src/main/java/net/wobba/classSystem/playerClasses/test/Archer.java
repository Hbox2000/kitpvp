package net.wobba.classSystem.playerClasses.test;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.enchantment.Enchantments;
import net.wobba.classSystem.playerClasses.classSystem.KitClass;
import net.wobba.item.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Archer extends KitClass {

    @Override
    public String getName() { return "Archer"; }

    @Override
    public List<ItemStack> getItems(ServerPlayer player) {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Items.BOW));
        items.add(new ItemStack(Items.ARROW, 64));
        ItemStack stack = new ItemStack(Items.CROSSBOW);
        enchantItem(stack, Enchantments.MULTISHOT, 1, player);

        items.add(new ItemStack(ModItems.SUMMON_FIREBALL));
        items.add(new ItemStack(ModItems.PACT_BOON));
        items.add(stack);

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
    public void applyStats(Player player) {
        // Apply speed boost
        Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.15);
        // Set max health
        Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(16.0); // 8 hearts
        player.setHealth(16.0f);
    }
}