package com.service.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.product.builder.ProductBuilder;
import com.service.product.dto.ProductDTO;
import com.service.product.service.ProductSvc;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String productServiceUrl = "/product/v1/product";
    private String productStockUrl = "/product/v1/product/stock";

    @MockBean
    private ProductSvc productSvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldFetchProductInfoByProductId() throws Exception {
        String productId = "product-id";
        ProductDTO productDTO = ProductBuilder.getProductDTO(productId);
        Mockito.when(productSvc.fetchProductById(productId)).thenReturn(productDTO);
        String url = productServiceUrl + "/" + productId;
        this.mockMvc.perform(MockMvcRequestBuilders.get(url)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(productId)));
    }

    @Test
    public void shouldReturnEmptyResponseIfProductNotPresent() throws Exception {
        String productId = "product-id";
        Mockito.when(productSvc.fetchProductById(productId)).thenReturn(null);
        String url = productServiceUrl + "/" + productId;
        this.mockMvc.perform(MockMvcRequestBuilders.get(url)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.isEmptyOrNullString()));
    }

    @Test
    public void shouldFetchProductStockByProductId() throws Exception {
        String productId = "product-id";
        ProductDTO productDTO = ProductBuilder.getProductDTO(productId);
        Mockito.when(productSvc.fetchProductStockById(productId)).thenReturn(productDTO);
        String url = productStockUrl + "/" + productId;
        this.mockMvc.perform(MockMvcRequestBuilders.get(url)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"id\":\"" + productDTO.getId() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"name\":\"" + productDTO.getName() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"description\":\"" + productDTO.getDescription() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"cost\":" + productDTO.getCost())))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"quantity\":" + productDTO.getQuantity())))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"totalCost\":" + productDTO.getTotalCost())));
    }


    @Test
    public void shouldReturnEmptyResponseIfProductStockNotPresent() throws Exception {
        String productId = "product-id";
        Mockito.when(productSvc.fetchProductStockById(productId)).thenReturn(null);
        String url = productStockUrl + "/" + productId;
        this.mockMvc.perform(MockMvcRequestBuilders.get(url)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.isEmptyOrNullString()));
    }

    @Test
    public void shouldFetchAllProductStock() throws Exception {
        String productId = "product-id";
        ProductDTO productDTO = ProductBuilder.getProductDTO(productId);
        Mockito.when(productSvc.fetchStockInfo()).thenReturn(Collections.singletonList(productDTO));
        this.mockMvc.perform(MockMvcRequestBuilders.get(productStockUrl)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"id\":\"" + productDTO.getId() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"name\":\"" + productDTO.getName() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"description\":\"" + productDTO.getDescription() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"cost\":" + productDTO.getCost())))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"quantity\":" + productDTO.getQuantity())))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"totalCost\":" + productDTO.getTotalCost())));
    }


    @Test
    public void shouldReturnEmptyResponseIfStockIsNotPresent() throws Exception {
        Mockito.when(productSvc.fetchStockInfo()).thenReturn(Collections.emptyList());
        this.mockMvc.perform(MockMvcRequestBuilders.get(productStockUrl)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.is("[]")));
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        String productId = "product-id";
        ProductDTO productDTO = ProductBuilder.getProductDTO(productId);
        Mockito.when(productSvc.createProduct(productDTO)).thenReturn(productDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post(productServiceUrl).contentType("application/json;charset=UTF-8").content(mapper.writeValueAsBytes(productDTO))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"id\":\"" + productDTO.getId() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"name\":\"" + productDTO.getName() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"description\":\"" + productDTO.getDescription() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"cost\":" + productDTO.getCost())))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"quantity\":" + productDTO.getQuantity())))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"totalCost\":" + productDTO.getTotalCost())));
    }

    @Test
    public void shouldNotCreateProductIfNameIsNotPresent() throws Exception {
        String productId = "product-id";
        ProductDTO productDTO = ProductBuilder.getProductDTO(productId);
        productDTO.setName(null);
        Mockito.when(productSvc.createProduct(productDTO)).thenReturn(productDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post(productServiceUrl).contentType("application/json;charset=UTF-8").content(mapper.writeValueAsBytes(productDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldNotCreateProductIfDescriptionIsNotPresent() throws Exception {
        String productId = "product-id";
        ProductDTO productDTO = ProductBuilder.getProductDTO(productId);
        productDTO.setDescription(null);
        Mockito.when(productSvc.createProduct(productDTO)).thenReturn(productDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post(productServiceUrl).contentType("application/json;charset=UTF-8").content(mapper.writeValueAsBytes(productDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldNotCreateProductIfCostIsNotPresent() throws Exception {
        String productId = "product-id";
        ProductDTO productDTO = ProductBuilder.getProductDTO(productId);
        productDTO.setCost(null);
        Mockito.when(productSvc.createProduct(productDTO)).thenReturn(productDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post(productServiceUrl).contentType("application/json;charset=UTF-8").content(mapper.writeValueAsBytes(productDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldNotCreateProductIfQuantityIsNotPresent() throws Exception {
        String productId = "product-id";
        ProductDTO productDTO = ProductBuilder.getProductDTO(productId);
        productDTO.setQuantity(null);
        Mockito.when(productSvc.createProduct(productDTO)).thenReturn(productDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post(productServiceUrl).contentType("application/json;charset=UTF-8").content(mapper.writeValueAsBytes(productDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        String productId = "product-id";
        ProductDTO productDTO = ProductBuilder.getProductDTO(productId);
        Mockito.when(productSvc.createProduct(productDTO)).thenReturn(productDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post(productServiceUrl).contentType("application/json;charset=UTF-8").content(mapper.writeValueAsBytes(productDTO))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"id\":\"" + productDTO.getId() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"name\":\"" + productDTO.getName() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"description\":\"" + productDTO.getDescription() + "\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"cost\":" + productDTO.getCost())))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"quantity\":" + productDTO.getQuantity())))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"totalCost\":" + productDTO.getTotalCost())));
    }
}
