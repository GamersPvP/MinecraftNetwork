package me.riguron.system.dialog.send;

import lombok.RequiredArgsConstructor;
import me.riguron.system.dialog.MessageSendResponse;
import me.riguron.system.dialog.PrivateMessage;
import me.riguron.system.punishment.PunishmentChecker;
import me.riguron.system.punishment.model.ActivePunishmentType;

import java.util.UUID;

/**
 * Service for exchanging private messages between the players.
 * Since the algorithm is large, it is broken down into multiple classes,
 * each representing a certain stage of algorithm. These are:
 * <p>
 * 1. SendPrivateMessage
 * 2. FindOnlinePlayer
 * 3. FindPlayerProfile
 * 4. CheckTarget
 * 5. FinalStage
 * <p>
 * This class represents an initial state of algorithm. A private message can be sent
 * only when player is not muted. If he is, no other stages of the algorithm do make sense,
 * so at first it's checked whether the player is muted.
 */
@RequiredArgsConstructor
public class SendPrivateMessage {

    private final PunishmentChecker punishmentChecker;
    private final FindOnlinePlayer findOnlinePlayer;

    public MessageSendResponse send(PrivateMessage message) {
        return this.isMuted(message.getFrom()) ? MessageSendResponse.MUTED : findOnlinePlayer.findAndSend(message);
    }

    private boolean isMuted(UUID uuid) {
        return punishmentChecker.checkPunishment(uuid, ActivePunishmentType.MUTE).isPresent();
    }

}
