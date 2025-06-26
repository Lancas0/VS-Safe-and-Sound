package com.lancas.vsafe.forge;

import com.lancas.vsafe.VSafeMod;
import com.lancas.vsafe.VSafeModClient;
import com.lancas.vsafe.config.VSafeConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

public class VSafeModForgeClient {
    public static void init(ModLoadingContext modLoadingContext) {
        VSafeModClient.init();

        modLoadingContext.registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory(
                (minecraft, screen) -> VSafeConfig.createConfigScreen(screen)
            )
        );
    }
}
