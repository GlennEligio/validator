package com.glenneligio.dataloadvalidator.dao.dam;


import com.glenneligio.dataloadvalidator.models.dam.ProductAttachment;

import java.util.List;

public interface ProductAtchDao extends Dao {
    // TODO: Need to update the SQL query later
    String FETCH_ALL_PRODUCT_ATCH = "SELECT * FROM ATCHREL";
    List<ProductAttachment> getAllProductAtchDao();
}
