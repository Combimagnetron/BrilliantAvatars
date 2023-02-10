package com.combimagnetron.brilliantavatars.placeholder.event;

import com.combimagnetron.brilliantavatars.placeholder.PlaceholderParser;
import com.combimagnetron.brilliantavatars.user.UserManager;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Optional;

public class ScoreboardPacketListener extends PacketListenerAbstract {

    public ScoreboardPacketListener() {
        super(PacketListenerPriority.MONITOR);
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.SCOREBOARD_OBJECTIVE) {
            Player player = (Player) event.getPlayer();
            if (!UserManager.byUUID(player.getUniqueId()).enabled()) return;
            WrapperPlayServerScoreboardObjective objectivePacket = new WrapperPlayServerScoreboardObjective(event);
            objectivePacket.setDisplayName(PlaceholderParser.parseFullComponent(player, objectivePacket.getDisplayName(), false));
        } else if (event.getPacketType() == PacketType.Play.Server.TEAMS) {
            Player player = (Player) event.getPlayer();
            if (!UserManager.byUUID(player.getUniqueId()).enabled()) return;
            WrapperPlayServerTeams teamsPacket = new WrapperPlayServerTeams(event);
            Optional<WrapperPlayServerTeams.ScoreBoardTeamInfo> infoOptional = teamsPacket.getTeamInfo();
            if (infoOptional.isEmpty()) return;
            WrapperPlayServerTeams.ScoreBoardTeamInfo teamInfo = infoOptional.get();
            if (teamInfo.getPrefix() != Component.empty()) {
                teamInfo.setPrefix(PlaceholderParser.parseFullComponent(player, teamInfo.getPrefix(), false));
            }
            if (teamInfo.getSuffix() != Component.empty()) {
                teamInfo.setSuffix(PlaceholderParser.parseFullComponent(player, teamInfo.getSuffix(), false));
            }
        }
    }


}
