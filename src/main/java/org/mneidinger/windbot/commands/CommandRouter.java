package org.mneidinger.windbot.commands;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mneidinger.windbot.commands.requests.CommandRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandOption;
import reactor.core.publisher.Mono;

@Service
public class CommandRouter {

    private static final Logger log = LoggerFactory.getLogger( CommandRouter.class );

    private Map<String, SlashCommand<?>> commands;

    public CommandRouter(List<SlashCommand<?>> slashCommands){
        this.commands = slashCommands
                        .stream()
                        .peek(cmd -> log.info(String.format("Cmd %s", cmd.getName())))
                        .collect(Collectors.toMap(s -> s.getName(), cmd -> cmd));
    }

    public Mono<Void> route(ChatInputInteractionEvent event){
        StringBuilder name = convertCommandNameToMapKey(event);        

        log.info(String.format("Routing command and potential subcommand: %s", name));

        SlashCommand<?> command = commands.get(name.toString());
        if(command==null){
            return event.reply("Unknown command").withEphemeral(true);
        }
        return executeCommandHelper(command, event);
    }

    private StringBuilder convertCommandNameToMapKey(ChatInputInteractionEvent event) {
        StringBuilder name = new StringBuilder(event.getCommandName());
        
        if(isThereOneSubcommand(event)){
            String subcommandName = event.getOptions().get(0).getName();
            name.append("_")
            .append(subcommandName);
        }
        return name;
    }

    private boolean isThereOneSubcommand(ChatInputInteractionEvent event) {
        return event.getOptions().size()==1 && event.getOptions().get(0).getType()==ApplicationCommandOption.Type.SUB_COMMAND;
    }

    private <R> Mono<Void> executeCommandHelper(SlashCommand<R> command, ChatInputInteractionEvent event){
        CommandRequestFactory<R> requestFactory = command.getRequestFactory();
        R requestBody = requestFactory.parse(event);

        return command.execute(requestBody)
                    .flatMap(commandResponse -> event.reply()
                        .withContent(commandResponse.content())
                        .withComponents(commandResponse.components())
                        .withEphemeral(commandResponse.ephemeral()));
    }
}
