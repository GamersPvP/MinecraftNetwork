package com.github.jolice.game.map.vote;

import com.github.jolice.game.map.GameMap;
import com.github.jolice.game.map.GameMaps;
import com.github.jolice.game.map.vote.result.UnknownMapResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameMapVotingTest {

    private GameMaps gameMaps;
    private GameMapVoting voting;

    @Before
    public void setUp() {
        gameMaps = mock(GameMaps.class);

        addMap("a");
        addMap("b");
        addMap("c");

        voting = new GameMapVoting(gameMaps);
    }

    @Test
    public void whenVoteForInexistentMapThenNotAccepted() {
        Assert.assertEquals(UnknownMapResult.class, voting.vote("something").getClass());
    }

    @Test
    public void whenVoteMostTimesForTeamThenItWon() {


        voting.vote("a");
        voting.vote("b");
        voting.vote("a");
        voting.vote("b");
        voting.vote("c");
        voting.vote("b");
        voting.vote("a");
        voting.vote("b");

        // a = 3
        // b = 4
        // c = 1

        Optional<GameMap> votingWinner = voting.getVotingWinner();


        assertTrue(votingWinner.isPresent());
        assertEquals("b", votingWinner.get().getName());
    }

    private void addMap(String name) {
        GameMap gameMap = mock(GameMap.class);
        when(gameMap.getName()).thenReturn(name);
        when(gameMaps.getMap(eq(name))).thenReturn(Optional.of(gameMap));
    }

}