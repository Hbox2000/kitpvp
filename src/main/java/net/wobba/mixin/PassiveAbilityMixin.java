package net.wobba.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.wobba.classSystem.playerClasses.abilitySystem.PassiveAbilityItem;
import net.wobba.classSystem.playerClasses.abilitySystem.PassiveTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(ServerPlayer.class)
public class PassiveAbilityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer)(Object) this;

        Set<PassiveAbilityItem> found = new HashSet<>();

        // Check inventory
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof PassiveAbilityItem passive) {
                found.add(passive);
                if (passive.isEquipped) {
                    passive.onTick(player);
                } else {
                    passive.onEquip(player);
                }
            }
        }

        // Check cursor item
        ItemStack cursor = player.containerMenu.getCarried();
        if (cursor.getItem() instanceof PassiveAbilityItem passive) {
            found.add(passive);
            if (passive.isEquipped) {
                passive.onTick(player);
            } else {
                passive.onEquip(player);
            }
        }

        // Check crafting slots
        for (int i = 0; i < player.inventoryMenu.slots.size(); i++) {
            ItemStack stack = player.inventoryMenu.slots.get(i).getItem();
            if (stack.getItem() instanceof PassiveAbilityItem passive) {
                found.add(passive);
                if (passive.isEquipped) {
                    passive.onTick(player);
                } else {
                    passive.onEquip(player);
                }
            }
        }

        // Unequip any passives that are equipped but not found in inventory
        PassiveTracker.getActivePassives(player.getUUID()).stream()
                .filter(passive -> !found.contains(passive))
                .forEach(passive -> passive.onUnequip(player));

        // Update tracker with currently found passives
        PassiveTracker.setActivePassives(player.getUUID(), found);
    }
}