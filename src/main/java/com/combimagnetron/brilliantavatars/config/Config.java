package com.combimagnetron.brilliantavatars.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    private final ConfigurationSection section;
    private final boolean customPlaceholdersEnabled;
    private final boolean chatEnabled;
    private final boolean scoreboardEnabled;
    private final boolean windowEnabled;


    public Config(YamlConfiguration configuration) {
        this.section = configuration.getConfigurationSection("");
        this.customPlaceholdersEnabled = section.getBoolean("custom-placeholders.enabled");
        this.chatEnabled = section.getBoolean("custom-placeholders.chat-enabled");
        this.scoreboardEnabled = section.getBoolean("custom-placeholders.scoreboard-enabled");
        this.windowEnabled = section.getBoolean("custom-placeholders.gui-enabled");
    }

    public boolean chatEnabled() {
        return customPlaceholdersEnabled && chatEnabled;
    }

    public boolean scoreboardEnabled() {
        return customPlaceholdersEnabled && scoreboardEnabled;
    }

    public boolean windowEnabled() {
        return customPlaceholdersEnabled && windowEnabled;
    }

    public boolean customPlaceholdersEnabled() {
        return customPlaceholdersEnabled;
    }




}
