package net.wobba.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class PreventInventoryDropMixin {

    @Shadow
    public abstract ItemStack getCarried();

    @Inject(
            method = "doClick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void preventInventoryDrop(int slotId, int button, ClickType clickType, Player player, CallbackInfo ci) {
        if (player.isCreative()) return;

        // Only cancel THROW click type (Q key)
        if (clickType == ClickType.THROW) {
            ci.cancel();
            return;
        }

        // Only cancel outside clicks (i == -999) when its a PICKUP action with an item being carried
        if (slotId == -999
                && clickType == ClickType.PICKUP  // must be a pickup click
                && !this.getCarried().isEmpty()) { // must be carrying an item
            ci.cancel();
        }
    }
}