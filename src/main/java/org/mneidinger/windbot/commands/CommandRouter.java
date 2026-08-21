package org.mneidinger.windbot.commands;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mneidinger.windbot.commands.requests.CommandRequestFactory;
import org.springframework.stereotype.Service;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

@Service
public class CommandRouter {

    private Map<String, SlashCommand<?>> commands;

    public CommandRouter(List<SlashCommand<?>> slashCommands){
        this.commands = slashCommands.stream().collect(Collectors.toMap(s -> s.getName(), cmd -> cmd));
    }

    public Mono<Void> route(ChatInputInteractionEvent event){
        SlashCommand<?> command = commands.get(event.getCommandName());

        if(command==null){
            return event.reply("Unknown command").withEphemeral(true);
        }
        return executeCommandHelper(command, event);
    }

    private <R> Mono<Void> executeCommandHelper(SlashCommand<R> command, ChatInputInteractionEvent event){
        CommandRequestFactory<R> requestFactory = command.getRequestFactory();

        R requestBody = requestFactory.parse(event);

        return command.execute(requestBody).flatMap(reply -> event.reply(reply));
    }
}
