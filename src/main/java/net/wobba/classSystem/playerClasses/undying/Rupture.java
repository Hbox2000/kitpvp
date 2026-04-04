package net.wobba.classSystem.playerClasses.undying;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.wobba.classSystem.abilitySystem.AbilityItem;

import java.util.Optional;

public class Rupture extends AbilityItem {

    private static final double RANGE = 5.0;
    private static final float MIN_DAMAGE = 4.0f;  // 3 stacks
    private static final float MAX_DAMAGE = 12.0f; // 0 stacks

    public Rupture(Properties properties) {
        super(properties, 360); // 18 second cooldown
    }

    @Override
    public boolean onUse(ServerPlayer player) {
        LivingEntity target = findTarget(player);
        if (target == null) return false;

        int stacks = AegisManager.getStacks(player);
        int missingStacks = 3 - stacks;

        // Scale damage with missing stacks
        float damage = MIN_DAMAGE + (MAX_DAMAGE - MIN_DAMAGE) * (missingStacks / 3.0f);

        // Armor piercing damage
        target.hurt(player.damageSources().magic(), damage);

        // Debuffs on self
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 0, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, false, true));

        player.level().playSound(null, player.blockPosition(),
                SoundEvents.WITHER_HURT, SoundSource.PLAYERS, 1.0f, 0.5f);

        return true;
    }

    private LivingEntity findTarget(ServerPlayer player) {
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(RANGE));
        AABB box = player.getBoundingBox().expandTowards(
                player.getLookAngle().scale(RANGE)).inflate(1.0);

        LivingEntity target = null;
        double closestDist = RANGE;

        for (Entity entity : player.level().getEntities(player, box)) {
            if (!(entity instanceof LivingEntity living)) continue;
            AABB entityBox = entity.getBoundingBox().inflate(0.3);
            Optional<Vec3> hit = entityBox.clip(start, end);
            if (hit.isPresent()) {
                double dist = start.distanceTo(hit.get());
                if (dist < closestDist) {
                    closestDist = dist;
                    target = living;
                }
            }
        }
        return target;
    }
}
