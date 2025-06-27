package com.lancas.vsafe;

import com.lancas.vsafe.config.VSafeConfig;
import com.lancas.vsafe.util.ExceptionUtil;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EzDebug {
    public enum DebugMode {
        Silent,
        OnlyInLog,
        OnlyInLogST,
        DisplayInGame,
        DisplayInGameAndLogST
    }
    public static boolean shouldDebugInLog() {
        return VSafeConfig.INSTANCE.debugMode != DebugMode.Silent;
    }
    public static boolean shouldDebugST() {
        return switch (VSafeConfig.INSTANCE.debugMode) {
            case OnlyInLogST, DisplayInGameAndLogST -> true;
            default -> false;
        };
    }
    public static boolean shouldDisplayInGame() {
        if (Platform.getEnvironment() == Env.SERVER) {
            return false;
        }

        return switch (VSafeConfig.INSTANCE.debugMode) {
            case DisplayInGame, DisplayInGameAndLogST -> true;
            default -> false;
        };
    }

    public static final Logger logger = Logger.getLogger("com.lancas.vsafe.EzDebug");

    public static final EzDebug DEFAULT = new EzDebug();

    private static void logInLog(Level level, String msg) {
        if (!shouldDebugInLog())
            return;

        if (shouldDebugST()) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            logger.log(level, msg + "\n" + Arrays.stream(stackTrace).map(Objects::toString).collect(Collectors.joining("\n")));
        } else {
            logger.log(level, msg);
        }
    }
    private static void exceptionInLog(Level level, String pre, Exception e) {
        if (!shouldDebugInLog())
            return;

        if (shouldDebugST()) {
            logger.log(level, pre + "\n" + ExceptionUtil.getStackTraceAsString(e));
        } else {
            logger.log(level, pre + "\n" + e.toString());
        }
    }

    public static EzDebug log(String str) {
        if (shouldDisplayInGame()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.sendSystemMessage(Component.literal(str));
            } else {
                //todo temp
            }
        }

        logInLog(Level.INFO, str);
        return DEFAULT;
    }

    public static <T> EzDebug logs(Iterable<T> c, @Nullable Function<T, String> strGetter) {
        if (c == null) {
            EzDebug.log("the collection is null.");
            return DEFAULT;
        }

        StringBuilder sb = new StringBuilder("Logs:\n");
        for (T t : c) {
            if (t == null) continue;  //todo maybe not skip
            if (strGetter == null)
                sb.append(t.toString()).append(", ");
            else
                sb.append(strGetter.apply(t)).append(", ");
        }
        log(sb.toString());
        return DEFAULT;
    }
    public static <K, V> EzDebug logs(Map<K, V> m, @Nullable BiFunction<K, V, String> strGetter) {
        if (m == null) {
            EzDebug.log("the map is null.");
            return DEFAULT;
        }

        StringBuilder sb = new StringBuilder("Logs:\n");
        m.forEach((k, v) -> {
            if (strGetter == null)
                sb.append("Key:" + k + ", Value:" + v).append("\n");
            else
                sb.append(strGetter.apply(k, v)).append("\n");
        });
        log(sb.toString());
        return DEFAULT;
    }

    public static EzDebug highlight(String str) {
        if (shouldDisplayInGame()) {
            Minecraft mcInst = Minecraft.getInstance();
            if (mcInst.player != null) {
                mcInst.player.sendSystemMessage(Component.literal("§a" + str));
            }
        }

        //System.out.println(str.startsWith("[Highlight]") ? str : "[Highlight]" + str);
        logInLog(Level.FINEST, str.startsWith("[Highlight]") ? str : "[Highlight]" + str);
        return DEFAULT;
    }
    public static EzDebug light(String str) {
        if (shouldDisplayInGame()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.sendSystemMessage(Component.literal("§7" + str));
            }
        }

        logInLog(Level.FINER, str.startsWith("[Light]") ? str : "[Light]" + str);
        return DEFAULT;
    }

    public static EzDebug warn(String str) {
        if (shouldDisplayInGame()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.sendSystemMessage(Component.literal("§6" + str));
            }

        }

        logInLog(Level.WARNING, str.startsWith("[Warn]") ? str : "[Warn]" + str);
        return DEFAULT;
    }
    public static EzDebug error(String str) {
        if (shouldDisplayInGame()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.sendSystemMessage(Component.literal("§4" + str));
            }
        }

        logInLog(Level.WARNING, str.startsWith("[Error]") ? str : "[Error]" + str);
        return DEFAULT;
    }
    public static EzDebug fatal(String str) {
        if (shouldDisplayInGame()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.sendSystemMessage(Component.literal("§4" + str));
            }
        }

        logInLog(Level.WARNING, str.startsWith("[Fatal]") ? str : "[Fatal]" + str);
        return DEFAULT;
    }

    public static EzDebug exception(String pre, Exception e) {
        if (shouldDisplayInGame()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.sendSystemMessage(Component.literal("§4" + pre + "\n" + e.toString()));
            }
        }

        exceptionInLog(Level.WARNING, pre, e);
        return DEFAULT;
    }

    public static EzDebug notImpl(String methodName) { return error("the method [" + methodName + "] is not impl!"); }



}
