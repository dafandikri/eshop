package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
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
        assertNull(productRepository.findById("wrong-id"));
    }

    @Test
    void testCreateProductWithNullId() {
        Product product = new Product();
        // explicitly leave productId as null to trigger auto-generation
        product.setProductId(null);
        product.setProductName("Auto-ID Product");
        product.setProductQuantity(75);
        Product created = productRepository.create(product);
        assertNotNull(created.getProductId());
        // Check that the product is indeed saved:
        Product found = productRepository.findById(created.getProductId());
        assertNotNull(found);
    }

    @Test
    void testFindByIdWithNullProductInData() throws Exception {
        // Manually add a product with a null id into productData
        Field field = ProductRepository.class.getDeclaredField("productData");
        field.setAccessible(true);
        List<Product> productData = (List<Product>) field.get(productRepository);
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("No ID Product");
        product.setProductQuantity(10);
        productData.add(product);
        // Expect findById(null) to return null since condition checks for non-null id
        assertNull(productRepository.findById(null));
        // Searching for any non-null id also returns null
        assertNull(productRepository.findById("any-id"));
    }

    @Test
    void testUpdateProductWithNullId() {
        // When updating a product with null id, update should return null
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("Null ID Product");
        product.setProductQuantity(20);
        assertNull(productRepository.update(product));
    }

    @Test
    void testDeleteByIdWithNullArgument() throws Exception {
        // Manually add a product with null id into productData
        Field field = ProductRepository.class.getDeclaredField("productData");
        field.setAccessible(true);
        List<Product> productData = (List<Product>) field.get(productRepository);
        int originalSize = productData.size();
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("Null ID Product");
        product.setProductQuantity(20);
        productData.add(product);
        // Calling deleteById(null) should not remove any product because condition fails.
        productRepository.deleteById(null);
        assertEquals(originalSize + 1, productData.size());
    }

    @Test
    void testUpdateProductWithMultipleProducts() {
        // Arrange: add two products
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        // Act: update second product (this forces the for-loop to iterate past the first element)
        Product update = new Product();
        update.setProductId("id-2");
        update.setProductName("Updated Product 2");
        update.setProductQuantity(30);
        Product returned = productRepository.update(update);

        // Assert: product2 is updated, product1 remains unchanged
        assertNotNull(returned);
        assertEquals("Updated Product 2", returned.getProductName());
        assertEquals(30, returned.getProductQuantity());
        assertEquals("Product 1", productRepository.findById("id-1").getProductName());
    }

    @Test
    void testDeleteProductMultipleProducts() {
        // Arrange: add two products
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        // Act: delete the second product (iterating past a non-matching first element)
        productRepository.deleteById("id-2");

        // Assert: product2 is removed; product1 remains available
        assertNotNull(productRepository.findById("id-1"));
        assertNull(productRepository.findById("id-2"));
    }

    @Test
    void testUpdateWhenExistingProductIdIsNull() throws Exception {
        // Arrange: manually add a product with a null ID into productData
        Field field = ProductRepository.class.getDeclaredField("productData");
        field.setAccessible(true);
        List<Product> productData = (List<Product>) field.get(productRepository);
        Product productWithNullId = new Product();
        productWithNullId.setProductId(null);
        productWithNullId.setProductName("Null ID Product");
        productWithNullId.setProductQuantity(10);
        productData.add(productWithNullId);
        
        // Act: attempt to update with a product having a non-null ID
        Product updated = new Product();
        updated.setProductId("some-id");
        updated.setProductName("Updated Name");
        updated.setProductQuantity(20);
        Product result = productRepository.update(updated);
        
        // Assert: since the existing product's ID is null, the condition fails and update returns null
        assertNull(result);
    }
}
