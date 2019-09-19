package io.riguron.game.timer;

import lombok.RequiredArgsConstructor;
import io.riguron.game.core.Game;
import io.riguron.game.core.GameState;
import io.riguron.system.task.timer.Timer;

@RequiredArgsConstructor
public class StartManagingTimer implements Timer {

    private final Game game;
    private final Timer delegate;

    @Override
    public void start() {
        game.setState(GameState.COUNTDOWN);
        delegate.start();
    }

    @Override
    public void stop() {
        game.setState(GameState.WAITING);
        delegate.stop();
    }
}
