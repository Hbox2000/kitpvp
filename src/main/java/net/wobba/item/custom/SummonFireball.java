package net.wobba.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SummonFireball extends Item {
    public SummonFireball(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {

        if (level.isClientSide()) {
            return InteractionResult.PASS;
        }

        BlockPos frontOfPlayer = player.blockPosition().relative(player.getDirection(), 2);
        Vec3 lookVector = player.getLookAngle();

        SmallFireball fireball = new SmallFireball(level, player, lookVector);

        fireball.setPos(
                player.getX() + lookVector.x,
                player.getEyeY() + lookVector.y,
                player.getZ() + lookVector.z
        );

        level.addFreshEntity(fireball);

        level.playSound(null, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0f, 1.0f);

        return InteractionResult.SUCCESS;
    }
}
