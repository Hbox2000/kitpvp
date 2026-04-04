package net.wobba.mixin.characterMixins;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.wobba.classSystem.playerClasses.undying.AegisManager;
import net.wobba.classSystem.playerClasses.undying.Aegis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class AegisKillMixin {

    @Inject(method = "awardKillScore", at = @At("HEAD"))
    private void onKill(Entity entity, DamageSource source, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer)(Object) this;
        if (!(entity instanceof Player)) return;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i).getItem() instanceof Aegis) {
                AegisManager.addStack(player);
                return;
            }
        }
    }
}
