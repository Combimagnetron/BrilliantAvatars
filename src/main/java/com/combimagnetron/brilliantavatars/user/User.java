package com.combimagnetron.brilliantavatars.user;

import com.combimagnetron.brilliantavatars.command.ToggleCommand;
import com.combimagnetron.brilliantavatars.util.Events;
import com.combimagnetron.brilliantavatars.util.Tasks;
import com.combimagnetron.imageloader.Avatar;
import com.combimagnetron.imageloader.Image;
import com.combimagnetron.imageloader.ImageUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class User {
    private final Map<Map.Entry<Integer, Integer>, String> bodyCache = new ConcurrentHashMap<>();
    private final Map<Map.Entry<Integer, Integer>, String> headCache = new ConcurrentHashMap<>();
    private final Map<Map.Entry<Integer, Integer>, String> smallSkinCache = new ConcurrentHashMap<>();
    private final OfflinePlayer bukkitPlayer;
    private final Avatar avatar;

    public User(OfflinePlayer player) {
        this.bukkitPlayer = player;
        this.avatar = Avatar.builder().colorType(Image.ColorType.LEGACY).playerName(player.getName()).build();
    }

    public User(String username) {
        this.bukkitPlayer = null;
        this.avatar = Avatar.builder().colorType(Image.ColorType.LEGACY).playerName(username).build();
    }

    public String getBody(int scale, int ascent) {
        try {
            return Tasks.getExecutor().submit(() -> {
                final String bodyString = bodyCache.computeIfAbsent(Map.entry(scale, ascent), k -> avatar.getFullBody(scale, ascent));
                return bodyCache.getOrDefault(Map.entry(scale, ascent), bodyString);
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHead(int scale, int ascent) {
        try {
            return Tasks.getExecutor().submit(() -> {
                final String smallAvatarString = headCache.computeIfAbsent(Map.entry(scale, ascent), k -> avatar.getHead(scale, ascent));
                return headCache.getOrDefault(Map.entry(scale, ascent), smallAvatarString);
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSmallSkin(int scale, int ascent) {
        try {
            return Tasks.getExecutor().submit(() -> {
                final String smallAvatarString = smallSkinCache.computeIfAbsent(Map.entry(scale, ascent), k -> avatar.getSmallSkin(ascent, scale));
                return smallSkinCache.getOrDefault(Map.entry(scale, ascent), smallAvatarString);
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public OfflinePlayer getBukkitPlayer() {
        return bukkitPlayer;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public boolean enabled() {
        if (bukkitPlayer == null) return true;
        return Boolean.parseBoolean(bukkitPlayer.getPlayer().getPersistentDataContainer().getOrDefault(ToggleCommand.KEY, PersistentDataType.STRING, "true"));
    }

    @Contract("_ -> new")
    public static @NotNull User fromOfflinePlayer(String string) {
        return new User(string);
    }
}
