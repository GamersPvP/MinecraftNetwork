package com.github.jolice.gui.multipage;

import com.github.jolice.gui.multipage.player.PlayerPages;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.IntUnaryOperator;

@RequiredArgsConstructor
public class PageSwitcher {

    private final InventoryPages inventoryPages;
    private final PlayerPages playerPages;
    private final Server server;

    public void switchPage(UUID uuid, IntUnaryOperator function) {
        Player player = server.getPlayer(uuid);
        player.closeInventory();
        int switchedTo = playerPages.switchPage(uuid, function);
        player.openInventory(inventoryPages.getPage(switchedTo));
    }
}
