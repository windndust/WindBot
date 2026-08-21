package org.mneidinger.windbot.commands;

import java.util.List;

import org.mneidinger.windbot.config.WindBotAppProp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import discord4j.core.GatewayDiscordClient;
import discord4j.discordjson.json.ApplicationCommandRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CommandRegistrar {

    private static final Logger log = LoggerFactory.getLogger( CommandRegistrar.class );

    private Mono<GatewayDiscordClient> client;

	private WindBotAppProp wb;

	private List<ApplicationCommandRequest> commands;

    public CommandRegistrar(Mono<GatewayDiscordClient> client, WindBotAppProp wb, List<ApplicationCommandRequest> commands){		
        this.client = client;
		this.wb = wb;
		this.commands = commands;
    }

	@EventListener(ApplicationReadyEvent.class)
	public void registerCommandsTwo(){

		client.flatMapMany(client -> {

			long applicationId = client.getSelfId().asLong();
			var applicationService = client.getRestClient().getApplicationService();

			return Mono.justOrEmpty(wb.serverGuildId())
				.flatMapMany(serverGuildId -> applicationService.bulkOverwriteGuildApplicationCommand(applicationId, serverGuildId, commands)
								.doFirst(() -> log.info("BulkOverwrite - Registering Guild Commands"))
								.doAfterTerminate(() -> log.info("Finished - BulkOverwrite - Registering Guild Commands")))
				.switchIfEmpty( Flux.defer(() -> applicationService.bulkOverwriteGlobalApplicationCommand(applicationId, commands)
									.doFirst(() -> log.info("BulkOverwrite - Registering Global Commands"))
									.doAfterTerminate(() -> log.info("Finished - BulkOverwrite - Registering Global Commands"))));
		})
		.doOnNext(cmd -> log.info("Registering %s command".formatted(cmd.name())))
		.doOnComplete(() -> log.info("Finished all command registrations"))
		.doOnError(error -> log.info("Command failed to register: %s".formatted(error.getMessage())))
		.subscribe();
	}
}
