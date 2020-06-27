package com.service.product.controller;

import com.service.product.dto.ProductDTO;
import com.service.product.service.ProductSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductSvc productSvc;

    @PostMapping("/v1/product")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO response = productSvc.createProduct(productDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/v1/product")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO response = productSvc.updateProduct(productDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/v1/product/{productId}")
    public ResponseEntity<ProductDTO> deleteProductById(@PathVariable String productId) {
        ProductDTO response = productSvc.deleteProduct(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/v1/product/{productId}")
    public ResponseEntity<ProductDTO> fetchProductById(@PathVariable String productId) {
        ProductDTO productDTO = productSvc.fetchProductById(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @GetMapping("/v1/product/stock/{productId}")
    public ResponseEntity<ProductDTO> fetchProductStockById(@PathVariable String productId) {
        ProductDTO productDTO = productSvc.fetchProductStockById(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @GetMapping("/v1/product/stock")
    public ResponseEntity<List<ProductDTO>> fetchProductStock() {
        List<ProductDTO> productDTOs = productSvc.fetchStockInfo();
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

}
