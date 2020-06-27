package com.service.product.service;

import com.service.product.dto.ProductDTO;

import java.util.List;

public interface ProductSvc {
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO);

    ProductDTO deleteProduct(String productId);

    ProductDTO fetchProductById(String productId);

    ProductDTO fetchProductStockById(String productId);

    List<ProductDTO> fetchStockInfo();
}
