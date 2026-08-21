package org.mneidinger.windbot.commands.requests;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;

@FunctionalInterface
public interface CommandRequestFactory<T> {

    T parse(ChatInputInteractionEvent event);
}
