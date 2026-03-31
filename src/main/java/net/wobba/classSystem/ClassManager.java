package net.wobba.classSystem;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.wobba.classSystem.playerClasses.Archer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClassManager {

    // Temporary storage for preview data
    private static final Map<UUID, KitClass> PREVIEW_CLASSES = new HashMap<>();

    public static void setPreviewClass(ServerPlayer player, KitClass kitClass) {
        PREVIEW_CLASSES.put(player.getUUID(), kitClass);
    }

    public static void clearClass(ServerPlayer player) {
        PLAYER_CLASSES.remove(player.getUUID());
        PREVIEW_CLASSES.remove(player.getUUID());
        player.getInventory().clearContent();

        // Reset all attributes to their default values
        AttributeSupplier defaultAttributes = ServerPlayer.createAttributes().build();
        for (AttributeInstance instance : player.getAttributes().getSyncableAttributes()) {
            try {
                instance.setBaseValue(defaultAttributes.getBaseValue(instance.getAttribute()));
            } catch (Exception ignored) {
                // Some attributes may not exist in the default supplier, skip them
            }
        }

        // Heal to full after resetting max health
        player.setHealth(player.getMaxHealth());
    }

    public static KitClass getPreviewClass(ServerPlayer player) {
        return PREVIEW_CLASSES.get(player.getUUID());
    }

    // All available classes
    private static final Map<String, KitClass> CLASSES = new HashMap<>();

    // Player -> their current class
    private static final Map<UUID, KitClass> PLAYER_CLASSES = new HashMap<>();

    static {
        register(new Archer());
        // register more classes here
    }

    public static void register(KitClass kitClass) {
        CLASSES.put(kitClass.getName().toLowerCase(), kitClass);
    }

    public static Map<String, KitClass> getClasses() {
        return CLASSES;
    }

    public static void setClass(ServerPlayer player, KitClass kitClass) {
        PLAYER_CLASSES.put(player.getUUID(), kitClass);
        kitClass.apply(player);
    }

    public static KitClass getClass(Player player) {
        return PLAYER_CLASSES.get(player.getUUID());
    }

    public static void reapplyOnRespawn(ServerPlayer player) {
        KitClass kitClass = PLAYER_CLASSES.get(player.getUUID());
        if (kitClass != null) {
            kitClass.apply(player);
        }
    }
}