package net.wobba.classSystem.abilityItems.warlock;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.wobba.classSystem.abilityItems.AbilityItem;

import java.util.UUID;

public class SummonLesserDemon extends AbilityItem {

    public SummonLesserDemon(Properties properties) {
        super(properties, 640); // 32 second cooldown
    }

    @Override
    public boolean onUse(ServerPlayer player) {
        ServerLevel level = (ServerLevel) player.level();

        WitherSkeleton demon = new WitherSkeleton(EntityType.WITHER_SKELETON, level) {
            private final UUID ownerUUID = player.getUUID();
            private int lifetimeTicks = 0;
            private static final int MAX_LIFETIME = 300; // 15 seconds

            @Override
            public void tick() {
                super.tick();
                lifetimeTicks++;
                if (lifetimeTicks >= MAX_LIFETIME) {
                    this.discard();
                }
            }

            @Override
            public boolean canAttack(LivingEntity target) {
                // Attack anyone except owner
                if (target.getUUID().equals(ownerUUID)) return false;
                return super.canAttack(target);
            }
        };

        // Position in front of player
        demon.setPos(player.getX() + player.getLookAngle().x * 2,
                player.getY(), player.getZ() + player.getLookAngle().z * 2);

        // 5 hearts
        demon.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0);
        demon.setHealth(10.0f);

        // High speed
        demon.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4);

        // Very long follow range so it never loses track
        demon.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(256.0);

        level.addFreshEntity(demon);
        player.level().playSound(null, player.blockPosition(),
                SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 0.5f, 1.5f);

        return true;
    }
}
