package net.wobba.classSystem.abilityItems;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class PassiveAbilityItem extends Item implements PassiveAbility {

    public PassiveAbilityItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onTick(ServerPlayer player) {}

    @Override
    public void onEquip(ServerPlayer player) {}

    @Override
    public void onUnequip(ServerPlayer player) {}
}