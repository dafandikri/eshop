package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
               .andExpect(status().isOk())
               .andExpect(view().name("createProduct"))
               .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPostValid() throws Exception {
        // Removed stubbing for void method service.create(...)
        mockMvc.perform(post("/product/create")
               .param("productId", "id-123")
               .param("productName", "Test Product")
               .param("productQuantity", "50"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("list"));
        verify(service).create(ArgumentMatchers.any(Product.class));
    }

    @Test
    void testCreateProductPostErrors() throws Exception {
        Product product = new Product();
        // ...simulate invalid product...
        BindingResult bindingResult = new BeanPropertyBindingResult(product, "product");
        bindingResult.reject("error", "validation error");

        mockMvc.perform(post("/product/create")
               .flashAttr("product", product)
               .flashAttr("org.springframework.validation.BindingResult.product", bindingResult))
               .andExpect(status().isOk())
               .andExpect(view().name("createProduct"));
    }

    @Test
    void testProductListPage() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/product/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("productList"))
               .andExpect(model().attributeExists("products"));
    }

    @Test
    void testEditProductPageNotFound() throws Exception {
        when(service.findById("non-existent")).thenReturn(null);
        mockMvc.perform(get("/product/edit").param("id", "non-existent"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testEditProductPageFound() throws Exception {
        Product product = new Product();
        product.setProductId("id-123");
        product.setProductName("Test Product");
        product.setProductQuantity(50);
        when(service.findById("id-123")).thenReturn(product);
        mockMvc.perform(get("/product/edit").param("id", "id-123"))
               .andExpect(status().isOk())
               .andExpect(view().name("EditProduct"))
               .andExpect(model().attribute("product", product));
    }

    @Test
    void testEditProductPostValid() throws Exception {
        // Removed stubbing for void method service.update(...)
        mockMvc.perform(post("/product/edit")
               .param("productId", "id-123")
               .param("productName", "Updated Name")
               .param("productQuantity", "75"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("list"));
        verify(service).update(ArgumentMatchers.any(Product.class));
    }

    @Test
    void testEditProductPostErrors() throws Exception {
        Product product = new Product();
        BindingResult bindingResult = new BeanPropertyBindingResult(product, "product");
        bindingResult.reject("error", "validation error");

        mockMvc.perform(post("/product/edit")
               .flashAttr("product", product)
               .flashAttr("org.springframework.validation.BindingResult.product", bindingResult))
               .andExpect(status().isOk())
               .andExpect(view().name("EditProduct"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(service).deleteById("id-123");
        mockMvc.perform(post("/product/delete").param("id", "id-123"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/product/list"));
        verify(service).deleteById("id-123");
    }
}
