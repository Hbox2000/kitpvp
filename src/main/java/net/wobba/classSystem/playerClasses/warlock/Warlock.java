package net.wobba.classSystem.playerClasses.warlock;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.TooltipDisplay;
import net.wobba.classSystem.classManagementSystem.KitClass;
import net.wobba.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class Warlock extends KitClass {


    @Override
    public String getName() { return "Warlock"; }

    @Override
    public List<ItemStack> getItems(ServerPlayer player) {
        List<ItemStack> items = new ArrayList<>();

        items.add(new ItemStack(ModItems.RITUAL_DAGGER));
        items.add(new ItemStack(ModItems.ELDRITCH_BLAST));
        items.add(new ItemStack(ModItems.SUMMON_LESSER_DEMON));
        items.add(new ItemStack(ModItems.POWER_WORD_KILL));
        items.add(new ItemStack(Items.AIR));
        items.add(new ItemStack(Items.AIR));
        items.add(new ItemStack(Items.AIR));
        items.add(new ItemStack(Items.AIR));
        items.add(new ItemStack(ModItems.PACT_BOON));



        return items;
    }

    @Override
    public ItemStack getIcon() {

        ItemStack icon = new ItemStack(ModItems.RITUAL_DAGGER);

        icon.set(DataComponents.TOOLTIP_DISPLAY,
                TooltipDisplay.DEFAULT
                        .withHidden(DataComponents.ATTRIBUTE_MODIFIERS, true)
                        .withHidden(DataComponents.WEAPON, true)
                        .withHidden(DataComponents.ATTACK_RANGE, true)
                        .withHidden(DataComponents.UNBREAKABLE, true)
                        .withHidden(DataComponents.ENCHANTMENTS, true)
                        .withHidden(DataComponents.RARITY, true)
        );

        icon.set(DataComponents.CUSTOM_NAME, Component.literal("Warlock").withStyle(ChatFormatting.GOLD));

        icon.set(DataComponents.LORE, new ItemLore(List.of(
                Component.literal("Get in close with short range slashes and powerful abilities").withStyle(ChatFormatting.GRAY)
        )));

        return icon;
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Items.SPAWNER), // Helmet
                new ItemStack(Items.LEATHER_CHESTPLATE), // Chestplate
                new ItemStack(Items.LEATHER_LEGGINGS), // Leggings
                new ItemStack(Items.LEATHER_BOOTS) // Boots
        };
    }

    @Override
    public ItemStack getOffhand() {
        return new ItemStack(Items.AIR);
    }

    @Override
    public void applyStats(Player player) {

    }
}