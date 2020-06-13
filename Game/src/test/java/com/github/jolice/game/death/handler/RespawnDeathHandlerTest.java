package com.github.jolice.game.death.handler;

import com.github.jolice.game.player.GamePlayer;
import com.github.jolice.game.player.repository.GamePlayerStorage;
import com.github.jolice.game.death.Respawn;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RespawnDeathHandlerTest {

    @Test
    public void onPlayerDeath() {

        GamePlayerStorage<? super GamePlayer> storage = mock(GamePlayerStorage.class);
        Respawn respawn = mock(Respawn.class);
        RespawnDeathHandler respawnDeathHandler = new RespawnDeathHandler(respawn, storage);

        UUID uuid = UUID.randomUUID();
        GamePlayer gamePlayer = mock(GamePlayer.class);
        when(storage.getPlayer(eq(uuid))).thenReturn(gamePlayer);

        respawnDeathHandler.onPlayerDeath(uuid);

        verify(respawn).markForRespawn(eq(gamePlayer));
    }
}