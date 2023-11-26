package com.glenneligio.dataloadvalidator.dao.dam;

import java.sql.Connection;
import java.sql.SQLException;

public interface Dao {
    Connection createConnection() throws SQLException;
}
