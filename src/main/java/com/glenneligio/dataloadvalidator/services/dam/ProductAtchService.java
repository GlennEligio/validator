package com.glenneligio.dataloadvalidator.services.dam;

import com.glenneligio.dataloadvalidator.models.dam.ProductAttachment;
import com.glenneligio.dataloadvalidator.models.dam.XDamManifest;

import java.util.List;
import java.util.Map;

public interface ProductAtchService {
    Map<String, List<ProductAttachment>> getProductAttachmentMap();
    List<ProductAttachment> getUnalignedProductAttachments(List<XDamManifest> xDamManifests);
}
