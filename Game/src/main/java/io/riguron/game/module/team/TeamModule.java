package io.riguron.game.module.team;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.ProvidesIntoSet;
import io.riguron.config.client.ConfigurationLoader;
import io.riguron.game.core.Game;
import io.riguron.game.listener.dropout.TeamDropOutListener;
import io.riguron.game.listener.start.TeamGameStartListener;
import io.riguron.game.listener.state.StateDependentListener;
import io.riguron.game.listener.team.TeamQuitListener;
import io.riguron.game.map.GameMap;
import io.riguron.game.player.GamePlayer;
import io.riguron.game.player.factory.GamePlayerFactory;
import io.riguron.game.player.repository.GamePlayerStorage;
import io.riguron.game.player.repository.ValidatingGamePlayerStorage;
import io.riguron.game.player.repository.VirtualGamePlayerStorage;
import io.riguron.game.player.team.NullTeamPlayer;
import io.riguron.game.player.team.TeamPlayer;
import io.riguron.game.team.client.TeamAssigner;
import io.riguron.game.team.client.TeamLoader;
import io.riguron.game.team.repository.InMemoryTeams;
import io.riguron.game.team.repository.Teams;
import io.riguron.game.team.selector.TeamGuiListener;
import io.riguron.game.team.selector.TeamSelectorInventory;
import io.riguron.game.winner.WinningHandler;
import io.riguron.game.winner.team.TeamResultCalculation;
import io.riguron.game.config.team.TeamGameConfig;
import io.riguron.game.core.Game;
import io.riguron.game.listener.dropout.TeamDropOutListener;
import io.riguron.game.listener.start.TeamGameStartListener;
import io.riguron.game.listener.state.StateDependentListener;
import io.riguron.game.listener.team.TeamQuitListener;
import io.riguron.game.map.GameMap;
import io.riguron.game.player.GamePlayer;
import io.riguron.game.player.factory.GamePlayerFactory;
import io.riguron.game.player.factory.TeamPlayerFactory;
import io.riguron.game.player.repository.GamePlayerStorage;
import io.riguron.game.player.repository.ValidatingGamePlayerStorage;
import io.riguron.game.player.repository.VirtualGamePlayerStorage;
import io.riguron.game.player.team.NullTeamPlayer;
import io.riguron.game.player.team.TeamPlayer;
import io.riguron.game.team.client.TeamAssigner;
import io.riguron.game.team.client.TeamLoader;
import io.riguron.game.team.repository.InMemoryTeams;
import io.riguron.game.team.repository.Teams;
import io.riguron.game.team.selector.TeamGuiListener;
import io.riguron.game.team.selector.TeamSelectorInventory;
import io.riguron.game.winner.WinningHandler;
import io.riguron.game.winner.team.ScoreTeamResultCalculation;
import io.riguron.game.winner.team.TeamResultCalculation;
import io.riguron.game.winner.team.TeamWinningHandler;
import io.riguron.system.stream.Broadcast;
import org.bukkit.Server;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;


public class TeamModule extends AbstractModule {

    @Provides
    @Singleton
    public GamePlayerStorage<TeamPlayer> gamePlayerRepository() {
        return new ValidatingGamePlayerStorage<>(new VirtualGamePlayerStorage<>(new NullTeamPlayer()));
    }

    @Provides
    @Singleton
    public GamePlayerStorage<?> genericPlayerRepository(GamePlayerStorage<TeamPlayer> teamPlayerRepository) {
        return teamPlayerRepository;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unchecked")
    public GamePlayerStorage<? super GamePlayer> gamePlayerStorage(GamePlayerStorage<?> storage) {
        return (GamePlayerStorage<? super GamePlayer>) storage;
    }

    @ProvidesIntoSet
    @Singleton
    public Listener teamDropOutListener(Game game, Teams teams, GamePlayerStorage<TeamPlayer> gamePlayerStorage) {
        return new TeamDropOutListener(game, teams, gamePlayerStorage);
    }

    @Provides
    @Singleton
    public TeamGameConfig gameOptions(ConfigurationLoader configurationLoader) {
        return configurationLoader.load("game", TeamGameConfig.class);
    }

    @Provides
    @Singleton
    public TeamLoader teamLoader(TeamGameConfig teamGameConfig, Teams teams) {
        TeamLoader teamLoader = new TeamLoader(teamGameConfig, teams);
        teamLoader.loadTeams();
        return teamLoader;
    }

    @Provides
    @Singleton
    public List<GameMap> teamMaps(ConfigurationLoader configurationLoader, GamePlayerStorage<TeamPlayer> teamPlayerRepository, Teams teams) {
        return new TeamMapsLoader(configurationLoader, teamPlayerRepository, teams).loadTeamMaps();
    }

    @Provides
    @Singleton
    public GamePlayerFactory<? extends GamePlayer> gamePlayerFactory() {
        return new TeamPlayerFactory();
    }

    @Provides
    @Singleton
    public Teams teamRepository() {
        return new InMemoryTeams();
    }

    @ProvidesIntoSet
    @Singleton
    public StateDependentListener teamGuiListener(GamePlayerStorage<TeamPlayer> gamePlayerStorage, TeamSelectorInventory teamSelectorInventory) {
        return new TeamGuiListener(gamePlayerStorage, teamSelectorInventory);
    }

    @Provides
    @Singleton
    public TeamSelectorInventory teamSelectorInventory(Teams teams, Server server) {
        return new TeamSelectorInventory(new ArrayList<>(teams.getAllTeams()), server);
    }

    @ProvidesIntoSet
    @Singleton
    public Listener gameStartListener(TeamAssigner teamAssigner) {
        return new TeamGameStartListener(teamAssigner);
    }

    @Provides
    @Singleton
    public TeamAssigner teamAssigner(GamePlayerStorage<TeamPlayer> gamePlayerStorage, Teams teams) {
        return new TeamAssigner(gamePlayerStorage, teams);
    }

    @Provides
    @Singleton
    public StateDependentListener teamQuitListener(GamePlayerStorage<TeamPlayer> gamePlayerStorage) {
        return new TeamQuitListener(gamePlayerStorage);
    }

    @Provides
    @Singleton
    public TeamResultCalculation teamResultCalculation(Teams teams) {
        return new ScoreTeamResultCalculation(teams);
    }

    @Provides
    @Singleton
    public WinningHandler winningHandler(TeamResultCalculation teamResultCalculation, Broadcast broadcast) {
        return new TeamWinningHandler(teamResultCalculation, broadcast);
    }
}
