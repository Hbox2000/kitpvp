package net.wobba.classSystem.playerClasses.undying;

import net.minecraft.server.level.ServerPlayer;
import net.wobba.classSystem.abilitySystem.PassiveAbilityItem;

public class Aegis extends PassiveAbilityItem {

    public Aegis(Properties properties) {
        super(properties);
    }

    @Override
    public void onEquip(ServerPlayer player) {
        isEquipped = true;
        AegisManager.setStacks(player, 3);
    }

    @Override
    public void onUnequip(ServerPlayer player) {
        isEquipped = false;
        AegisManager.setStacks(player, 0);
        AegisManager.clearPlayer(player.getUUID());
    }

    @Override
    public void onTick(ServerPlayer player) {
        AegisManager.tick(player);
    }
}
