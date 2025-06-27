package com.lancas.vsafe.forge;


import com.lancas.vsafe.VSafeModClient;
import com.lancas.vsafe.VSafeModServer;
import com.lancas.vsafe.config.VSafeConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class VSafeModForgeServer {
    public static void init(ModLoadingContext modLoadingContext) {
        VSafeModServer.init();
    }
}
