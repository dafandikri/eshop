package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
}