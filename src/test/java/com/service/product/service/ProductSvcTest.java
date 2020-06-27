package com.service.product.service;

import com.service.product.builder.ProductBuilder;
import com.service.product.dto.ProductDTO;
import com.service.product.model.Product;
import com.service.product.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class ProductSvcTest {

    @InjectMocks
    private ProductSvcImpl productSvc;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnProductInfoOnValidProfileId() {
        String productId = "product-id";
        Product product = ProductBuilder.getProduct(productId);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductDTO productDTO = productSvc.fetchProductById(productId);
        Assert.assertEquals(product.getId(), productDTO.getId());
        Assert.assertEquals(product.getName(), productDTO.getName());
        Assert.assertEquals(product.getDescription(), productDTO.getDescription());
        Assert.assertEquals(product.getCost(), productDTO.getCost());
        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
    }

    @Test
    public void shouldReturnEmptyProductOnEmptyProductId() {
        String productId = "";
        Assert.assertNull(productSvc.fetchProductById(productId));
        Mockito.verify(productRepository, Mockito.times(0)).findById(productId);
    }

    @Test
    public void shouldReturnEmptyProductOnNullProductId() {
        String productId = null;
        Assert.assertNull(productSvc.fetchProductById(productId));
        Mockito.verify(productRepository, Mockito.times(0)).findById(productId);
    }

    @Test
    public void shouldReturnProductStockInfoOnValidProfileId() {
        String productId = "product-id";
        Product product = ProductBuilder.getProduct(productId);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductDTO productDTO = productSvc.fetchProductStockById(productId);
        Assert.assertEquals(product.getId(), productDTO.getId());
        Assert.assertEquals(product.getName(), productDTO.getName());
        Assert.assertEquals(product.getDescription(), productDTO.getDescription());
        Assert.assertEquals(product.getCost(), productDTO.getCost());
        Assert.assertEquals(product.getProductQuantity().getQuantity(), productDTO.getQuantity());
        Long totalCost = product.getCost() * product.getProductQuantity().getQuantity();
        Assert.assertEquals(totalCost, productDTO.getTotalCost());
        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
    }

    @Test
    public void shouldReturnEmptyProductStockOnEmptyProductId() {
        String productId = "";
        Assert.assertNull(productSvc.fetchProductStockById(productId));
        Mockito.verify(productRepository, Mockito.times(0)).findById(productId);
    }

    @Test
    public void shouldReturnEmptyProductStockOnNullProductId() {
        String productId = null;
        Assert.assertNull(productSvc.fetchProductStockById(productId));
        Mockito.verify(productRepository, Mockito.times(0)).findById(productId);
    }


    @Test
    public void shouldReturnAllProductStockInfo() {
        String productId = "product-id";
        Product product = ProductBuilder.getProduct(productId);
        Mockito.when(productRepository.findAll()).thenReturn(Collections.singleton(product));
        List<ProductDTO> productDTOs = productSvc.fetchStockInfo();
        productDTOs.forEach(productDTO -> {
            Assert.assertEquals(product.getId(), productDTO.getId());
            Assert.assertEquals(product.getName(), productDTO.getName());
            Assert.assertEquals(product.getDescription(), productDTO.getDescription());
            Assert.assertEquals(product.getCost(), productDTO.getCost());
            Assert.assertEquals(product.getProductQuantity().getQuantity(), productDTO.getQuantity());
            Long totalCost = product.getCost() * product.getProductQuantity().getQuantity();
            Assert.assertEquals(totalCost, productDTO.getTotalCost());
        });

        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldReturnEmptyProductStockIfNoStock() {
        Mockito.when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<ProductDTO> productDTOs = productSvc.fetchStockInfo();
        Assert.assertTrue(productDTOs.isEmpty());
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldDeleteProductByProductId() {
        String productId = "product-id";
        Product product = ProductBuilder.getProduct(productId);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductDTO productDTO = productSvc.deleteProduct(productId);
        Assert.assertEquals(product.getId(), productDTO.getId());
        Assert.assertEquals(product.getName(), productDTO.getName());
        Assert.assertEquals(product.getDescription(), productDTO.getDescription());
        Assert.assertEquals(product.getCost(), productDTO.getCost());
        Assert.assertEquals(product.getProductQuantity().getQuantity(), productDTO.getQuantity());
        Long totalCost = product.getCost() * product.getProductQuantity().getQuantity();
        Assert.assertEquals(totalCost, productDTO.getTotalCost());
        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }

    @Test
    public void shouldNotDeleteProductIfProductNotFound() {
        String productId = "product-id";
        Product product = ProductBuilder.getProduct(productId);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());
        Assert.assertNull(productSvc.deleteProduct(productId));
        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
        Mockito.verify(productRepository, Mockito.times(0)).delete(product);
    }

    @Test
    public void shouldNotDeleteProductIfProductIdIsEmpty() {
        String productId = "";
        Assert.assertNull(productSvc.deleteProduct(productId));
        Mockito.verify(productRepository, Mockito.times(0)).findById(productId);
    }

    @Test
    public void shouldNotDeleteProductIfProductIdIsNull() {
        String productId = null;
        Assert.assertNull(productSvc.deleteProduct(productId));
        Mockito.verify(productRepository, Mockito.times(0)).findById(productId);
    }

    @Test
    public void shouldCreateProductOnValidInput() {
        String productId = "product-id";
        ProductDTO requestDTO = ProductBuilder.getProductDTO(productId);
        Product product = ProductBuilder.getProduct(productId + "-product");
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDTO productDTO = productSvc.createProduct(requestDTO);
        Assert.assertEquals(product.getId(), productDTO.getId());
        Assert.assertNotEquals(requestDTO.getId(), productDTO.getId());
        Assert.assertEquals(product.getName(), productDTO.getName());
        Assert.assertEquals(product.getDescription(), productDTO.getDescription());
        Assert.assertEquals(product.getCost(), productDTO.getCost());
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldUpdateProductOnValidInput() {
        String productId = "product-id";
        ProductDTO requestDTO = ProductBuilder.getProductDTO(productId);
        requestDTO.setCost(200l);
        requestDTO.setQuantity(500);
        Product product = ProductBuilder.getProduct(productId);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        ProductDTO productDTO = productSvc.updateProduct(requestDTO);
        Assert.assertEquals(requestDTO.getId(), productDTO.getId());
        Assert.assertEquals(requestDTO.getName(), productDTO.getName());
        Assert.assertEquals(requestDTO.getDescription(), productDTO.getDescription());
        Assert.assertEquals(requestDTO.getCost(), productDTO.getCost());
        Assert.assertEquals(requestDTO.getQuantity(), productDTO.getQuantity());
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
    }

    @Test
    public void shouldNotUpdateProductIfNotPresent() {
        String productId = "product-id";
        ProductDTO requestDTO = ProductBuilder.getProductDTO(productId);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());
        ProductDTO productDTO = productSvc.updateProduct(requestDTO);
        Assert.assertNull(productDTO);
        Mockito.verify(productRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
    }
}
