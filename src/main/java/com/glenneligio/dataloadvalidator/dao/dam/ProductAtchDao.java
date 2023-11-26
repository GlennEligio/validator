package com.glenneligio.dataloadvalidator.dao.dam;


import com.glenneligio.dataloadvalidator.models.dam.ProductAttachment;

import java.util.List;

public interface ProductAtchDao extends Dao {
    String FETCH_ALL_PRODUCT_ATCH =
    List<ProductAttachment> getAllProductAtchDao();
}
