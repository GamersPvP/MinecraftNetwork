package io.riguron.system.stream;

import io.riguron.system.internalization.Message;
import io.riguron.system.internalization.Message;


public interface Broadcast {

    /**
     * Sends a message to all online players.
     *
     * @param message raw message
     */
    void broadcast(String message);

    /**
     * Sends an internalized to all online players.
     * Each player will receive a translation of the message
     * depending on his language.
     *
     * @param message internalized message
     */
    void broadcast(Message message);
}
