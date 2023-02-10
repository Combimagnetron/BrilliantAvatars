package com.combimagnetron.brilliantavatars.user;

import com.combimagnetron.brilliantavatars.command.ToggleCommand;
import com.combimagnetron.brilliantavatars.util.Events;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

import java.rmi.server.UID;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private static final Map<UUID, User> uuidUserMap = new ConcurrentHashMap<>();
    private static final Map<String, User> offlineUserCache = new ConcurrentHashMap<>();

    public UserManager() {
        Events.event(PlayerJoinEvent.class)
                .priority(EventPriority.HIGHEST)
                .async(event -> {
                    final User user = new User(event.getPlayer());
                    uuidUserMap.put(event.getPlayer().getUniqueId(), user);
                });
        Events.event(PlayerQuitEvent.class)
                .async(event -> {
                    terminate(event.getPlayer().getUniqueId());
                });
    }

    public static User byUUID(UUID uuid) {
        final Optional<User> user = Optional.ofNullable(uuidUserMap.get(uuid));
        return user.orElse(uuidUserMap.putIfAbsent(uuid, new User(Bukkit.getOfflinePlayer(uuid))));
    }

    public static void terminate(User user) {
        uuidUserMap.remove(user.getBukkitPlayer().getUniqueId());
    }

    public static void terminate(UUID uuid) {
        uuidUserMap.remove(uuid);
    }

    public static void addOfflineUser(String name, User user) {
        offlineUserCache.putIfAbsent(name, user);
    }

    public static User registerOfflineUser(String name) {
        User user = new User(name);
        return offlineUserCache.put(name, user);
    }

    public  static void removeOfflineUser(String name) {
        offlineUserCache.remove(name);
    }


}
