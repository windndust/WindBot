package org.mneidinger.windbot.discordevent;

import org.mneidinger.windbot.commands.CommandRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

@Service
public class SlashCommandListener implements DiscordEventListener<ChatInputInteractionEvent>{

    private static final Logger log = LoggerFactory.getLogger( SlashCommandListener.class );

    private CommandRouter router;

    public SlashCommandListener(CommandRouter router){
        this.router = router;
    }

    @Override
    public Class<ChatInputInteractionEvent> getEventType() {
        return ChatInputInteractionEvent.class;
    }

    @Override
    public Mono<Void> execute(ChatInputInteractionEvent event) {
        log.info("Processing chat input interaction event");
        return router.route(event);
    }
}
