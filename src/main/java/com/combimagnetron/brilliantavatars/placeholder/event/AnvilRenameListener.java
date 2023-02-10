package com.combimagnetron.brilliantavatars.placeholder.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class AnvilRenameListener implements Listener {
    @EventHandler
    public void onRename(PrepareAnvilEvent event) {
        if (event.getResult() == null) return;
        if (event.getResult().getItemMeta() == null) return;
        String name = event.getResult().getItemMeta().getDisplayName();
        if (name.contains("<brilliantavatars_")) event.setResult(new ItemStack(Material.AIR));
    }

}
