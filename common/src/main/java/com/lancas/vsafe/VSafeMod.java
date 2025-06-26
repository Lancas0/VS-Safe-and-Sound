package com.lancas.vsafe;

import com.google.common.base.Suppliers;
import com.lancas.vsafe.config.VSafeConfig;
import com.lancas.vsafe.valkyrien.ExceptionalForceInducer;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import java.util.function.Supplier;

public class VSafeMod {
    public static final String MODID = "vsafe";

    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MODID));

    // Registering a new creative tab
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MODID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> EXAMPLE_TAB = TABS.register("tab", () ->
        CreativeTabRegistry.create(Component.translatable("itemGroup." + MODID + ".tab"),
            () -> new ItemStack(VSafeMod.CRASH_IT.get())));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MODID, Registries.ITEM);
    public static final RegistrySupplier<Item> CRASH_IT = ITEMS.register("crash_it", () ->
        new Item(new Item.Properties().arch$tab(VSafeMod.EXAMPLE_TAB)) {
            @Override
            public InteractionResult useOn(UseOnContext ctx) {
                if (!(ctx.getLevel() instanceof ServerLevel level))
                    return InteractionResult.PASS;

                ServerShip ship = VSGameUtilsKt.getShipObjectManagingPos(level, ctx.getClickedPos());
                if (ship == null)
                    return InteractionResult.PASS;

                ship.saveAttachment(ExceptionalForceInducer.class, new ExceptionalForceInducer());
                return InteractionResult.SUCCESS;
            }
        }
    );

    public static void init() {
        TABS.register();
        ITEMS.register();

        VSafeConfig.init();
    }
}
