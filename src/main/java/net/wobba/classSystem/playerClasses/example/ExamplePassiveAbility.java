package net.wobba.classSystem.playerClasses.example;

import net.minecraft.server.level.ServerPlayer;
import net.wobba.classSystem.playerClasses.abilitySystem.PassiveAbilityItem;

public class ExamplePassiveAbility extends PassiveAbilityItem {

    static boolean hasPactBoon = false;

    public ExamplePassiveAbility(Properties properties) {
        super(properties);
    }

    // Runs when the passive item enters the players inventory
    // IMPORTANT: Will not work if you remove isEquiped from either of the fucntions.
    @Override
    public void onEquip(ServerPlayer player) {
        isEquipped = true;
    }

    // Runs when the passive item exits the players inventory
    @Override
    public void onUnequip(ServerPlayer player) {
        isEquipped = false;
    }

    // Runs every tick while the player has the item
    @Override
    public void onTick(ServerPlayer player) {
    }

    // This item is a lil more complicated to make work than the active so if you need help ask me
    // Mostly just bc there isn't much more I can add to this template to make it easier
}
