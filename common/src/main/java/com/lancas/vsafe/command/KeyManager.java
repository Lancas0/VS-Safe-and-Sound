package com.lancas.vsafe.command;

/*
import com.lancas.vsafe.EzDebug;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Environment(EnvType.SERVER)
public class KeyManager {
    private static final Path KEY_FILE = Platform.getConfigFolder().resolve("vsafe_opme.key");

    public static void rotateKey() {
        int tryTimes = 20;
        for (int i = 0; i < tryTimes; ++i) {
            try {
                Files.writeString(KEY_FILE, UUID.randomUUID().toString());
                return;
            } catch (Exception e) {
                EzDebug.warn("fail to rotate key:" + e.toString());
            }
        }
    }

    public static boolean isKeyMatch(UUID inKey) {
        if (inKey == null)
            return false;
        if (!Files.exists(KEY_FILE))
            return false;

        try {
            UUID rightKey = UUID.fromString(Files.readString(KEY_FILE));
            return rightKey.equals(inKey);
        } catch (Exception e) {
            EzDebug.warn("fail to match key:" + e.toString());
            return false;
        }
    }
}
*/