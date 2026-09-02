package org.mneidinger.windbot.commands.responses;

import java.util.List;

import discord4j.core.object.component.LayoutComponent;

public record CommandResponse(
    String content,
    boolean ephemeral,
    List<LayoutComponent> components
) {

    public static CommandResponse text(String message){
        return new CommandResponse(message, false, List.of());
    }

    public static CommandResponse ephemeral(String message){
        return new CommandResponse(message, true, List.of());
    }

    public static CommandResponse withComponents(String message, List<LayoutComponent> components){
        return new CommandResponse(message, false, components);
    }
}