package net.wobba.classSystem.gui;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class LockedSlot extends Slot {

    public LockedSlot(ItemStack stack, int x, int y) {
        super(new SimpleContainer(1), 0, x, y);
        this.container.setItem(0, stack);
    }

    @Override
    public boolean mayPickup(@NonNull Player player) {
        return false;
    }

    @Override
    public boolean mayPlace(@NonNull ItemStack stack) {
        return false;
    }
}
