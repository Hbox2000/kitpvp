package net.wobba.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.wobba.classSystem.classManagementSystem.ClassManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class PlayerRespawnMixin {

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void onRespawn(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        ClassManager.reapplyOnRespawn((ServerPlayer)(Object) this);
    }
}
