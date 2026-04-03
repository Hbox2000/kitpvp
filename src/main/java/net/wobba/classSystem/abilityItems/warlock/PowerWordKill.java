package net.wobba.classSystem.abilityItems.warlock;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.wobba.classSystem.abilityItems.AbilityItem;

import java.util.Optional;

public class PowerWordKill extends AbilityItem {

    private static final double RANGE = 5.0; // slightly longer than melee
    private static final float DAMAGE_TO_TARGET = 12.0f; // 6 hearts
    private static final float DAMAGE_TO_SELF = 6.0f;    // 3 hearts, offset by passive = ~2 hearts

    public PowerWordKill(Properties properties) {
        super(properties, 360); // 18 second cooldown
    }

    @Override
    public boolean onUse(ServerPlayer player) {
        // Raycast to find target in range
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(RANGE));

        AABB box = player.getBoundingBox().expandTowards(
                player.getLookAngle().scale(RANGE)).inflate(1.0);

        LivingEntity target = null;
        double closestDist = RANGE;

        for (Entity entity : player.level().getEntities(player, box)) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (entity == player) continue;

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

        if (target == null) return false;

        // Damage target
        target.hurt(player.damageSources().magic(), DAMAGE_TO_TARGET);

        // Damage self
        player.hurt(player.damageSources().magic(), DAMAGE_TO_SELF);

        // Effects
        player.level().playSound(null, player.blockPosition(),
                SoundEvents.WITHER_DEATH, SoundSource.PLAYERS, 0.5f, 2.0f);

        return true;
    }
}
