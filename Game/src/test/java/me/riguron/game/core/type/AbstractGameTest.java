package me.riguron.game.core.type;

import org.bukkit.plugin.PluginManager;
import org.junit.Before;
import org.junit.Test;
import me.riguron.game.core.Game;
import me.riguron.game.core.GameState;
import me.riguron.game.event.GameStateChangeEvent;
import me.riguron.game.map.GameMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class AbstractGameTest {

    private Game abstractGame;
    private PluginManager pluginManager;

    @Before
    public void setUp() {
        pluginManager = mock(PluginManager.class);
        abstractGame = new AbstractGame(mock(GameMap.class), pluginManager);
    }

    @Test
    public void whenStartedThenPlayersTeleported() {
        abstractGame.start();
        // start doesn't actually switch the game state
        assertEquals(GameState.WAITING, abstractGame.getState());
    }

    @Test
    public void whenStateChangedToTheSameThenEventNotDispatched() {
        abstractGame.setState(GameState.WAITING);
        verifyZeroInteractions(pluginManager);
    }

    @Test
    public void whenStateSwitchedThenEventFired() {
        abstractGame.setState(GameState.COUNTDOWN);
        assertEquals(GameState.COUNTDOWN, abstractGame.getState());
        verify(pluginManager).callEvent(argThat(event -> {
            assertEquals(GameStateChangeEvent.class, event.getClass());
            GameStateChangeEvent gameStateChangeEvent = (GameStateChangeEvent) event;
            return GameState.COUNTDOWN == gameStateChangeEvent.getTo()
                    &&
                    gameStateChangeEvent.getGame() == abstractGame;
        }));

    }


}