package com.lancas.vsafe.fabric;

import com.lancas.vsafe.VSafeModClient;
import net.fabricmc.api.ClientModInitializer;

public class VSafeModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VSafeModClient.init();
    }
}
