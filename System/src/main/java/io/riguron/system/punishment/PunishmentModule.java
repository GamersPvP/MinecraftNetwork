package io.riguron.system.punishment;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.ProvidesIntoSet;
import io.ebean.EbeanServer;
import io.riguron.messaging.MessagingService;
import io.riguron.persistence.entity.EntityGroup;
import io.riguron.server.repository.PlayerRepository;
import io.riguron.system.player.PlayerProfileRepository;
import io.riguron.system.punishment.model.ActivePunishmentRecord;
import io.riguron.system.punishment.model.PunishmentData;
import io.riguron.system.punishment.model.PunishmentRecord;
import io.riguron.system.punishment.repository.PunishmentDataRepository;
import io.riguron.system.punishment.service.ActivePunishmentService;
import io.riguron.system.punishment.service.PunishmentService;
import io.riguron.system.punishment.type.Kick;
import io.riguron.system.punishment.type.Warn;
import io.riguron.system.punishment.type.active.Ban;
import io.riguron.system.punishment.type.active.Mute;
import io.riguron.system.task.TaskFactory;
import io.riguron.system.player.PlayerProfileRepository;
import io.riguron.system.punishment.model.ActivePunishmentRecord;
import io.riguron.system.punishment.model.PunishmentData;
import io.riguron.system.punishment.model.PunishmentRecord;
import io.riguron.system.punishment.repository.PunishmentDataProvider;
import io.riguron.system.punishment.repository.PunishmentDataRepository;
import io.riguron.system.punishment.repository.PunishmentRepository;
import io.riguron.system.punishment.service.ActivePunishmentService;
import io.riguron.system.punishment.service.PunishmentService;
import io.riguron.system.punishment.type.ProxyKick;
import io.riguron.system.punishment.type.Warn;
import io.riguron.system.punishment.type.active.Excuse;
import io.riguron.system.punishment.type.active.Mute;
import io.riguron.system.punishment.type.active.Ban;
import io.riguron.system.punishment.type.Kick;
import io.riguron.system.task.TaskFactory;

import java.util.Arrays;

public class PunishmentModule extends AbstractModule {

    @Provides
    @Singleton
    public PunishmentChecker punishmentChecker(PunishmentDataRepository punishmentDataRepository, TaskFactory taskFactory) {
        return new PunishmentChecker(punishmentDataRepository, taskFactory);
    }


    @Provides
    @Singleton
    public Warn warn(PunishmentService punishmentService, PunishmentDataProvider dataProvider, PunishmentDataRepository repository, Ban ban) {
        return new Warn(punishmentService, dataProvider, repository, ban, 3);
    }


    @Provides
    @Singleton
    public Kick kick(ProxyKick proxyKick, PunishmentService punishmentService) {
        return new Kick(proxyKick, punishmentService);
    }

    @Provides
    @Singleton
    public Excuse excuse(PunishmentDataProvider dataProvider, PunishmentDataRepository repository) {
        return new Excuse(dataProvider, repository);
    }

    @Singleton
    @Provides
    public Mute mute(ActivePunishmentService activePunishmentService, Excuse excuse) {
        return new Mute(activePunishmentService, excuse);
    }

    @Singleton
    @Provides
    public Ban ban(ActivePunishmentService activePunishmentService, Excuse excuse, ProxyKick proxyKick) {
        return new Ban(activePunishmentService, excuse, proxyKick);
    }

    @Singleton
    @Provides
    public ActivePunishmentService activePunishmentService(PunishmentDataProvider provider, PunishmentDataRepository dataRepository, PunishmentRepository repository, PlayerProfileRepository playerProfileRepository) {
        return new ActivePunishmentService(provider, dataRepository, repository, playerProfileRepository);
    }

    @Singleton
    @Provides
    public PunishmentDataProvider punishmentDataProvider(PunishmentDataRepository punishmentDataRepository) {
        return new PunishmentDataProvider(punishmentDataRepository);
    }

    @Singleton
    @Provides
    public PunishmentDataRepository punishmentDataRepository(EbeanServer ebeanServer) {
        return new PunishmentDataRepository(ebeanServer);
    }

    @Singleton
    @Provides
    public PunishmentRepository punishmentRepository(EbeanServer ebeanServer) {
        return new PunishmentRepository(ebeanServer);
    }

    @Singleton
    @ProvidesIntoSet
    public EntityGroup entityGroup() {
        return new EntityGroup(Arrays.asList(PunishmentRecord.class, PunishmentData.class, ActivePunishmentRecord.class));
    }

    @Singleton
    @Provides
    public PunishmentService punishmentService(PunishmentRepository punishmentRepository, PlayerProfileRepository playerProfileRepository) {
        return new PunishmentService(punishmentRepository, playerProfileRepository);
    }

    @Singleton
    @Provides
    public ProxyKick proxyKick(PlayerRepository repository, MessagingService messagingService) {
        return new ProxyKick(repository, messagingService);
    }
}
