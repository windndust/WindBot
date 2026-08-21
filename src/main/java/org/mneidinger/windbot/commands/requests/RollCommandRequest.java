package org.mneidinger.windbot.commands.requests;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;

public record RollCommandRequest(int numOfDie, String dieType) {

    private static final Predicate<String> DICE_ROLL_VALIDATOR = Pattern.compile("[1-9][0-9]?d(100|30|20|12|10|8|6|4|2)").asMatchPredicate();

    public static RollCommandRequest from(ChatInputInteractionEvent event) {
        String rollOption = event.getOption("die")
                        .flatMap(option -> option.getValue())
                        .map(value -> value.asString())
                        .get();
        
        if(!DICE_ROLL_VALIDATOR.test(rollOption)){
            //how to handle error?
        }
        
        int numOfDie = Integer.parseInt(rollOption.substring(0, rollOption.indexOf("d")));
        String dieType = rollOption.substring(rollOption.indexOf("d"));
        return new RollCommandRequest(numOfDie, dieType);
    }
}
