package net.wobba.item;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.wobba.Kitpvp;
import net.wobba.classSystem.playerClasses.undying.Aegis;
import net.wobba.classSystem.playerClasses.undying.Gambit;
import net.wobba.classSystem.playerClasses.undying.Paralysis;
import net.wobba.classSystem.playerClasses.undying.Rupture;
import net.wobba.classSystem.playerClasses.warlock.EldritchBlast;
import net.wobba.classSystem.playerClasses.warlock.PowerWordKill;
import net.wobba.classSystem.playerClasses.warlock.SummonLesserDemon;
import net.wobba.classSystem.playerClasses.warlock.PactBoon;
import net.wobba.classSystem.playerClasses.test.TestPassive;
import net.wobba.classSystem.playerClasses.test.SummonFireball;

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

    // Test abilities
    public static final Item SUMMON_FIREBALL = register("summon_fireball", SummonFireball::new, new Item.Properties());
    public static final Item REGEN_PASSIVE = register("regen_passive", TestPassive::new, new Item.Properties());

    // Warlock items
    public static final Item PACT_BOON = register("pact_boon", PactBoon::new, new Item.Properties());
    public static final Item ELDRITCH_BLAST = register("eldritch_blast", EldritchBlast::new, new Item.Properties());
    public static final Item SUMMON_LESSER_DEMON = register("summon_lesser_demon", SummonLesserDemon::new, new Item.Properties());
    public static final Item POWER_WORD_KILL = register("power_word_kill", PowerWordKill::new, new Item.Properties());
    public static final Item RITUAL_DAGGER = register("ritual_dagger", Item::new, new Item.Properties().sword(ToolMaterial.DIAMOND, 1f, -3f).component(DataComponents.UNBREAKABLE, Unit.INSTANCE));

    // Undying items
    public static final Item AEGIS_PASSIVE = register("aegis_passive", Aegis::new, new Item.Properties());
    public static final Item PARALYSIS = register("paralysis", Paralysis::new, new Item.Properties());
    public static final Item RUPTURE = register("rupture", Rupture::new, new Item.Properties());
    public static final Item GAMBIT = register("gambit", Gambit::new, new Item.Properties());
    public static final Item SOUL_BLADE = register("soul_blade", Item::new, new Item.Properties().sword(ToolMaterial.DIAMOND, 1f, -3f).component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
    );
}
