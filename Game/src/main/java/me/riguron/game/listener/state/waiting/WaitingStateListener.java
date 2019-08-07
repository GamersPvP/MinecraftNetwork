package me.riguron.game.listener.state.waiting;

import me.riguron.game.core.GameState;
import me.riguron.game.listener.state.StateDependentListener;

import java.util.Arrays;
import java.util.List;

public class WaitingStateListener implements StateDependentListener {
    @Override
    public List<GameState> states() {
        return Arrays.asList(GameState.WAITING, GameState.COUNTDOWN);
    }
}
