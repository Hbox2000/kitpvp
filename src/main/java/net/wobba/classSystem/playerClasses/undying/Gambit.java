package net.wobba.classSystem.playerClasses.undying;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.wobba.classSystem.abilitySystem.AbilityItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Gambit extends AbilityItem {

    private static final float HEALTH_REDUCTION = 9.0f; // 4.5 hearts
    private static final int GAMBIT_DURATION = 200; // 10 seconds
    private static final Map<UUID, Long> gambitActive = new HashMap<>();

    public Gambit(Properties properties) {
        super(properties, 400); // 20 second cooldown
    }

    @Override
    public boolean onUse(ServerPlayer player) {
        // Haste 4 for 3 seconds
        player.addEffect(new MobEffectInstance(MobEffects.HASTE, 60, 3, false, true));

        // Regain 1 Aegis stack immediately
        AegisManager.addStack(player);

        // Reduce max health
        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        float currentMax = (float) maxHealth.getBaseValue();
        float newMax = Math.max(2.0f, currentMax - HEALTH_REDUCTION); // min 1 heart
        maxHealth.setBaseValue(newMax);

        // Clamp current health
        if (player.getHealth() > newMax) {
            player.setHealth(newMax);
        }

        // Schedule health restoration after 10 seconds
        gambitActive.put(player.getUUID(), player.level().getGameTime() + GAMBIT_DURATION);

        player.level().playSound(null, player.blockPosition(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0f, 1.5f);

        return true;
    }

    public static void tick(ServerPlayer player) {
        Long expireTime = gambitActive.get(player.getUUID());
        if (expireTime == null) return;

        if (player.level().getGameTime() >= expireTime) {
            // Restore max health
            AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            maxHealth.setBaseValue(Math.min(10.0f, maxHealth.getBaseValue() + HEALTH_REDUCTION));
            gambitActive.remove(player.getUUID());
        }
    }

    public static void clearPlayer(UUID uuid) {
        gambitActive.remove(uuid);
    }
}