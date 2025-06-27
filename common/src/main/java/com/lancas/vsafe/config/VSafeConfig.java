package com.lancas.vsafe.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.lancas.vsafe.EzDebug;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import dev.architectury.platform.Platform;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.NotImplementedException;
import org.spongepowered.asm.mixin.Final;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
@Config(name = VSafeMod.MODID)
public class VSafeConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public EzDebug.DebugMode debugMode = EzDebug.DebugMode.OnlyInLogST;
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
                .setDefaultValue(EzDebug.DebugMode.OnlyInLogST)
                .setTooltip(Component.translatable("tooltip.vsafe.debug_mode"))
                .setSaveConsumer(debug -> get().debugMode = debug)
                .build()
        );

        return builder.build();
    }
}*/
public class VSafeConfig {
    //private static final String configFilename = "vsafe-config.json";
    //private static final Path configPath = resolveConfigPath(configFilename);
    private static final Path CONFIG_PATH = Platform.getConfigFolder();
    private static final File CONFIG_FILE = CONFIG_PATH.resolve("vsafe.toml").toFile();

    public static final VSafeConfig INSTANCE = new VSafeConfig(EzDebug.DebugMode.OnlyInLogST, true);

    public volatile EzDebug.DebugMode debugMode;
    public volatile boolean safePhysThreadOn;
    private VSafeConfig() {}
    private VSafeConfig(EzDebug.DebugMode inDebugMode, boolean inSafePhysThreadOn) {
        debugMode = inDebugMode;
        safePhysThreadOn = inSafePhysThreadOn;
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    public static void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try {
                VSafeConfig loaded = new Toml().read(CONFIG_FILE).to(VSafeConfig.class);
                INSTANCE.safePhysThreadOn = loaded.safePhysThreadOn;
                INSTANCE.debugMode = loaded.debugMode;

                if (INSTANCE.debugMode == null) {
                    INSTANCE.debugMode = EzDebug.DebugMode.OnlyInLogST;
                    EzDebug.warn("get null debug mode, set to default");
                }
            } catch (Exception e) {
                EzDebug.warn("fail to load VSafe config:" + e.toString());
            }
        }
    }

    public static void saveConfig() {
        try {
            TomlWriter writer = new TomlWriter();
            writer.write(INSTANCE, CONFIG_FILE);
        } catch (IOException e) {
            EzDebug.warn("fail to save VSafe config:" + e.toString());
        }
    }

    @Environment(EnvType.CLIENT)
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Component.translatable("title.vsafe.config"))
            .setSavingRunnable(VSafeConfig::saveConfig);  //Single game auto saving

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.vsafe.category.server"));


        general.addEntry(entryBuilder.startBooleanToggle(
                    Component.translatable("option.vsafe.safe_phys_thread_on"),
                    INSTANCE.safePhysThreadOn
                )
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.vsafe.safe_phys_thread_on"))
                .setSaveConsumer(debug -> INSTANCE.safePhysThreadOn = debug)
                .build()
        );

        general.addEntry(entryBuilder.startEnumSelector(
                    Component.translatable("option.vsafe.debug_mode"),
                    EzDebug.DebugMode.class,
                    INSTANCE.debugMode
                )
                .setDefaultValue(EzDebug.DebugMode.OnlyInLogST)
                .setTooltip(Component.translatable("tooltip.vsafe.debug_mode"))
                .setSaveConsumer(debug -> INSTANCE.debugMode = debug)
                .build()
        );

        return builder.build();
    }
}