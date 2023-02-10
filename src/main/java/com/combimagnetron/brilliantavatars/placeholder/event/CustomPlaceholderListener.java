package com.combimagnetron.brilliantavatars.placeholder.event;

import com.combimagnetron.brilliantavatars.BrilliantAvatars;
import com.combimagnetron.brilliantavatars.placeholder.PlaceholderParser;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.chat.message.ChatMessage_v1_19_1;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChatMessage;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CustomPlaceholderListener extends PacketListenerAbstract {
    private final BrilliantAvatars plugin;

    public CustomPlaceholderListener(BrilliantAvatars plugin) {
        super(PacketListenerPriority.HIGHEST);
        this.plugin = plugin;
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        final ServerVersion serverVersion = PacketEvents.getAPI().getServerManager().getVersion();
        Player player = (Player) event.getPlayer();
        if (event.getPacketType() == PacketType.Play.Server.CHAT_MESSAGE) {
            if (serverVersion.isNewerThanOrEquals(serverVersion)) {
                WrapperPlayServerChatMessage messagePacket = new WrapperPlayServerChatMessage(event);
                ChatMessage_v1_19_1 chatMessage = (ChatMessage_v1_19_1) messagePacket.getMessage();
                Component text = PlaceholderParser.parseFullComponent(player, chatMessage.getChatContent(), false);
                chatMessage.setUnsignedChatContent(text);
            }
        } else if (event.getPacketType() == PacketType.Play.Server.SYSTEM_CHAT_MESSAGE) {
            WrapperPlayServerSystemChatMessage messagePacket = new WrapperPlayServerSystemChatMessage(event);
            Component text = PlaceholderParser.parseFullComponent(player, messagePacket.getMessage(), false);
            messagePacket.setMessage(text);
        } else if (event.getPacketType() == PacketType.Play.Server.OPEN_WINDOW) {
            WrapperPlayServerOpenWindow windowPacket = new WrapperPlayServerOpenWindow(event);
            windowPacket.setTitle(PlaceholderParser.parseFullComponent((Player) event.getPlayer(), windowPacket.getTitle(), true));
        } else if (event.getPacketType() == PacketType.Play.Server.SCOREBOARD_OBJECTIVE) {
            WrapperPlayServerScoreboardObjective objectivePacket = new WrapperPlayServerScoreboardObjective(event);
            Bukkit.getLogger().info(LegacyComponentSerializer.legacyAmpersand().serialize(objectivePacket.getDisplayName()));
            objectivePacket.setDisplayName(PlaceholderParser.parseFullComponent(player, objectivePacket.getDisplayName(), false));
        } else if (event.getPacketType() == PacketType.Play.Server.TEAMS) {
            WrapperPlayServerTeams teamsPacket = new WrapperPlayServerTeams(event);
            Optional<WrapperPlayServerTeams.ScoreBoardTeamInfo> infoOptional = teamsPacket.getTeamInfo();
            if (infoOptional.isEmpty()) return;
            WrapperPlayServerTeams.ScoreBoardTeamInfo teamInfo = infoOptional.get();
            teamInfo.setPrefix(PlaceholderParser.parseFullComponent(player, teamInfo.getPrefix(), false));
            teamInfo.setSuffix(PlaceholderParser.parseFullComponent(player, teamInfo.getSuffix(), false));
            Bukkit.getLogger().info(teamsPacket.getTeamName());
        }
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CHAT_MESSAGE) {
            WrapperPlayClientChatMessage messagePacket = new WrapperPlayClientChatMessage(event);
            if (messagePacket.getMessage().contains("<brilliantavatars")) {
                event.setCancelled(true);
                BrilliantAvatars.getPlugin(BrilliantAvatars.class).audiences().player((Player) event.getPlayer())
                        .sendMessage(Component.text("You can't use avatars in chat!", TextColor.fromHexString("#FF1122")));
            }
        }
    }

}
