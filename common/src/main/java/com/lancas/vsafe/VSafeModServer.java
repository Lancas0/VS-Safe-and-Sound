package com.lancas.vsafe;


import com.google.common.base.Suppliers;
import com.lancas.vsafe.command.VSafeCommand;
import com.lancas.vsafe.config.VSafeConfig;
import com.lancas.vsafe.valkyrien.ExceptionalForceInducer;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.jfr.event.ServerTickTimeEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import java.util.function.Supplier;

@Environment(EnvType.SERVER)
public class VSafeModServer {
    private static long lastRotateTimestamp = 0;
    private static final long rotateInterval = 1000 * 60;  //each minute rotate key
    public static void init() {
        /*lastRotateTimestamp = System.currentTimeMillis();
        KeyManager.rotateKey();

        TickEvent.SERVER_POST.register(s -> {
            if (System.currentTimeMillis() - lastRotateTimestamp > rotateInterval) {
                immediateRotateKey();
            }
        });*/
    }

    public static void immediateRotateKey() {
        /*lastRotateTimestamp = System.currentTimeMillis();
        KeyManager.rotateKey();*/
    }
}
