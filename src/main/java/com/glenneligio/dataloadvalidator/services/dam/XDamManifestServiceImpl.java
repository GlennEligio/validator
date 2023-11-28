package com.glenneligio.dataloadvalidator.services.dam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glenneligio.dataloadvalidator.models.dam.Image;
import com.glenneligio.dataloadvalidator.models.dam.ProductAttachment;
import com.glenneligio.dataloadvalidator.models.dam.XDamManifest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class XDamManifestServiceImpl implements XDamManifestService{

    private static List<XDamManifest> xDamManifestsTest = new ArrayList<>();

    static {
        // BLBLPV2M06
        xDamManifestsTest.add(new XDamManifest("BLBLPV2M06",
                "[{\"type\":\"\",\"usage\":\"PLP\",\"order\":1,\"path\":\"/cdn-record-files/contactlenses/494a2dfe-c207-4993-8c65-afc800d26c77/BLBLPV2M06__shad__fr5.png\",\"view\":\"auto\"},{\"type\":\"\",\"usage\":\"PDP Carousel\",\"order\":1,\"path\":\"/cdn-record-files/contactlenses/10eff353-c1a0-464a-8377-afc800d26a9f/BLBLPV2M06__shad__fr2.png\",\"view\":\"auto\"},{\"type\":\"\",\"usage\":\"PDP Carousel\",\"order\":2,\"path\":\"/cdn-record-files/contactlenses/2893eeee-2867-4fc1-830d-afc800d26e53/BLBLPV2M06__shad__lt2.png\",\"view\":\"auto\"},{\"type\":\"\",\"usage\":\"PDP Carousel\",\"order\":3,\"path\":\"/cdn-record-files/contactlenses/d78a78fe-0303-4781-a94d-afc800d27031/BLBLPV2M06__shad__al2.png\",\"view\":\"auto\"}]",
                "2023-11-20 08:29:29.384"));
        // BLBLPV2T06
        xDamManifestsTest.add(new XDamManifest("BLBLPV2T06",
                "[{\"marker\":\"\",\"type\":\"\",\"usage\":\"PLP\",\"order\":1,\"path\":\"/cdn-record-files/contactlenses/cc0b950f-0002-4745-9d33-afc800d248d5/BLBLPV2T06__shad__fr5.png\",\"view\":\"auto\"},{\"marker\":\"\",\"type\":\"\",\"usage\":\"Thumbnail\",\"order\":1,\"path\":\"/cdn-record-files/contactlenses/cc0b950f-0002-4745-9d33-afc800d248d5/BLBLPV2T06__shad__fr5.png\",\"view\":\"auto\"},{\"marker\":\"\",\"type\":\"\",\"usage\":\"PDP Carousel\",\"order\":1,\"path\":\"/cdn-record-files/contactlenses/174e7eef-448b-45d5-8fae-afc800d2473c/BLBLPV2T06__shad__fr2.png\",\"view\":\"auto\"},{\"marker\":\"\",\"type\":\"\",\"usage\":\"PDP Carousel\",\"order\":2,\"path\":\"/cdn-record-files/contactlenses/3704c957-6d46-4889-a06b-afc800d2457d/BLBLPV2T06__shad__lt2.png\",\"view\":\"auto\"}]",
                "2023-11-20 08:29:29.384"));
    }

    @Override
    public List<XDamManifest> getAllXDamManifest() {
        return xDamManifestsTest;
    }

    @Override
    public List<XDamManifest> recreateAllImages(List<XDamManifest> xDamManifests) {
        return xDamManifests.stream().map(this::recreateImages).toList();
    }

    @Override
    public XDamManifest recreateImages(XDamManifest xDamManifest) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            List<Image> images = mapper.readValue(xDamManifest.getManifestJson(), new TypeReference<>() {});
            xDamManifest.setImages(images);
        } catch (Exception e) {
            log.info("Something went wrong");
            e.printStackTrace();
        }
        return xDamManifest;
    }

    @Override
    public List<Image> getMissingImages(Map<String, List<ProductAttachment>> partNumberProductAttachmentMap) {
        List<XDamManifest> xDamManifests = recreateAllImages(getAllXDamManifest());
        List<Image> missingImages = new ArrayList<>();
        for(XDamManifest xDamManifest : xDamManifests) {
            List<ProductAttachment> productAttachments = partNumberProductAttachmentMap.get(xDamManifest.getPartNumber());
            for(Image image: xDamManifest.getImages()) {
                if(productAttachments.stream().noneMatch(p ->
                        p.getUsage().equals(image.getUsage()) &&
                        p.getOrder() == image.getOrder() &&
                        p.getAtchPath().equals(image.getPath()))) {
                    missingImages.add(image);
                }
            }
        }
        return missingImages;
    }
}
