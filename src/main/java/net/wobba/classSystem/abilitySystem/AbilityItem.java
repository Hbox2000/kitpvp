package net.wobba.classSystem.abilitySystem;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.wobba.classSystem.playerClasses.warlock.PactBoon;

public abstract class AbilityItem extends Item {

    private final int cooldownTicks;

    public AbilityItem(Properties properties, int cooldownTicks) {
        super(properties);
        this.cooldownTicks = cooldownTicks;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        ServerPlayer serverPlayer = (ServerPlayer) player;

        if (serverPlayer.getCooldowns().isOnCooldown(new ItemStack(this))) {
            return InteractionResult.FAIL;
        }

        boolean used = onUse(serverPlayer);

        if (used) {
            serverPlayer.getCooldowns().addCooldown(new ItemStack(this), cooldownTicks);

            // Trigger pact boon passive
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                if (player.getInventory().getItem(i).getItem() instanceof PactBoon pactItem) {

                    pactItem.trigger(serverPlayer);

                    break;
                }
            }


        }

        return InteractionResult.SUCCESS;
    }

    // Return true if the ability was successfully used
    public abstract boolean onUse(ServerPlayer player);
}