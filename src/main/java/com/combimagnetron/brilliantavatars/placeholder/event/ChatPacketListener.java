package com.combimagnetron.brilliantavatars.placeholder.event;

import com.combimagnetron.brilliantavatars.BrilliantAvatars;
import com.combimagnetron.brilliantavatars.placeholder.PlaceholderParser;
import com.combimagnetron.brilliantavatars.user.UserManager;
import com.combimagnetron.brilliantavatars.util.Tasks;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.chat.message.ChatMessage_v1_19_1;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChatMessage;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChatMessage;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSystemChatMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class ChatPacketListener extends PacketListenerAbstract {

    public ChatPacketListener() {
        super(PacketListenerPriority.MONITOR);
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.CHAT_MESSAGE) {
            final ServerVersion serverVersion = PacketEvents.getAPI().getServerManager().getVersion();
            Player player = (Player) event.getPlayer();
            if (!UserManager.byUUID(player.getUniqueId()).enabled()) return;
            if (serverVersion.isNewerThanOrEquals(serverVersion)) {
                WrapperPlayServerChatMessage messagePacket = new WrapperPlayServerChatMessage(event);
                ChatMessage_v1_19_1 chatMessage = (ChatMessage_v1_19_1) messagePacket.getMessage();
                Component text = PlaceholderParser.parseFullComponent(player, chatMessage.getChatContent(), false);
                chatMessage.setUnsignedChatContent(text);
            }
        } else if (event.getPacketType() == PacketType.Play.Server.SYSTEM_CHAT_MESSAGE) {
            final ServerVersion serverVersion = PacketEvents.getAPI().getServerManager().getVersion();
            Player player = (Player) event.getPlayer();
            if (player == null) return;
            if (!UserManager.byUUID(player.getUniqueId()).enabled()) return;
            WrapperPlayServerSystemChatMessage messagePacket = new WrapperPlayServerSystemChatMessage(event);
            Component text = PlaceholderParser.parseFullComponent(player, messagePacket.getMessage(), false);
            messagePacket.setMessage(text);
        }
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CHAT_MESSAGE) {
            if (!UserManager.byUUID(((Player)event.getPlayer()).getUniqueId()).enabled()) return;
            WrapperPlayClientChatMessage messagePacket = new WrapperPlayClientChatMessage(event);
            if (messagePacket.getMessage().contains("<brilliantavatars")) {
                event.setCancelled(true);
                BrilliantAvatars.getPlugin(BrilliantAvatars.class).audiences().player((Player) event.getPlayer())
                        .sendMessage(Component.text("You can't use avatars in chat!", TextColor.fromHexString("#FF1122")));
            }
        }
    }

}
