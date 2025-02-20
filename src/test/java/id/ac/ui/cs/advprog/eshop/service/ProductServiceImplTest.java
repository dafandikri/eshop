package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product existingProduct;

    @BeforeEach
    void setUp() {
        existingProduct = new Product();
        existingProduct.setProductId("test-uuid");
        existingProduct.setProductName("Existing Product");
        existingProduct.setProductQuantity(10);
    }

    @Test
    void testEditProductPositive() {
        // Arrange
        when(productRepository.update(any(Product.class))).thenReturn(existingProduct);

        // Act
        Product updatedProduct = productService.update(existingProduct);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals("Existing Product", updatedProduct.getProductName());
        verify(productRepository, times(1)).update(existingProduct);
    }

    @Test
    void testEditProductNegative() {
        // Arrange
        // Simulate the repository returning null if it can’t find the product to update
        when(productRepository.update(any(Product.class))).thenReturn(null);

        // Act
        Product notFoundProduct = productService.update(existingProduct);

        // Assert
        assertNull(notFoundProduct);
        verify(productRepository, times(1)).update(existingProduct);
    }

    @Test
    void testDeleteProductPositive() {
        // Arrange
        doNothing().when(productRepository).deleteById("test-uuid");

        // Act: call deleteById
        productService.deleteById("test-uuid");

        // Assert: verify the interaction
        verify(productRepository, times(1)).deleteById("test-uuid");
    }

    @Test
    void testDeleteProductNegative() {
        // Arrange
        // For a “not found” case, the repository might do nothing if it doesn’t find the product.
        // We'll just verify it was called. If your real code throws an exception, adapt accordingly.
        doNothing().when(productRepository).deleteById("wrong-uuid");

        // Act
        productService.deleteById("wrong-uuid");

        // Assert
        verify(productRepository, times(1)).deleteById("wrong-uuid");
    }

    @Test
    void testCreateProduct() {
        Product newProduct = new Product();
        newProduct.setProductId("new-uuid");
        newProduct.setProductName("New Product");
        newProduct.setProductQuantity(5);

        when(productRepository.create(newProduct)).thenReturn(newProduct);
        
        Product createdProduct = productService.create(newProduct);
        
        assertNotNull(createdProduct);
        assertEquals("New Product", createdProduct.getProductName());
        verify(productRepository, times(1)).create(newProduct);
    }

    @Test
    void testFindAllProducts() {
        Product p1 = new Product();
        p1.setProductId("p1");
        p1.setProductName("Product 1");
        p1.setProductQuantity(3);

        Product p2 = new Product();
        p2.setProductId("p2");
        p2.setProductName("Product 2");
        p2.setProductQuantity(7);

        Iterator<Product> iterator = Arrays.asList(p1, p2).iterator();
        when(productRepository.findAll()).thenReturn(iterator);
        
        List<Product> products = productService.findAll();
        assertEquals(2, products.size());
        assertTrue(products.contains(p1));
        assertTrue(products.contains(p2));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdProduct() {
        String productId = "existing-id";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Exist Product");
        product.setProductQuantity(8);

        when(productRepository.findById(productId)).thenReturn(product);
        
        Product foundProduct = productService.findById(productId);
        assertNotNull(foundProduct);
        assertEquals("Exist Product", foundProduct.getProductName());
        verify(productRepository, times(1)).findById(productId);
    }
}