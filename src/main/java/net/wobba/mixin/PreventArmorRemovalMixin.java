package net.wobba.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public class PreventArmorRemovalMixin {

    @Inject(method = "doClick", at = @At("HEAD"), cancellable = true)
    private void preventArmorRemoval(int slotId, int button, ClickType clickType, Player player, CallbackInfo ci) {
        if (player.isCreative()) return;
        if (!(player instanceof ServerPlayer)) return;

        // Armor slots in the player inventory are slots 5-8
        if (slotId >= 5 && slotId <= 8) {
            ci.cancel();
        }
    }
}