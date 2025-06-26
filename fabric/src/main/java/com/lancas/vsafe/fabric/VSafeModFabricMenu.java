package com.lancas.vsafe.fabric;

import com.lancas.vsafe.VSafeMod;
import com.lancas.vsafe.config.VSafeConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ModInitializer;
import org.apache.commons.lang3.NotImplementedException;

public class VSafeModFabricMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return VSafeConfig::createConfigScreen;
    }
}
