package org.mneidinger.windbot.config;

import java.util.List;

import org.mneidinger.windbot.discordevent.DiscordEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import reactor.core.publisher.Mono;

@Configuration
public class BeanConfiguration {

	private static final Logger log = LoggerFactory.getLogger( BeanConfiguration.class );

	private WindBotAppProp wb;

	public BeanConfiguration(WindBotAppProp wb){
		this.wb = wb;
	}

	@Bean
	<T extends Event> Mono<GatewayDiscordClient> discordClient(List<DiscordEventListener<T>> eventListeners){		
		Mono<GatewayDiscordClient> gateway = DiscordClientBuilder.create(wb.token())
			.build()
			.login()
			.doOnNext(client -> {
				log.info("Registering %s event listeners".formatted(eventListeners.size()));
				for(DiscordEventListener<T> listener : eventListeners){
					registerListener(client, listener);
				}
				log.info("Done registering event listeners");
			})
			.cache();	
		return gateway;
	}

	private <T extends Event> void registerListener(GatewayDiscordClient client, DiscordEventListener<T> listener){
		client.on(listener.getEventType())
		.flatMap(event -> listener.execute(event))
		.subscribe();
	}

	@Bean
	ApplicationCommandRequest aboutDiscordCommand(){
		return ApplicationCommandRequest.builder()
				.name("about")
				.description("Give about information about this bot")
				.build();
	}

	@Bean
	ApplicationCommandRequest rollDiscordCommand(){
		return ApplicationCommandRequest.builder()
				.name("roll")
				.addOption(ApplicationCommandOptionData.builder()
					.name("die")
					.description("Number of die and type")
					.required(true)
					.type(ApplicationCommandOption.Type.STRING.getValue())
					.build())
				.description("Roll of a die!")
				.build();
	}

}