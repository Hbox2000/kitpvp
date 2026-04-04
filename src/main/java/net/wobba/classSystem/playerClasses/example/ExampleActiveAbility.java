package net.wobba.classSystem.playerClasses.example;

import net.minecraft.server.level.ServerPlayer;
import net.wobba.classSystem.abilitySystem.AbilityItem;

public class ExampleActiveAbility extends AbilityItem {

    // The number is the cooldown in ticks.
    public ExampleActiveAbility(Properties properties) {
        super(properties, 120);
    }

    // This runs when someone right clicks holding the item.
    @Override
    public boolean onUse(ServerPlayer player) {
        // Make your ability here.

        // Return true if the ability works and false if it doesn't.
        // e.g. If there isn't a target in range for the ability return false so the ability doesn't go on cooldown
        // even though you didn't use it.
        return true;
    }
}