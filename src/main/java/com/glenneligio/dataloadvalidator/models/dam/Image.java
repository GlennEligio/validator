package com.glenneligio.dataloadvalidator.models.dam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class Image {
    private String usage;
    private Integer order;
    private String path;
}
