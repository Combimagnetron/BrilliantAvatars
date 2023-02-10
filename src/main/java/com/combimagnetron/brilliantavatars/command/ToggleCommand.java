package com.combimagnetron.brilliantavatars.command;

import com.combimagnetron.brilliantavatars.BrilliantAvatars;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ToggleCommand implements CommandExecutor {
    public final static NamespacedKey KEY = new NamespacedKey(BrilliantAvatars.getPlugin(BrilliantAvatars.class), "avatars-enabled");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players!");
            return false;
        }
        final PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        final boolean enabled = Boolean.parseBoolean(dataContainer.getOrDefault(KEY, PersistentDataType.STRING, "true"));
        final String message = !enabled ? "Enabled" : "Disabled";
        dataContainer.set(KEY, PersistentDataType.STRING, String.valueOf(!enabled));
        player.sendMessage(message + " the avatars!");
        return false;
    }
}
