package org.mneidinger.discordbot;

import java.util.List;

import org.mneidinger.discordbot.BotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;

import org.mneidinger.discordbot.event.*;

@Configuration
public class BotConfiguration {

	private static final Logger log = LoggerFactory.getLogger( BotConfiguration.class );

	@Value("${token}")
	private String token;

	@Bean
	<T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {

		try {
			GatewayDiscordClient client = DiscordClientBuilder.create(token)
				.build()
				.login()
				.block();

			for(EventListener<T> listener : eventListeners) {
				client.on(listener.getEventType())
					.flatMap(listener::execute)
					.onErrorResume(listener::handleError)
					.subscribe();
			}

			return client;
		}catch(Exception e) {
			log.error("Exception!: ", e);
		}
		return null;
	}
}