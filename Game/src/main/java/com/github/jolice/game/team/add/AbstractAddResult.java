package com.github.jolice.game.team.add;

import lombok.RequiredArgsConstructor;
import com.github.jolice.game.team.Team;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractAddResult implements TeamAddResult {

    private final Team team;

    @Override
    public List<String> description() {
        return Arrays.asList(messageDescription(), team.getName());
    }

    abstract String messageDescription();
}
