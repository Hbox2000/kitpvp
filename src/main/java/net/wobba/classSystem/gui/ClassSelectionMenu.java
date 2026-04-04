package net.wobba.classSystem.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.wobba.ModMenuTypes;
import net.wobba.classSystem.playerClasses.classSystem.ClassManager;
import net.wobba.classSystem.playerClasses.classSystem.KitClass;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ClassSelectionMenu extends AbstractContainerMenu {

    public ClassSelectionMenu(int syncId, Inventory playerInventory) {
        super(ModMenuTypes.CLASS_SELECTION, syncId);

        int slotIndex = 0;
        for (KitClass kitClass : ClassManager.getClasses().values()) {
            int row = slotIndex / 9;
            int col = slotIndex % 9;
            this.addSlot(new ClassSlot(kitClass.getIcon(), col * 18 + 8, row * 18 + 18));
            slotIndex++;
        }
    }

    @Override
    public void clicked(int slotId, int button, @NonNull ClickType clickType, @NonNull Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        if (slotId < 0) return;

        List<KitClass> classes = new ArrayList<>(ClassManager.getClasses().values());
        if (slotId < classes.size()) {
            KitClass selected = classes.get(slotId);
            ClassManager.setPreviewClass(serverPlayer, selected);
            serverPlayer.closeContainer();
            serverPlayer.level().getServer().execute(() ->
                    serverPlayer.openMenu(new SimpleMenuProvider(
                            (syncId, inv, p) -> new ClassPreviewMenu(syncId, selected, selected.getItems(serverPlayer)),
                            Component.literal(selected.getName())
                    ))
            );
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
