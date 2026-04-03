package net.wobba.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.wobba.classSystem.abilityItems.PassiveAbilityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class InventoryChangeMixin {

    @Inject(method = "setItem", at = @At("HEAD"))
    private void onSetItem(int slot, ItemStack newStack, CallbackInfo ci) {
        Inventory inventory = (Inventory)(Object) this;
        if (!(inventory.player instanceof ServerPlayer player)) return;

        ItemStack oldStack = inventory.getItem(slot);

        // Item removed or replaced
        if (oldStack.getItem() instanceof PassiveAbilityItem ability) {
            ability.onUnequip(player);
        }

        // Item added
        if (newStack.getItem() instanceof PassiveAbilityItem ability) {
            ability.onEquip(player);
        }
    }
}
