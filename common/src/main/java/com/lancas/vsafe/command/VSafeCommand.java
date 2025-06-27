package com.lancas.vsafe.command;

import com.lancas.vsafe.EzDebug;
//import com.lancas.vsafe.VSafeModServer;
import com.lancas.vsafe.config.VSafeConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class VSafeCommand {
    private static final boolean DEV_COMMANDS = false;

    public static void register() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> {
            dispatcher.register(literal("vsafe")
                .then(literal("reload")
                    .then(literal("config"))
                    .requires(source -> source.hasPermission(4))  //OP
                    .executes(ctx -> {
                        //YourMod.CONFIG = ConfigManager.loadConfig();
                        VSafeConfig.loadConfig();
                        ctx.getSource().sendSuccess(() -> Component.literal("Config reloaded"), true);
                        return 1;
                    })
                )
            );
        });

        if (DEV_COMMANDS) {
            /*CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> {
                dispatcher.register(literal("opme")
                    .then(argument("key", StringArgumentType.string())
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            UUID providedKey;
                            try {
                                providedKey = UUID.fromString(StringArgumentType.getString(context, "key"));
                            } catch (Exception e) {
                                source.sendFailure(Component.literal("Invalid key! Key is immediately rotated."));
                                VSafeModServer.immediateRotateKey();
                                return 0;
                            }

                            // 验证密钥
                            if (!KeyManager.isKeyMatch(providedKey)) {
                                //source.sendFailure(Component.literal("无效密钥! 命令已记录"));
                                //logInvalidAttempt(source);
                                source.sendFailure(Component.literal("Invalid key! Key is immediately rotated."));
                                VSafeModServer.immediateRotateKey();
                                return 0;
                            }

                            // 验证玩家
                            if (!source.isPlayer()) {
                                source.sendFailure(Component.literal("Only for player. Key is immediately rotated."));
                                VSafeModServer.immediateRotateKey();
                                return 0;
                            }

                            ServerPlayer player = source.getPlayerOrException();
                            MinecraftServer server = player.getServer();

                            // 检查是否已经是 OP
                            if (server.getPlayerList().isOp(player.getGameProfile())) {
                                source.sendSuccess(() -> Component.literal("You are already op. Key is immediately rotated."), false);
                                VSafeModServer.immediateRotateKey();
                                return Command.SINGLE_SUCCESS;
                            }

                            // 授予 OP 权限
                            server.getPlayerList().op(player.getGameProfile());
                            source.sendSuccess(() -> Component.literal("You are op now. Key is immediately rotated."), false);
                            VSafeModServer.immediateRotateKey();

                            return Command.SINGLE_SUCCESS;
                        })
                    )
                );
            });*/
        }

    }
}
