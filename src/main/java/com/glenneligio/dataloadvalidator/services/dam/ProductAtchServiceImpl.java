package com.glenneligio.dataloadvalidator.services.dam;

import com.glenneligio.dataloadvalidator.models.dam.Image;
import com.glenneligio.dataloadvalidator.models.dam.ProductAttachment;
import com.glenneligio.dataloadvalidator.models.dam.XDamManifest;

import java.util.*;
import java.util.stream.Collectors;

public class ProductAtchServiceImpl implements ProductAtchService{

    private static List<ProductAttachment> productAttachmentsTest = new ArrayList<>();

    static {
        // BLBLPV2M06
        productAttachmentsTest.add(new ProductAttachment("BLBLPV2M06",
                "/cdn-record-files/contactlenses/10eff353-c1a0-464a-8377-afc800d26a9f/BLBLPV2M06__shad__fr2.png",
                "BLBLPV2M06_10eff353-c1a0-464a-8377-afc800d26a9f/BLBLPV2M06__shad__fr2",
                "PDP",
                1));
        productAttachmentsTest.add(new ProductAttachment("BLBLPV2M06",
                "/cdn-record-files/contactlenses/2893eeee-2867-4fc1-830d-afc800d26e53/BLBLPV2M06__shad__lt2.png",
                "BLBLPV2M06_2893eeee-2867-4fc1-830d-afc800d26e53/BLBLPV2M06__shad__lt2",
                "PDP",
                2));
        productAttachmentsTest.add(new ProductAttachment("BLBLPV2M06",
                "/cdn-record-files/contactlenses/d78a78fe-0303-4781-a94d-afc800d27031/BLBLPV2M06__shad__al2.png",
                "BLBLPV2M06_d78a78fe-0303-4781-a94d-afc800d27031/BLBLPV2M06__shad__al2",
                "PDP",
                3));
        productAttachmentsTest.add(new ProductAttachment("BLBLPV2M06",
                "/cdn-record-files/contactlenses/494a2dfe-c207-4993-8c65-afc800d26c77/BLBLPV2M06__shad__fr5.png",
                "BLBLPV2M06_494a2dfe-c207-4993-8c65-afc800d26c77/BLBLPV2M06__shad__fr5",
                "PLP",
                1));
        // BLBLPV2T06
        productAttachmentsTest.add(new ProductAttachment("BLBLPV2T06",
                "/cdn-record-files/contactlenses/174e7eef-448b-45d5-8fae-afc800d2473c/BLBLPV2T06__shad__fr2.png",
                "BLBLPV2T06_174e7eef-448b-45d5-8fae-afc800d2473c/BLBLPV2T06__shad__fr2",
                "PDP",
                1));
        productAttachmentsTest.add(new ProductAttachment("BLBLPV2T06",
                "/cdn-record-files/contactlenses/3704c957-6d46-4889-a06b-afc800d2457d/BLBLPV2T06__shad__lt2.png",
                "BLBLPV2T06_3704c957-6d46-4889-a06b-afc800d2457d/BLBLPV2T06__shad__lt2",
                "PDP",
                2));
        productAttachmentsTest.add(new ProductAttachment("BLBLPV2T06",
                "/cdn-record-files/contactlenses/cc0b950f-0002-4745-9d33-afc800d248d5/BLBLPV2T06__shad__fr5.png",
                "BLBLPV2T06_cc0b950f-0002-4745-9d33-afc800d248d5/BLBLPV2T06__shad__fr5",
                "PLP",
                1));
        productAttachmentsTest.add(new ProductAttachment("BLBLPV2T06",
                "/cdn-record-files/contactlenses/cc0b950f-0002-4745-9d33-afc800d248d5/BLBLPV2T06__shad__fr5.png",
                "BLBLPV2T06_cc0b950f-0002-4745-9d33-afc800d248d5/BLBLPV2T06__shad__fr5",
                "Thumbnail",
                1));
    }

    @Override
    public Map<String, List<ProductAttachment>> getProductAttachmentMap() {
        return productAttachmentsTest.stream().collect(Collectors.groupingBy(ProductAttachment::getPartNumber));
    }

    @Override
    public List<ProductAttachment> getUnalignedProductAttachments(List<XDamManifest> xDamManifests) {
        // GOAL: Get all unaligned ProductAttachments
        // Check if any of the grouping of ProductAttachments have corresponding part number in XDAMMANIFEST
        Map<String, List<ProductAttachment>> partNumberAttachmentsMap = getProductAttachmentMap();
        Set<String> manifestJsonPartNumberSet = xDamManifests.stream().map(XDamManifest::getPartNumber).distinct().collect(Collectors.toSet());
        Set<String> productAtchPartNumberSet = partNumberAttachmentsMap.keySet();
        for(String productAtchPartNumber : productAtchPartNumberSet) {
            if(!manifestJsonPartNumberSet.contains(productAtchPartNumber)) {
                partNumberAttachmentsMap.remove(productAtchPartNumber);
            }
        }
        // Check if any of the ProductAttachments in ManifestJSON images are not aligned
        for(XDamManifest xDamManifest : xDamManifests) {
            List<ProductAttachment> partNumberAttachments = partNumberAttachmentsMap.get(xDamManifest.getPartNumber());
            List<Image> images = xDamManifest.getImages();
            partNumberAttachments = partNumberAttachments.stream().filter(p -> images.stream().noneMatch(image ->
                    p.getUsage().equals(image.getUsage()) &&
                            p.getOrder() == image.getOrder() &&
                            p.getAtchPath().equals(image.getPath()))).collect(Collectors.toList());

        }
        return partNumberAttachmentsMap.values().stream().flatMap(Collection::stream).toList();
    }
}
