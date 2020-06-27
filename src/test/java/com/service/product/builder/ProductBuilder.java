package com.service.product.builder;

import com.service.product.dto.ProductDTO;
import com.service.product.model.Product;
import com.service.product.model.ProductQuantity;

public class ProductBuilder {
    private ProductBuilder() {

    }

    public static ProductDTO getProductDTO(String productId) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName("product-name");
        productDTO.setDescription("product-description");
        productDTO.setCost(10l);
        productDTO.setQuantity(10);
        productDTO.setTotalCost(productDTO.getCost() * productDTO.getQuantity());
        return productDTO;
    }

    public static Product getProduct(String productId) {
        Product product = new Product();
        product.setId(productId);
        product.setName("product-name");
        product.setDescription("product-description");
        product.setCost(10l);
        ProductQuantity productQuantity = new ProductQuantity();
        productQuantity.setQuantity(10);
        product.setProductQuantity(productQuantity);
        productQuantity.setProduct(product);
        return product;
    }
}
