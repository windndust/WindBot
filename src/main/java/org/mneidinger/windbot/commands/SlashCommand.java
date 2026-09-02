package org.mneidinger.windbot.commands;

import org.mneidinger.windbot.commands.requests.CommandRequestFactory;
import org.mneidinger.windbot.commands.responses.CommandResponse;

import reactor.core.publisher.Mono;

public abstract class SlashCommand<T> {

    /**
     * A simple name used by the CommandRouter internally to reference the command object.
     * Needs to match the name set in the ApplicationCommandRequest bean
     * 
     * @return
     */
    abstract String getName();

    /**
     * 
     * @return
     */
    abstract CommandRequestFactory<T> getRequestFactory();

    /**
     * 
     * @param t
     * @return
     */
    abstract Mono<CommandResponse> execute(T request);
}
