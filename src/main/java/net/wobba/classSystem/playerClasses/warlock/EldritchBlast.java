package net.wobba.classSystem.playerClasses.warlock;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.phys.Vec3;
import net.wobba.classSystem.playerClasses.abilitySystem.AbilityItem;

public class EldritchBlast extends AbilityItem {

    public EldritchBlast(Properties properties) {
        super(properties, 120); // 6 second cooldown
    }

    @Override
    public boolean onUse(ServerPlayer player) {
        Vec3 look = player.getLookAngle();

        WitherSkull skull = new WitherSkull(player.level(), player, look);

        skull.setPos(
                player.getX() + look.x,
                player.getEyeY() + look.y,
                player.getZ() + look.z
        );

        player.level().addFreshEntity(skull);
        player.level().playSound(null, player.blockPosition(),
                SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f);

        return true;
    }
}