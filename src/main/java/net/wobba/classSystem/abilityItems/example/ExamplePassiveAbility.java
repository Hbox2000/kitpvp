package net.wobba.classSystem.abilityItems.example;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.wobba.classSystem.abilityItems.PassiveAbilityItem;

public class ExamplePassiveAbility extends PassiveAbilityItem {

    static boolean hasPactBoon = false;

    public ExamplePassiveAbility(Properties properties) {
        super(properties);
    }

    // Runs when the passive item enters the players inventory
    @Override
    public void onEquip(ServerPlayer player) {
    }

    // Runs when the passive item exits the players inventory
    @Override
    public void onUnequip(ServerPlayer player) {
    }

    // Runs every tick while the player has the item
    @Override
    public void onTick(ServerPlayer player) {
    }

    // This item is a lil more complicated to make work than the active so if you need help ask me
    // Mostly just bc there isn't much more I can add to this template to make it easier
}
