package net.wobba.classSystem.abilityItems;

import net.minecraft.server.level.ServerPlayer;

public interface PassiveAbility {
    // Called every tick while the item is in the player's inventory
    void onTick(ServerPlayer player);

    // Called when the item enters the inventory
    void onEquip(ServerPlayer player);

    // Called when the item leaves the inventory
    void onUnequip(ServerPlayer player);
}