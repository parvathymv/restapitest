package com.containerize.restapidocker.controller;
import com.containerize.restapidocker.dto.ProductDTO;
import com.containerize.restapidocker.entity.Product;
import com.containerize.restapidocker.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Pen");
        productDTO.setPrice(10.0);

        product = new Product();
        product.setId(1L);
        product.setName("Pen");
        product.setPrice(10.0);
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any())).thenReturn(productDTO);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pen"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(productDTO));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pen"));
    }

    @Test
    void testGetProductById() throws Exception {
        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pen"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        when(productService.updateProduct(Mockito.eq(1L), any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPatchProduct() throws Exception {
        when(productService.patchProduct(Mockito.eq(1L), any(ProductDTO.class))).thenReturn(product);

        mockMvc.perform(patch("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pen"));
    }

    @Test
    void testGetProductByName() throws Exception {
        when(productService.getProductByName("Pen")).thenReturn(product);

        mockMvc.perform(get("/api/products/getByName/Pen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pen"));
    }

    @Test
    void testGetProductByPriceRange() throws Exception {
        when(productService.getProductByPriceRange(5.0, 15.0)).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products/5.0/15.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(10.0));
    }

    @Test
    void testUpdatePriceByName() throws Exception {
        when(productService.updatePriceByName(15.0, "Pen")).thenReturn(1);

        mockMvc.perform(put("/api/products/updatePrice/15.0/Pen"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product price updated"));
    }
}

