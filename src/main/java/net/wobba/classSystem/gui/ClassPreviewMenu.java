package net.wobba.classSystem.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.wobba.ModMenuTypes;
import net.wobba.classSystem.classManagementSystem.ClassManager;
import net.wobba.classSystem.classManagementSystem.KitClass;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ClassPreviewMenu extends AbstractContainerMenu {

    private final KitClass kitClass;
    private static final int BACK_SLOT = 0;   // slot 1
    private static final int CONFIRM_SLOT = 8; // slot 9

    // Client-side constructor - called automatically by Fabric's menu factory
    public ClassPreviewMenu(int syncId, Inventory inv) {
        this(syncId, null, List.of());
    }

    public ClassPreviewMenu(int syncId, KitClass kitClass, List<ItemStack> displayItems) {
        super(ModMenuTypes.CLASS_PREVIEW, syncId);
        this.kitClass = kitClass;

        if (kitClass == null) {
            // Top row - 9 slots
            for (int i = 0; i < 9; i++) {
                this.addSlot(new LockedSlot(ItemStack.EMPTY, i * 18 + 8, 18));
            }
            // Bottom row - 9 slots
            for (int i = 0; i < 9; i++) {
                this.addSlot(new LockedSlot(ItemStack.EMPTY, i * 18 + 8, 36));
            }
            return;
        }

        ItemStack[] armor = kitClass.getArmor();

        // Slot 1 - Back button
        ItemStack back = new ItemStack(Items.RED_WOOL);
        back.set(DataComponents.CUSTOM_NAME, Component.literal("Back").withStyle(ChatFormatting.DARK_RED));
        this.addSlot(new LockedSlot(back, 8, 18));

        // Slot 2 - empty
        this.addSlot(new LockedSlot(ItemStack.EMPTY, 26, 18));

        // Slots 3-6 - armor (helmet, chestplate, leggings, boots)
        for (int i = 0; i < 4; i++) {
            this.addSlot(new LockedSlot(armor[i], 44 + i * 18, 18));
        }

        // Slot 7 - offhand
        this.addSlot(new LockedSlot(kitClass.getOffhand(), 116, 18));

        // Slot 8 - empty
        this.addSlot(new LockedSlot(ItemStack.EMPTY, 134, 18));

        // Slot 9 - Confirm button
        ItemStack confirm = new ItemStack(Items.LIME_WOOL);
        confirm.set(DataComponents.CUSTOM_NAME, Component.literal("Confirm").withStyle(ChatFormatting.GREEN));
        this.addSlot(new LockedSlot(confirm, 152, 18));

        // Slots 10-18 - hotbar (weapons and abilities)
        for (int i = 0; i < 9; i++) {
            ItemStack stack = i < displayItems.size() ? displayItems.get(i) : ItemStack.EMPTY;
            this.addSlot(new LockedSlot(stack, i * 18 + 8, 36));
        }
    }

    public KitClass getKitClass() {
        return kitClass;
    }

    @Override
    public void clicked(int slotId, int button, @NonNull ClickType clickType, @NonNull Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        if (slotId == BACK_SLOT) {
            serverPlayer.closeContainer();
            serverPlayer.level().getServer().execute(() ->
                    serverPlayer.openMenu(new SimpleMenuProvider(
                            (syncId, inv, p) -> new ClassSelectionMenu(syncId, inv),
                            Component.literal("Select Class")
                    ))
            );
        } else if (slotId == CONFIRM_SLOT) {
            ClassManager.setClass(serverPlayer, kitClass);
            serverPlayer.closeContainer();
        }
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return true;
    }
}
