package net.wobba.classSystem.abilityItems.test;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.phys.Vec3;
import net.wobba.classSystem.abilityItems.AbilityItem;

public class SummonFireball extends AbilityItem {
    public SummonFireball(Properties properties) {
        super(properties, 40);
    }

    @Override
    public boolean onUse(ServerPlayer player) {

        BlockPos frontOfPlayer = player.blockPosition().relative(player.getDirection(), 2);
        Vec3 lookVector = player.getLookAngle();

        SmallFireball fireball = new SmallFireball(player.level(), player, lookVector);

        fireball.setPos(
                player.getX() + lookVector.x,
                player.getEyeY() + lookVector.y,
                player.getZ() + lookVector.z
        );

        player.level().addFreshEntity(fireball);

        player.level().playSound(null, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0f, 1.0f);

        return true;
    }
}
