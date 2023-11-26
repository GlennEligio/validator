package com.glenneligio.dataloadvalidator.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glenneligio.dataloadvalidator.models.DbConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DbConfigurationServiceImpl implements DbConfigurationService{

    @Override
    public List<DbConfiguration> parseYamlFile(String yamlFilePath) throws FileNotFoundException {
        log.info("Db config yaml file: {}",yamlFilePath);
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(yamlFilePath);
        Map<String, List<DbConfiguration>> dbConfigs = yaml.load(inputStream);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(dbConfigs.get("configuration"), new TypeReference<>() {});
    }

    @Override
    public DbConfiguration getDbConfig(List<DbConfiguration> dbConfigurations,String brand, String env, String envType) {
        return dbConfigurations.stream().filter(dbConfig ->
                dbConfig.getBrand_name().equals(brand) && dbConfig.getEnvironment().equals(env) && dbConfig.getEnvironment_type().equals(envType)
        ).findFirst().orElse(null);
    }
}
