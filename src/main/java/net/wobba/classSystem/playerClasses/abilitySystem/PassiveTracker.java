package net.wobba.classSystem.playerClasses.abilitySystem;

import java.util.*;

public class PassiveTracker {

    private static final Map<UUID, Set<PassiveAbilityItem>> activePassives = new HashMap<>();

    public static Set<PassiveAbilityItem> getActivePassives(UUID uuid) {
        return activePassives.computeIfAbsent(uuid, k -> new HashSet<>());
    }

    public static void setActivePassives(UUID uuid, Set<PassiveAbilityItem> passives) {
        activePassives.put(uuid, passives);
    }

    public static void clearPlayer(UUID uuid) {
        activePassives.remove(uuid);
    }
}