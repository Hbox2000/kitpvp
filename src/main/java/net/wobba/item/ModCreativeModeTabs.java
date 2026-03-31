package net.wobba.item;


import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.wobba.Kitpvp;

public class ModCreativeModeTabs {
    public static void initialize() {}

    public static final CreativeModeTab CUSTOM_CREATIVE_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(Kitpvp.MOD_ID, "creative_tab"),
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(Items.WAXED_OXIDIZED_COPPER_GOLEM_STATUE))
                .title(Component.translatable("itemGroup.kitpvp"))
                .displayItems((params, output) -> {
                    output.accept(ModItems.SUMMON_FIREBALL);
                })
                .build()
            );
}
