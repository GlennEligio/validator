package com.glenneligio.dataloadvalidator.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbConfiguration {

    private String brand_name;
    private String environment;
    private String environment_type;
    private Database database;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Database {
        private String host;
        private int port;
        private String username;
        private String password;
        private String name;
    }
}
