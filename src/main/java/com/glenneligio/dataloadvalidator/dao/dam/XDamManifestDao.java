package com.glenneligio.dataloadvalidator.dao.dam;

import com.glenneligio.dataloadvalidator.models.dam.XDamManifest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface XDamManifestDao extends Dao {
    String fetchXDamManifestEntries = "SELECT * FROM XDAMMANIFEST ORDER BY PARTNUMBER";
    List<XDamManifest> getAllPartnumberManifest();

}
