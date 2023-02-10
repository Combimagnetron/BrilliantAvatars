package com.combimagnetron.brilliantavatars.placeholder.event;

import com.combimagnetron.brilliantavatars.placeholder.PlaceholderParser;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenWindow;
import org.bukkit.entity.Player;

public class WindowPacketListener extends PacketListenerAbstract {

    public WindowPacketListener() {
        super(PacketListenerPriority.MONITOR);
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.OPEN_WINDOW) {
            Player player = (Player) event.getPlayer();
            WrapperPlayServerOpenWindow windowPacket = new WrapperPlayServerOpenWindow(event);
            windowPacket.setTitle(PlaceholderParser.parseFullComponent(player, windowPacket.getTitle(), true));
        }
    }


}
