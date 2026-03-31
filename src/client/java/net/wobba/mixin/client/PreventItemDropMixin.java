package net.wobba.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class PreventItemDropMixin {

    @Inject(
            method = "drop(Z)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void preventQDrop(boolean bl, CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer)(Object) this;
        if (!player.isCreative()) {  // allow drop in creative
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}