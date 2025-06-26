package com.lancas.vsafe.config;

import com.lancas.vsafe.EzDebug;
import com.lancas.vsafe.VSafeMod;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Config(name = VSafeMod.MODID)
public class VSafeConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public EzDebug.DebugMode debugMode = EzDebug.DebugMode.OnlyInLog;
    @ConfigEntry.Gui.Tooltip
    public boolean safePhysThreadOn = true;


    public static void init() {
        AutoConfig.register(VSafeConfig.class, Toml4jConfigSerializer::new);
    }
    public static VSafeConfig get() {
        return AutoConfig.getConfigHolder(VSafeConfig.class).getConfig();
    }
    public static void save() {
        AutoConfig.getConfigHolder(VSafeConfig.class).save();
    }


    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Component.translatable("title.vsafe.config"))
            .setSavingRunnable(VSafeConfig::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.vsafe.general"));


        general.addEntry(entryBuilder.startBooleanToggle(
                    Component.translatable("option.vsafe.safe_phys_thread_on"),
                    get().safePhysThreadOn
                )
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.vsafe.safe_phys_thread_on"))
                .setSaveConsumer(debug -> get().safePhysThreadOn = debug)
                .build()
        );

        general.addEntry(entryBuilder.startEnumSelector(
                    Component.translatable("option.vsafe.debug_mode"),
                    EzDebug.DebugMode.class,
                    get().debugMode
                )
                .setDefaultValue(EzDebug.DebugMode.OnlyInLog)
                .setTooltip(Component.translatable("tooltip.vsafe.debug_mode"))
                .setSaveConsumer(debug -> get().debugMode = debug)
                .build()
        );

        return builder.build();
    }
}