package com.combimagnetron.brilliantavatars.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class AvatarExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "brilliantavatars";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Combimagnetron";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() { return true; }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        return PlaceholderParser.parse(offlinePlayer, params, false);
    }
}
