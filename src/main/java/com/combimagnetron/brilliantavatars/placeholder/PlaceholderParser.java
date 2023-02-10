package com.combimagnetron.brilliantavatars.placeholder;

import com.combimagnetron.brilliantavatars.BrilliantAvatars;
import com.combimagnetron.brilliantavatars.user.User;
import com.combimagnetron.brilliantavatars.user.UserManager;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class PlaceholderParser {
    private static final Pattern pattern = Pattern.compile("<([^%]*)>");

    public static String parse(OfflinePlayer offlinePlayer, String string, boolean skipCheck) {
        string = PlaceholderAPI.setBracketPlaceholders(offlinePlayer, string);
        if (string.equals("enabled")) {
            final User user = UserManager.byUUID(offlinePlayer.getUniqueId());
            return String.valueOf(user.enabled());
        }
        if (!string.contains("_")) return string;
        final String[] parameters = string.split("_");
        int length = parameters.length;
        String playerName = "";
        if (length > 3) playerName = parameters[3];
        final User user = playerName.length() == 0 ? UserManager.byUUID(offlinePlayer.getUniqueId()) : UserManager.registerOfflineUser(playerName);
        if (!user.enabled() && !skipCheck || user.getAvatar() == null) return "";
        final int size = Integer.parseInt(parameters[1] == null ? 1 + "" : parameters[1]);
        final int ascent = Integer.parseInt(parameters[2] == null ? 0 + "" : parameters[2]);
        return switch (parameters[0]) {
            case "body" -> user.getBody(size, ascent);
            case "head" -> user.getHead(size, ascent);
            case "small" -> user.getSmallSkin(size, ascent);
            default -> "Invalid placeholder, read the wiki for more information.";
        };
    }


    public static Component parseFullComponent(Player offlinePlayer, Component adventureComponent, boolean skipCheck) {
        return adventureComponent.replaceText(builder -> builder.match(pattern).replacement((matchResult, builder1) -> {
            String placeholder = parse(Bukkit.getOfflinePlayer(offlinePlayer.getUniqueId()), matchResult.group().replace("<brilliantavatars_", "").replace(">", ""), skipCheck);
            Component placeholderComponent = BrilliantAvatars.legacyComponentSerializer().deserialize(placeholder);
            return builder1.build().replaceText(builder2 -> builder2.matchLiteral(matchResult.group()).replacement(placeholderComponent)).font(Key.key("brilliantavatars:cubes"));
        }));
    }

}
