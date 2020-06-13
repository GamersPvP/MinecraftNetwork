package com.github.jolice.game.death;

import com.github.jolice.game.death.handler.PlayerDeathHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@RequiredArgsConstructor
public class DeathListener implements Listener {

    private final PlayerDeathHandler playerDeathHandler;

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        playerDeathHandler.onPlayerDeath(e.getEntity().getUniqueId());
    }

}
