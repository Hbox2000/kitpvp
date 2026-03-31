package net.wobba;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.wobba.classSystem.gui.ClassPreviewMenu;
import net.wobba.classSystem.gui.ClassSelectionMenu;

public class ModMenuTypes {

    public static final MenuType<ClassSelectionMenu> CLASS_SELECTION = Registry.register(
            BuiltInRegistries.MENU,
            Identifier.fromNamespaceAndPath(Kitpvp.MOD_ID, "class_selection"),
            new MenuType<>(ClassSelectionMenu::new, FeatureFlags.VANILLA_SET)
    );

    public static final MenuType<ClassPreviewMenu> CLASS_PREVIEW = Registry.register(
            BuiltInRegistries.MENU,
            Identifier.fromNamespaceAndPath(Kitpvp.MOD_ID, "class_preview"),
            new MenuType<>((syncId, inv) -> {
                // Client reconstructs using the player's preview class
                return new ClassPreviewMenu(syncId, null);
            }, FeatureFlags.VANILLA_SET)
    );

    public static void initialize() {}
}
