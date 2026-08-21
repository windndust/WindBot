package org.mneidinger.windbot.commands;

import org.mneidinger.windbot.commands.requests.CommandRequestFactory;

import reactor.core.publisher.Mono;

public abstract class SlashCommand<T> {

    abstract String getName();

    abstract CommandRequestFactory<T> getRequestFactory();

    abstract Mono<String> execute(T t);
}
