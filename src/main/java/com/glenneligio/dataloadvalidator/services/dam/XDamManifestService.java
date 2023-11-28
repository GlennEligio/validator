package com.glenneligio.dataloadvalidator.services.dam;

import com.glenneligio.dataloadvalidator.models.dam.Image;
import com.glenneligio.dataloadvalidator.models.dam.ProductAttachment;
import com.glenneligio.dataloadvalidator.models.dam.XDamManifest;

import java.util.List;
import java.util.Map;

public interface XDamManifestService {
    List<XDamManifest> getAllXDamManifest();
    List<XDamManifest> recreateAllImages(List<XDamManifest> xDamManifests);

    XDamManifest recreateImages(XDamManifest xDamManifest);
    List<Image> getMissingImages(Map<String, List<ProductAttachment>> partNumberProductAttachmentMap);
}
