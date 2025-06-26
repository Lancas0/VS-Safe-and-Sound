package com.lancas.vsafe.forge;

import com.lancas.vsafe.VSafeMod;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VSafeMod.MODID)
public class VSafeModForge {
    public VSafeModForge() {
        EventBuses.registerModEventBus(VSafeMod.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        VSafeMod.init();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> VSafeModForgeClient.init(ModLoadingContext.get()));
    }
}
