package net.wobba.classSystem.playerClasses.undying;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class AegisManager {

    private static final Map<UUID, Integer> aegisStacks = new HashMap<>();
    private static final Map<UUID, Long> lastDamageTime = new HashMap<>();
    private static final int MAX_STACKS = 3;
    private static final int REGEN_TICKS = 300; // 15 seconds

    public static int getStacks(ServerPlayer player) {
        return aegisStacks.getOrDefault(player.getUUID(), 0);
    }

    public static void setStacks(ServerPlayer player, int stacks) {
        int clamped = Math.max(0, Math.min(MAX_STACKS, stacks));
        aegisStacks.put(player.getUUID(), clamped);
        updateSpeedEffect(player);
        updateAegisItems(player, clamped);
    }

    private static void updateAegisItems(ServerPlayer player, int stacks) {
        // Find all aegis passive item slots
        List<Integer> aegisSlots = new ArrayList<>();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i).getItem() instanceof Aegis) {
                aegisSlots.add(i);
            }
        }

        if (aegisSlots.isEmpty()) return;

        int mainSlot = aegisSlots.get(0); // the real passive slot

        // Remove any extra aegis items first
        for (int i = 1; i < aegisSlots.size(); i++) {
            player.getInventory().setItem(aegisSlots.get(i), ItemStack.EMPTY);
        }

        // Set the stack count on the main item to show stacks
        ItemStack aegisStack = player.getInventory().getItem(mainSlot);
        if (!aegisStack.isEmpty()) {
            aegisStack.setCount(Math.max(1, stacks)); // count cant be 0
        }
    }

    public static void addStack(ServerPlayer player) {
        setStacks(player, getStacks(player) + 1);
    }

    public static void removeStack(ServerPlayer player) {
        setStacks(player, getStacks(player) - 1);
    }

    public static void recordDamage(ServerPlayer player) {
        lastDamageTime.put(player.getUUID(), player.level().getGameTime());
    }

    public static void tick(ServerPlayer player) {
        int stacks = getStacks(player);

        if (stacks < MAX_STACKS) {
            long lastDamage = lastDamageTime.getOrDefault(player.getUUID(), 0L);
            long currentTime = player.level().getGameTime();

            if (currentTime - lastDamage >= REGEN_TICKS) {
                addStack(player);
                lastDamageTime.put(player.getUUID(), currentTime);
            }
        }

        spawnOrbParticles(player);
    }

    private static void updateSpeedEffect(ServerPlayer player) {
        if (getStacks(player) > 0) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.SPEED, Integer.MAX_VALUE, 1, false, false));
        } else {
            player.removeEffect(MobEffects.SPEED);
        }
    }

    private static void spawnOrbParticles(ServerPlayer player) {
        if (!(player.level() instanceof ServerLevel serverLevel)) return;

        int stacks = getStacks(player);
        double yaw = Math.toRadians(player.getYHeadRot());

        double[][] positions = {
                { player.getX(), player.getEyeY() + 0.5, player.getZ() },
                { player.getX() - Math.cos(yaw) * 0.5, player.getEyeY() + 0.1, player.getZ() - Math.sin(yaw) * 0.5 },
                { player.getX() + Math.cos(yaw) * 0.5, player.getEyeY() + 0.1, player.getZ() + Math.sin(yaw) * 0.5 }
        };

        for (int i = 0; i < 3; i++) {
            boolean active = i < stacks;
            ParticleOptions particle = active ? ParticleTypes.GLOW_SQUID_INK : ParticleTypes.SMOKE;

            for (ServerPlayer viewer : serverLevel.players()) {
                if (viewer.getUUID().equals(player.getUUID())) continue;
                serverLevel.sendParticles(viewer, particle, false, false,
                        positions[i][0], positions[i][1], positions[i][2],
                        1, 0, 0, 0, 0);
            }
        }
    }

    public static void clearPlayer(UUID uuid) {
        aegisStacks.remove(uuid);
        lastDamageTime.remove(uuid);
    }
}
