package com.lancas.vsafe.fabric;

import com.lancas.vsafe.VSafeModClient;
import com.lancas.vsafe.VSafeModServer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class VSafeModFabricServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        VSafeModServer.init();
    }
}
