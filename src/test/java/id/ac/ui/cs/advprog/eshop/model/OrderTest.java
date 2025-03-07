package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductId("a02c5328-4a37-4644-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);

        this.products.add(product1);
        this.products.add(product2);
    }

    // Unhappy path: Test creating the order with empty products
    @Test
    void testCreateOrderEmptyProduct() {
        this.products.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            new Order("13652556-012a-4c07-b546-54eb139d679b",
                    this.products,
                    1708560000L,
                    "Safira Sudrajat");
        });
    }

    // Happy path: Test creating the order with the default status (no status parameter)
    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order("13652556-012a-4c07-b546-54eb139d679b",
                this.products,
                1708560000L,
                "Safira Sudrajat");

        assertSame(this.products, order.getProducts());
        assertEquals(2, order.getProducts().size());
        assertEquals("Sampo Cap Bambang", order.getProducts().get(0).getProductName());
        assertEquals("Sabun Cap Usep", order.getProducts().get(1).getProductName());

        assertEquals("13652556-012a-4c07-b546-54eb139d679b", order.getId());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());
        // Using the enum’s default value here:
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order.getStatus());
    }

    // Happy path: Test creating the order with SUCCESS status
    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order("13652556-012a-4c07-b546-54eb139d679b",
                this.products,
                1708560000L,
                "Safira Sudrajat",
                OrderStatus.SUCCESS.getValue());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    // Unhappy path: Test creating the order with an invalid status
    @Test
    void testCreateOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Order("13652556-012a-4c07-b546-54eb139d679b",
                    this.products,
                    1708560000L,
                    "Safira Sudrajat",
                    "MEOW");
        });
    }

    // Happy path: Test editing (setting) the order status to CANCELLED
    @Test
    void testSetStatusToCancelled() {
        Order order = new Order("13652556-012a-4c07-b546-54eb139d679b",
                this.products,
                1708560000L,
                "Safira Sudrajat");
        order.setStatus(OrderStatus.CANCELLED.getValue());
        assertEquals(OrderStatus.CANCELLED.getValue(), order.getStatus());
    }

    // Unhappy path: Test editing (setting) the order status to an invalid value
    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order("13652556-012a-4c07-b546-54eb139d679b",
                this.products,
                1708560000L,
                "Safira Sudrajat");
        assertThrows(IllegalArgumentException.class, () -> order.setStatus("MEOW"));
    }
}
