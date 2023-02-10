package com.combimagnetron.brilliantavatars;

import com.combimagnetron.brilliantavatars.command.ToggleCommand;
import com.combimagnetron.brilliantavatars.config.Config;
import com.combimagnetron.brilliantavatars.placeholder.*;
import com.combimagnetron.brilliantavatars.placeholder.event.AnvilRenameListener;
import com.combimagnetron.brilliantavatars.placeholder.event.ChatPacketListener;
import com.combimagnetron.brilliantavatars.placeholder.event.ScoreboardPacketListener;
import com.combimagnetron.brilliantavatars.placeholder.event.WindowPacketListener;
import com.combimagnetron.brilliantavatars.user.UserManager;
import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class BrilliantAvatars extends JavaPlugin {

    private final static GsonComponentSerializer SERIALIZER = GsonComponentSerializer.gson();
    private final static LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.builder().hexColors().useUnusualXRepeatedCharacterHexFormat().build();
    private Config config;
    private net.kyori.adventure.platform.bukkit.BukkitAudiences adventure;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
        PacketEvents.getAPI().getSettings()
                .bStats(false)
                .checkForUpdates(false);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new Config((YamlConfiguration) getConfig());
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new AvatarExpansion().register();
        }
        UserManager manager = new UserManager();
        getCommand("toggle").setExecutor(new ToggleCommand());
        this.adventure = BukkitAudiences.create(this);
        getServer().getPluginManager().registerEvents(new AnvilRenameListener(), this);
        initPacketListeners();
        PacketEvents.getAPI().init();
    }

    @Override
    public void onDisable() {
        this.adventure.close();
        this.adventure = null;
        PacketEvents.getAPI().terminate();
    }

    private void initPacketListeners() {
        if (!config.customPlaceholdersEnabled()) return;
        if (config.chatEnabled()) PacketEvents.getAPI().getEventManager().registerListener(new ChatPacketListener());
        if (config.scoreboardEnabled()) PacketEvents.getAPI().getEventManager().registerListener(new ScoreboardPacketListener());
        if (config.windowEnabled()) PacketEvents.getAPI().getEventManager().registerListener(new WindowPacketListener());
    }

    public net.kyori.adventure.platform.bukkit.BukkitAudiences audiences() {
        return adventure;
    }

    public static GsonComponentSerializer gsonSerializer() {
        return SERIALIZER;
    }

    public static LegacyComponentSerializer legacyComponentSerializer() {
        return LEGACY_COMPONENT_SERIALIZER;
    }
}
