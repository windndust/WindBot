package org.mneidinger.windbot.commands;

import org.mneidinger.windbot.commands.requests.AboutCommandRequest;
import org.mneidinger.windbot.commands.requests.CommandRequestFactory;
import org.mneidinger.windbot.commands.responses.CommandResponse;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AboutCommand extends SlashCommand<AboutCommandRequest> {

    @Override
    String getName() {
        return "about";
    }

    @Override
    CommandRequestFactory<AboutCommandRequest> getRequestFactory() {
        return AboutCommandRequest::from;
    }

    @Override
    Mono<CommandResponse> execute(AboutCommandRequest request) {
        return Mono.just(CommandResponse.text("A custom discord bot developed by WinDnDusT! Capable of controlling the wind!"));
    }
}
