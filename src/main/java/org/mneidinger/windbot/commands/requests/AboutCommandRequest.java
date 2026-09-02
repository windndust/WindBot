package org.mneidinger.windbot.commands.requests;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;

public record AboutCommandRequest() {

    public static AboutCommandRequest from(ChatInputInteractionEvent event){
        return new AboutCommandRequest();
    }
}
