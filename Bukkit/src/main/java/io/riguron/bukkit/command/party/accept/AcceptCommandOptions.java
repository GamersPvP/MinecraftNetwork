package io.riguron.bukkit.command.party.accept;

import io.riguron.command.base.CommandOptions;
import io.riguron.system.internalization.Message;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AcceptCommandOptions implements CommandOptions {

    @Override
    public String getBody() {
        return "accept";
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.empty();
    }

    @Override
    public Message getDescription() {
        return new Message("party.command.accept.description");
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("a");
    }

    @Override
    public Message getUsage() {
        return new Message("party.command.accept.usage");
    }

    @Override
    public int getMinimumNumberOfArguments() {
        return 1;
    }

    @Override
    public int getMaximumNumberOfArguments() {
        return 1;
    }
}
