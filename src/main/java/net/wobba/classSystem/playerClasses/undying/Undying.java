package net.wobba.classSystem.playerClasses.undying;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.wobba.classSystem.classManagementSystem.KitClass;
import net.wobba.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class Undying extends KitClass {

    @Override
    public String getName() { return "Undying"; }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY
        };
    }

    @Override
    public ItemStack getOffhand() {
        return ItemStack.EMPTY;
    }

    @Override
    public List<ItemStack> getItems(ServerPlayer player) {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(ModItems.SOUL_BLADE));
        items.add(new ItemStack(ModItems.PARALYSIS));
        items.add(new ItemStack(ModItems.RUPTURE));
        items.add(new ItemStack(ModItems.GAMBIT));
        items.add(new ItemStack(Items.AIR));
        items.add(new ItemStack(Items.AIR));
        items.add(new ItemStack(Items.AIR));
        items.add(new ItemStack(Items.AIR));
        items.add(new ItemStack(ModItems.AEGIS_PASSIVE));
        return items;
    }

    @Override
    public void applyStats(Player player) {
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0); // 5 hearts
        player.setHealth(10.0f);
        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1);
    }
}
