package net.wobba.mixin.characterMixins;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.wobba.classSystem.playerClasses.undying.AegisManager;
import net.wobba.classSystem.playerClasses.undying.Aegis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class AegisDamageMixin {

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void onHurt(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayer player = (ServerPlayer)(Object) this;

        // Check if player has Aegis passive
        boolean hasAegis = false;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i).getItem() instanceof Aegis) {
                hasAegis = true;
                break;
            }
        }
        if (!hasAegis) return;

        // Aegis does not block poison or fire
        if (source.is(DamageTypes.ON_FIRE/*needs to be poison cant work out what its called*/) || source.is(DamageTypes.ON_FIRE)
                || source.is(DamageTypes.IN_FIRE) || source.is(DamageTypes.CAMPFIRE)) return;

        int stacks = AegisManager.getStacks(player);
        if (stacks > 0) {
            AegisManager.removeStack(player);
            AegisManager.recordDamage(player);
            // Spawn totem-like effect to show stack consumed
            level.broadcastEntityEvent(player, (byte) 35);
            cir.setReturnValue(false); // cancel the damage
            cir.cancel();
        } else {
            AegisManager.recordDamage(player);
        }
    }
}
