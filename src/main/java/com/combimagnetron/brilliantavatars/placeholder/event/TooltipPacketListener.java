package com.combimagnetron.brilliantavatars.placeholder.event;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import org.bukkit.entity.Player;

public class TooltipPacketListener extends PacketListenerAbstract {

    public TooltipPacketListener() {
        super(PacketListenerPriority.MONITOR);
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
            Player player = (Player) event.getPlayer();
            WrapperPlayServerEntityMetadata entityMetadataPacket = new WrapperPlayServerEntityMetadata(event);
            entityMetadataPacket.getEntityMetadata().forEach(entityData -> {
                if (entityData.getType() == EntityDataTypes.COMPONENT) {
                    //entityData.setValue();
                }
            });
        }
    }


}
