package com.glenneligio.dataloadvalidator.services;

import com.glenneligio.dataloadvalidator.models.DbConfiguration;

import java.io.FileNotFoundException;
import java.util.List;

public interface DbConfigurationService {
    List<DbConfiguration> parseYamlFile(String yamlFilePath) throws FileNotFoundException;
    DbConfiguration getDbConfig(List<DbConfiguration> dbConfigurations, String brand, String env, String envType);
}
