package org.mneidinger.windbot.config;

import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix="windbot")
public record WindBotAppProp (
    @NotBlank(message="The discord bot token is missing. Please provide a 'windbot.token' in your environment or command-line arguments. Create a token on discord.com/developers/home ")
    String token,
    Optional<Long> serverGuildId    
){}
