package net.wobba;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.wobba.classSystem.classManagementSystem.ClassManager;
import net.wobba.classSystem.gui.ClassSelectionMenu;

public class ModCommands {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("class")
                    .executes(context -> {
                        ServerPlayer player = context.getSource().getPlayerOrException();
                        player.openMenu(new SimpleMenuProvider(
                                (syncId, inv, p) -> new ClassSelectionMenu(syncId, inv),
                                Component.literal("Select Class")
                        ));
                        return 1;
                    })
            );

            dispatcher.register(Commands.literal("clearclass")
                    .executes(context -> {
                        ServerPlayer player = context.getSource().getPlayerOrException();
                        ClassManager.clearClass(player);
                        player.sendSystemMessage(Component.literal("Class cleared!").withStyle(ChatFormatting.GREEN));
                        return 1;
                    })
            );
        });
    }
}