package com.lawrencejews.productservice.service;

import com.lawrencejews.productservice.model.ProductRequest;
import com.lawrencejews.productservice.model.ProductResponse;

public interface ProductService  {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);
}
