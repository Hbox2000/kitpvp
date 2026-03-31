package net.wobba.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.Item;
import net.wobba.Kitpvp;
import net.wobba.item.custom.SummonFireball;

import java.util.function.Function;

public class ModItems {
    public static void initialize() {

    }

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {

        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Kitpvp.MOD_ID, name)); // kitpvp:fireball

        T item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static final Item SUMMON_FIREBALL = register("summon_fireball", SummonFireball::new, new Item.Properties().useCooldown(2));
}
