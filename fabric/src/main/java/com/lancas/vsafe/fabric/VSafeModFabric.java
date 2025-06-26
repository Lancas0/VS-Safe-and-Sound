package com.lancas.vsafe.fabric;

import com.lancas.vsafe.VSafeMod;
import net.fabricmc.api.ModInitializer;

public class VSafeModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VSafeMod.init();
    }
}
