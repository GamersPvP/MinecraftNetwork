package com.github.jolice.game.listener.change;

import com.github.jolice.game.core.Game;
import com.github.jolice.game.map.vote.GameMapVoting;
import lombok.RequiredArgsConstructor;
import com.github.jolice.game.core.GameState;
import com.github.jolice.game.event.GameStateChangeEvent;
import io.riguron.server.ServerName;
import io.riguron.server.repository.ServerFieldType;
import io.riguron.server.repository.ServerRepository;
import io.riguron.system.internalization.Message;
import io.riguron.system.stream.Broadcast;


@RequiredArgsConstructor
public class GameCountdownListener extends SingleStateChangeListener {

    private final GameMapVoting gameMapVoting;
    private final Broadcast broadcast;
    private final ServerRepository serverRepository;

    @Override
    protected void onGameStateChange(GameStateChangeEvent gameStateChangeEvent) {
        this.closeVoting(gameStateChangeEvent.getGame());
    }

    private void closeVoting(Game game) {
        if (gameMapVoting.isOpen()) {
            gameMapVoting.getVotingWinner().ifPresent(gameMap -> {
                game.setMap(gameMap);
                serverRepository.set(ServerName.INSTANCE.get(), ServerFieldType.MAP, gameMap.getName());
                broadcast.broadcast(new Message("game.voting.map.won", gameMap.getName()));
            });
            gameMapVoting.close();
        }
    }

    @Override
    protected GameState getTriggerState() {
        return GameState.COUNTDOWN;
    }
}
