package org.mneidinger.windbot.commands;

import org.mneidinger.windbot.commands.requests.CommandRequestFactory;
import org.mneidinger.windbot.commands.requests.RollCommandRequest;
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
    Mono<String> execute(RollCommandRequest request) {
        //Placeholder while I puzzle out the CommandRouter and injecting the request object
        return Mono.just("You Rolled %s %s".formatted(request.numOfDie(), request.dieType()));
    }
}
