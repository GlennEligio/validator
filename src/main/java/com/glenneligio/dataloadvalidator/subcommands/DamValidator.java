package com.glenneligio.dataloadvalidator.subcommands;

import com.glenneligio.dataloadvalidator.models.DbConfiguration;
import com.glenneligio.dataloadvalidator.services.DbConfigurationService;
import com.glenneligio.dataloadvalidator.services.DbConfigurationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@Command(
        name = "dam",
        description = "Validate contents of the XDAMMANFEST to the ATCH* tables"
)
public class DamValidator implements Runnable{

    @Autowired
    private DbConfigurationService dbConfigurationService;

    @Option(names = {"-d", "--dbConfigs"}, description = "YAML file that contains the db configurations")
    private String dbConfigFile;

    @Option(names = {"-b", "--brand"}, description = "Brand of the database to validate, e.g. CLE, VDE, CDI")
    private String brand;

    @Option(names = {"-e", "--env"}, description = "Environment of the database to validate, e.g. INT, UAT, PROD")
    private String env;

    @Option(names = {"-t", "--envType"}, description = "Environment type of the database to validate, e.g. AUTH or LIVE")
    private String envType;

    @Option(names = {"--published"}, description = "Specify whether to validate only published products")
    private Boolean isPublished;

    @Override
    public void run() {
        log.info("Inside DamValidator");
        log.info("General output - dbConfig: {}:", dbConfigFile);
        log.info("Db inputs: - brand: {}, env: {}, envType: {}", brand, env, envType);

        List<DbConfiguration> dbConfigurations;
        try {
            log.info("Getting the dbConfigurations");
            dbConfigurations = dbConfigurationService.parseYamlFile(dbConfigFile);
        } catch (FileNotFoundException e) {
            log.info("Yaml file can't be parsed");
            throw new RuntimeException(e);
        }
        DbConfiguration dbConfiguration = dbConfigurationService.getDbConfig(dbConfigurations, brand, env, envType);
        log.info("Db Configuration: {}", dbConfiguration);


    }
}
