package me.riguron.bukkit.listener.stream;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.riguron.bukkit.stream.StreamBroadcast;
import me.riguron.bukkit.stream.StreamType;

@RequiredArgsConstructor
public class JoinBroadcastingListener implements Listener {

    private final StreamBroadcast streamBroadcast;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        streamBroadcast.onStream(StreamType.JOIN, event.getPlayer().getUniqueId());
    }
}
