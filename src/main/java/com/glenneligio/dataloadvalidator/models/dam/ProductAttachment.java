package com.glenneligio.dataloadvalidator.models.dam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttachment {
    private String partNumber;
    private String atchPath;
    private String atchIdentifier;
    private String usage;
    private int order;
}
