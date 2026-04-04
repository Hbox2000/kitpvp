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

public class Paralysis extends AbilityItem {

    private static final double RANGE = 6.0;

    public Paralysis(Properties properties) {
        super(properties, 60); // 3 second cooldown base, scales with stacks
    }

    @Override
    public boolean onUse(ServerPlayer player) {
        int stacks = AegisManager.getStacks(player);
        if (stacks <= 0) return false;

        // Find target in range
        LivingEntity target = findTarget(player);
        if (target == null) return false;

        int durationTicks = stacks * 20; // 1 second per stack

        target.addEffect(new MobEffectInstance(
                MobEffects.SLOWNESS, durationTicks, 1, false, true));
        target.addEffect(new MobEffectInstance(
                MobEffects.MINING_FATIGUE, durationTicks, 0, false, true));

        player.level().playSound(null, player.blockPosition(),
                SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.PLAYERS, 0.5f, 1.5f);

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