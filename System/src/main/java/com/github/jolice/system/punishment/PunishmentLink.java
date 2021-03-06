package com.github.jolice.system.punishment;

import com.github.jolice.system.punishment.model.ActivePunishmentType;
import com.github.jolice.system.punishment.repository.PunishmentDataRepository;
import com.github.jolice.system.login.LoginDetails;
import lombok.RequiredArgsConstructor;
import com.github.jolice.system.login.chain.LoginChainLink;
import com.github.jolice.system.login.chain.LoginProcessingException;

/**
 * LoginChain link responsible for checking player's punishment. If player is banned,
 * he gets disconnected from the server with an appropriate disconnect message that describes
 * player's ban reason.
 */
@RequiredArgsConstructor
public class PunishmentLink extends LoginChainLink {

    private final PunishmentChecker punishmentChecker;
    private final PunishmentDataRepository repository;

    @Override
    protected void proceed(LoginDetails loginDetails) {
        this.load(loginDetails);
        this.checkBanned(loginDetails);
        super.proceedNext(loginDetails);
    }

    private void checkBanned(LoginDetails loginDetails) {
        punishmentChecker
                .checkPunishment(loginDetails.getUuid(), ActivePunishmentType.BAN)
                .ifPresent(activePunishmentRecord -> {
                    repository.invalidate(loginDetails.getUuid());
                    throw new LoginProcessingException("You are banned! Reason: " + activePunishmentRecord.getReason());
                });
    }

    private void load(LoginDetails loginDetails) {
        repository
                .findPunishmentData(loginDetails.getUuid())
                .ifPresent(punishmentData -> repository.put(punishmentData.getId(), punishmentData));
    }
}
