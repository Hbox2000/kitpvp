package net.wobba.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class MenuClickMixin {

    @Inject(
            method = "drop(Z)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void preventDrop(boolean bl, CallbackInfo ci) {
        ci.cancel();
    }
}