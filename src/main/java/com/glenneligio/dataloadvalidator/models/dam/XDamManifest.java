package com.glenneligio.dataloadvalidator.models.dam;

import lombok.Data;

import java.util.List;

@Data
public class XDamManifest {
    private String partNumber;
    private String manifestJson;
    private String lastUpdated;
    private List<Image> images;

    public XDamManifest(String partNumber, String manifestJson, String lastUpdated) {
        this.partNumber = partNumber;
        this.manifestJson = manifestJson;
        this.lastUpdated = lastUpdated;
    }
}
