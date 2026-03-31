package net.wobba.classSystem.gui;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class ClassSlot extends Slot {

    private final ItemStack icon;

    public ClassSlot(ItemStack icon, int x, int y) {
        super(new SimpleContainer(1), 0, x, y);
        this.icon = icon;
        this.container.setItem(0, icon);
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
