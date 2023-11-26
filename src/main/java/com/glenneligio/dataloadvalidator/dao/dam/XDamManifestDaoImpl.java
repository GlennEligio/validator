package com.glenneligio.dataloadvalidator.dao.dam;

import com.glenneligio.dataloadvalidator.models.dam.XDamManifest;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class XDamManifestDaoImpl implements XDamManifestDao{
    private String hostname, username, password, dbName;
    private int port;

    public XDamManifestDaoImpl(String hostname, String username, String password, String dbName, int port) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.dbName = dbName;
        this.port = port;
    }

    @Override
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:oracle:thin:@" + hostname + ":"+port+"/"+dbName, username, password);
    }

    @Override
    public List<XDamManifest> getAllPartnumberManifest() {
        List<XDamManifest> xDamManifests = new ArrayList<>();
        try(Connection connection = createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(fetchXDamManifestEntries);
            ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String partnumber = resultSet.getString("PARTNUMBER");
                String manifestJson = resultSet.getString("MANIFESTJSON");
                String lastUpdated = resultSet.getString("LASTUPDATED");
                xDamManifests.add(new XDamManifest(partnumber, manifestJson, lastUpdated));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xDamManifests;
    }
}
