package de.pierreschwang.hermes.config;

import de.pierreschwang.hermes.config.annotation.IgnoreConfigEntry;
import de.pierreschwang.hermes.config.converter.ConfigConverter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class HermesConfig {

    @IgnoreConfigEntry
    private final File configFile;

    @IgnoreConfigEntry
    private final Set<ConfigConverter> converters = new HashSet<>();

    public HermesConfig(File configFile) {
        this.configFile = configFile;
    }

    public void initialize() {
        if (!configFile.exists() && !configFile.mkdirs()) {
            System.err.printf("Failed to create configuration file %s", configFile.getAbsolutePath());
            return;
        }

    }

}
