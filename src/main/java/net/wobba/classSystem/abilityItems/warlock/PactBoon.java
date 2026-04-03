package net.wobba.classSystem.abilityItems.warlock;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.wobba.classSystem.abilityItems.PassiveAbilityItem;

public class PactBoon extends PassiveAbilityItem {

    static boolean hasPactBoon = false;

    public PactBoon(Properties properties) {
        super(properties);
    }

    @Override
    public void onEquip(ServerPlayer player) {
        hasPactBoon = true;
    }

    @Override
    public void onUnequip(ServerPlayer player) {
        // Remove absorption if active
        player.removeEffect(MobEffects.ABSORPTION);

        hasPactBoon = false;
    }

    public void trigger(ServerPlayer player) {
        // Only trigger if player has the pact boon item

        if (!hasPactBoon) return;

        if (player.getCooldowns().isOnCooldown(new ItemStack(this))) return;

        player.addEffect(new MobEffectInstance(
                MobEffects.ABSORPTION,
                60,  // 3 seconds
                0,   // level 2 = 2 absorption hearts
                false,
                false)
        );

        player.getCooldowns().addCooldown(new ItemStack(this), 100);
    }
}
