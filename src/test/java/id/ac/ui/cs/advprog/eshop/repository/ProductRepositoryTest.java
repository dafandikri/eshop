package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testUpdateProductPositive() {
        // Arrange: create and add a product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Example Product");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Act: update the product
        product.setProductName("Updated Product Name");
        product.setProductQuantity(200);
        Product updated = productRepository.update(product);

        // Assert: ensure the product was indeed updated
        assertNotNull(updated);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", updated.getProductId());
        assertEquals("Updated Product Name", updated.getProductName());
        assertEquals(200, updated.getProductQuantity());
    }

    @Test
    void testUpdateProductNegative() {
        // Arrange: create a product but do NOT add it to the repository
        Product product = new Product();
        product.setProductId("non-existent-id");
        product.setProductName("Non-existent Name");
        product.setProductQuantity(10);

        // Act: attempt to update a product that wasn't created
        Product updated = productRepository.update(product);

        // Assert: expect null since no matching product exists
        assertNull(updated);
    }

    @Test
    void testDeleteProductPositive() {
        // Arrange: create and add a product
        Product product = new Product();
        product.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product.setProductName("To-be-deleted");
        product.setProductQuantity(50);
        productRepository.create(product);

        // Act: delete the product by ID
        productRepository.deleteById("a0f9de46-90b1-437d-a0bf-d0821dde9096");

        // Assert: confirm the product no longer exists
        assertNull(productRepository.findById("a0f9de46-90b1-437d-a0bf-d0821dde9096"));
    }

    @Test
    void testDeleteProductNegative() {
        // Arrange/Act: try deleting a product that doesn't exist
        productRepository.deleteById("wrong-id");

        // Assert: no errors, repository stays unaffected
        // (Change if you want to confirm an exception or another behavior)
        assertNull(productRepository.findById("wrong-id"));
    }
}
