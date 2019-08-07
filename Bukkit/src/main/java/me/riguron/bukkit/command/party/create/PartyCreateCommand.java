package me.riguron.bukkit.command.party.create;

import me.riguron.bukkit.command.party.PartySubCommand;
import me.riguron.command.base.CommandOptions;
import me.riguron.system.internalization.Message;
import me.riguron.system.party.PartyService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PartyCreateCommand extends PartySubCommand {

    public PartyCreateCommand(PartyService partyService) {
        super(partyService, (ps, commandExecution) -> ps.create(commandExecution.getSender().getName()));
    }

}
