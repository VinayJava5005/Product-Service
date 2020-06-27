package com.service.product.service;

import com.service.product.dto.ProductDTO;
import com.service.product.model.Product;
import com.service.product.model.ProductQuantity;
import com.service.product.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductSvcImpl implements ProductSvc {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        String id = UUID.randomUUID().toString();
        productDTO.setId(id);
        Product product = new Product();
        ProductQuantity productQuantity = new ProductQuantity();
        product.setProductQuantity(productQuantity);
        populateProductInfo(productDTO, product);
        Product savedProduct = productRepository.save(product);
        return populateProductInfo(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        ProductDTO response = null;
        String id = productDTO.getId();
        if (!StringUtils.isEmpty(id)) {
            Optional<Product> result = productRepository.findById(id);
            if (result.isPresent()) {
                Product product = result.get();
                populateProductInfo(productDTO, product);
                Product savedProduct = productRepository.save(product);
                response = populateProductInfo(savedProduct);
            }

        }
        return response;
    }

    @Override
    public ProductDTO deleteProduct(String productId) {
        ProductDTO productDTO = null;
        if (!StringUtils.isEmpty(productId)) {
            Optional<Product> result = productRepository.findById(productId);
            if (result.isPresent()) {
                Product product = result.get();
                productRepository.delete(product);
                productDTO = populateProductInfo(product);
            }
        }
        return productDTO;
    }

    @Override
    public ProductDTO fetchProductById(String productId) {
        ProductDTO productDTO = null;

        if (!StringUtils.isEmpty(productId)) {

            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                productDTO = new ProductDTO();
                BeanUtils.copyProperties(product.get(), productDTO);
            }
        }
        return productDTO;
    }

    @Override
    public ProductDTO fetchProductStockById(String productId) {

        ProductDTO productDTO = null;
        if (!StringUtils.isEmpty(productId)) {
            Optional<Product> result = productRepository.findById(productId);
            if (result.isPresent()) {
                Product product = result.get();
                productDTO = populateProductInfo(product);
            }
        }
        return productDTO;
    }

    @Override
    public List<ProductDTO> fetchStockInfo() {
        Iterable<Product> result = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        result.forEach(product -> {
            ProductDTO productDTO = populateProductInfo(product);
            productDTOs.add(productDTO);
        });
        return productDTOs;
    }

    private ProductDTO populateProductInfo(Product product) {
        ProductDTO productDTO;
        productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        ProductQuantity productQuantity = product.getProductQuantity();
        productDTO.setQuantity(productQuantity.getQuantity());
        productDTO.setTotalCost(productDTO.getQuantity() * productDTO.getCost());
        return productDTO;
    }

    private void populateProductInfo(ProductDTO productDTO, Product product) {
        BeanUtils.copyProperties(productDTO, product);
        ProductQuantity productQuantity = product.getProductQuantity();
        productQuantity.setQuantity(productDTO.getQuantity());
        productQuantity.setProduct(product);
        product.setProductQuantity(productQuantity);
    }
}
