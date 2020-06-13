package com.github.jolice.system.player.repository;

import com.github.jolice.system.exception.PlayerNotFoundException;
import com.github.jolice.system.player.CachingPlayerRepository;
import com.github.jolice.system.player.PlayerAssociation;
import com.github.jolice.system.player.PlayerProfile;
import com.github.jolice.system.player.PlayerProfileRepository;
import com.github.jolice.system.player.specification.PlayerNameSpecification;
import com.github.jolice.system.player.specification.UUIDSpecification;
import com.github.jolice.system.test.RepositoryTest;
import io.ebean.EbeanServer;
import com.github.jolice.persistence.specification.Specification;
import org.junit.After;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PlayerProfileRepositoryTest extends RepositoryTest<PlayerProfileRepository> {

    private static final String NAME = "name";

    @Test
    public void whenFindByUUIDThenFound() {
        PlayerProfile profile = new PlayerProfile(UUID.randomUUID(), NAME);
        testFindBy(new UUIDSpecification(profile.getUuid()), profile);
    }

    @Test
    public void whenFindByNameThenFound() {
        PlayerProfile profile = new PlayerProfile(UUID.randomUUID(), NAME);
        testFindBy(new PlayerNameSpecification(NAME), profile);
    }

    @Test
    public void whenFindPreferencesAssociationThenLoaded() {
        PlayerProfile profile = new PlayerProfile(UUID.randomUUID(), NAME);
        PlayerProfile found = testFindBy(new UUIDSpecification(profile.getUuid()), profile, PlayerAssociation.PREFERENCES);
        runAndAssertQueries(0, () -> assertTrue(found.getPlayerPreferences().isPartyRequests()));
    }

    @Test
    public void whenFindPurchasesThenFound() {
        PlayerProfile profile = new PlayerProfile(UUID.randomUUID(), NAME);
        PlayerProfile found = testFindBy(new UUIDSpecification(profile.getUuid()), profile, PlayerAssociation.PURCHASES);
        runAndAssertQueries(0, () -> assertFalse(
                found.getPurchases().hasPurchase(1)));
    }

    @Test(expected = PlayerNotFoundException.class)
    public void whenGetInexistentThenThrown() {
        repository.get(UUID.randomUUID());
    }

    @Test(expected = PlayerNotFoundException.class)
    public void whenInvalidateThenRemoved() {

        UUID uuid = UUID.randomUUID();
        PlayerProfile pp = mock(PlayerProfile.class);
        repository.put(uuid, pp);
        assertEquals(repository.get(uuid), pp);
        repository.invalidate(uuid);
        repository.get(uuid);
    }


    @Test
    public void findID() {
        UUID uuid = UUID.randomUUID();
        PlayerProfile pp = new PlayerProfile(uuid, NAME);
        repository.save(pp);
        final Optional<UUID> id = repository.findId(new PlayerNameSpecification(NAME));
        assertTrue(id.isPresent());
        id.ifPresent(found -> assertEquals(uuid, found));
    }


    @After
    public void cleanUp() {

        ebean.createQuery(PlayerProfile.class).delete();
    }

    private PlayerProfile testFindBy(Specification specification, PlayerProfile profile, PlayerAssociation... associations) {
        repository.save(profile);
        final PlayerProfile loadedProfile = runAndAssertQueries(1, () -> repository.find(specification, associations).orElseThrow(PlayerNotFoundException::new));
        assertNotNull(loadedProfile);
        assertEquals(profile, loadedProfile);
        return loadedProfile;
    }


    @Override
    protected PlayerProfileRepository createRepository(EbeanServer server) {
        return new CachingPlayerRepository(ebean);
    }

}