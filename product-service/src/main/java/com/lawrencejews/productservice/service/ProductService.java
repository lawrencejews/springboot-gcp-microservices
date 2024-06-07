package com.lawrencejews.productservice.service;

import com.lawrencejews.productservice.models.ProductRequest;
import com.lawrencejews.productservice.models.ProductResponse;

public interface ProductService  {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
