package com.containerize.restapidocker.service;
import com.containerize.restapidocker.dto.ProductDTO;
import com.containerize.restapidocker.entity.Product;
import com.containerize.restapidocker.exception.ResourceNotFoundException;
import com.containerize.restapidocker.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1L);
        product.setName("Pen");
        product.setPrice(10.0);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Pen");
        productDTO.setPrice(10.0);
    }

    @Test
    void testCreateProduct() {
        when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pen");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = List.of(product);
        when(productRepository.findAll()).thenReturn(products);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        List<ProductDTO> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Pen", result.get(0).getName());
    }

    @Test
    void testGetProductById_Found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Pen", result.getName());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testUpdateProduct() {
        Product updated = new Product();
        updated.setName("Marker");
        updated.setPrice(15.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.updateProduct(1L, updated);

        assertEquals("Marker", result.getName());
        assertEquals(15.0, result.getPrice());
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testPatchProduct() {
        ProductDTO patchDTO = new ProductDTO();
        patchDTO.setPrice(20.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.patchProduct(1L, patchDTO);

        assertEquals(20.0, result.getPrice());
    }

    @Test
    void testGetProductByName() {
        when(productRepository.findByName("Pen")).thenReturn(product);

        Product result = productService.getProductByName("Pen");

        assertNotNull(result);
        assertEquals("Pen", result.getName());
    }

    @Test
    void testUpdatePriceByName() {
        when(productRepository.updatePriceByName(100.0, "Pen")).thenReturn(1);

        int updated = productService.updatePriceByName(100.0, "Pen");

        assertEquals(1, updated);
        verify(productRepository, times(1)).updatePriceByName(100.0, "Pen");
    }
}

