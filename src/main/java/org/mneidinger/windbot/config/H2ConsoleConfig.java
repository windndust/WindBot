package org.mneidinger.windbot.config;

import java.sql.SQLException;

import org.h2.tools.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("local-h2")
public class H2ConsoleConfig {

    private static final Logger log = LoggerFactory.getLogger( H2ConsoleConfig.class );
    
    private Server webServer;

    private String dbUsername;

    private String r2dbcUrl;

    public H2ConsoleConfig(@Value("${spring.r2dbc.username}") String dbUsername, @Value("${spring.r2dbc.url}") String r2dbcUrl){
        this.dbUsername = dbUsername;
        this.r2dbcUrl = r2dbcUrl;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws SQLException {

        String jdbcUrl = r2dbcUrl.replace("r2dbc:h2:mem:///", "jdbc:h2:mem:");

        log.info(String.format("DB URL: %S, Username: %s", jdbcUrl, dbUsername));


        this.webServer = Server.createWebServer("-webPort", "8082", "-webAllowOthers").start();
        log.info(String.format("H2 Web Console successfully started on port: %s", webServer.getPort()));
    }

    @EventListener(ContextClosedEvent.class)
    public void stop(){
        if(this.webServer != null){
            this.webServer.stop();
            log.info("H2 Web Console stopped.");
        }
    }
}
