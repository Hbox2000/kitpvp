package net.wobba.classSystem.playerClasses.test;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.wobba.classSystem.playerClasses.abilitySystem.PassiveAbilityItem;

public class TestPassive extends PassiveAbilityItem {

    public TestPassive(Properties properties) {
        super(properties);
    }

    @Override
    public void onEquip(ServerPlayer player) {
        // Apply permanent regen effect when item enters inventory
        player.addEffect(new MobEffectInstance(
                MobEffects.REGENERATION,
                Integer.MAX_VALUE,  // infinite duration
                0,                  // level 1
                false,
                false               // hide particles
        ));

        isEquipped = true;
    }

    @Override
    public void onUnequip(ServerPlayer player) {
        // Remove regen when item leaves inventory
        player.removeEffect(MobEffects.REGENERATION);

        isEquipped = false;
    }
}