package com.glenneligio.dataloadvalidator.commands;

import com.glenneligio.dataloadvalidator.subcommands.DamValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Slf4j
@Component
@Command(
        name = "validate",
        mixinStandardHelpOptions = true,
        description = "Used to validate the result of dataload",
        subcommands = {DamValidator.class}
)
public class DataloadValidator implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        log.info("Inside DataloadValidator");
        return -23;
    }
}
