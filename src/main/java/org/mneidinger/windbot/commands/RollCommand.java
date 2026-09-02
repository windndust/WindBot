package org.mneidinger.windbot.commands;

import org.mneidinger.windbot.commands.requests.CommandRequestFactory;
import org.mneidinger.windbot.commands.requests.RollCommandRequest;
import org.mneidinger.windbot.commands.responses.CommandResponse;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class RollCommand extends SlashCommand<RollCommandRequest>{

    @Override
    String getName() {
        return "roll";
    }

    @Override
    CommandRequestFactory<RollCommandRequest> getRequestFactory(){
        return RollCommandRequest::from;
    }

    @Override
    Mono<CommandResponse> execute(RollCommandRequest request) {
        return Mono.just(CommandResponse.text("You rolled %s %ss".formatted(request.numOfDie(), request.dieType())));
    }
}
