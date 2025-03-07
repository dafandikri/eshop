package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    OrderRepository orderRepository;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();

        // Create a shared list of Products
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        // Create test Orders
        orders = new ArrayList<>();

        Order order1 = new Order(
                "13652556-012a-4c07-b546-54eb139d679b",
                products,
                1708560000L,
                "Safira Sudrajat"
        );
        orders.add(order1);

        Order order2 = new Order(
                "7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products,
                1708570000L,
                "Safira Sudrajat"
        );
        orders.add(order2);

        Order order3 = new Order(
                "e34ef40-9eff-4da8-9487-8ee697ecbf1e",
                products,
                1708570000L,
                "Bambang Sudrajat"
        );
        orders.add(order3);
    }

    // 3) Happy path: Use save() to add a new Order
    @Test
    void testSaveCreate() {
        Order order = orders.get(1);               // We'll insert the second order in the list
        Order result = orderRepository.save(order);

        // Now, retrieve it by ID
        Order findResult = orderRepository.findById(orders.get(1).getId());

        assertEquals(order.getId(), result.getId());
        assertEquals(order.getId(), findResult.getId());
        assertEquals(order.getOrderTime(), findResult.getOrderTime());
        assertEquals(order.getAuthor(), findResult.getAuthor());
        assertEquals(order.getStatus(), findResult.getStatus());
    }

    // 4) Happy path: Use save() to update an Order
    @Test
    void testSaveUpdate() {
        Order order = orders.get(1);
        orderRepository.save(order);

        // Create a "new" Order object with the same ID but a new status
        Order newOrder = new Order(
                order.getId(),
                order.getProducts(),
                order.getOrderTime(),
                order.getAuthor(),
                OrderStatus.SUCCESS.getValue()
        );
        Order result = orderRepository.save(newOrder);

        // Verify that the saved object is updated
        Order findResult = orderRepository.findById(orders.get(1).getId());
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getId(), findResult.getId());
        assertEquals(order.getOrderTime(), findResult.getOrderTime());
        assertEquals(order.getAuthor(), findResult.getAuthor());
        assertEquals(OrderStatus.SUCCESS.getValue(), findResult.getStatus());
    }

    // 5) Happy path: Use findById() to find an Order with a valid ID
    @Test
    void testFindByIdIfIdFound() {
        // Insert all test Orders
        for (Order o : orders) {
            orderRepository.save(o);
        }

        // Find the second order by ID
        Order findResult = orderRepository.findById(orders.get(1).getId());
        assertEquals(orders.get(1).getId(), findResult.getId());
        assertEquals(orders.get(1).getOrderTime(), findResult.getOrderTime());
        assertEquals(orders.get(1).getAuthor(), findResult.getAuthor());
        assertEquals(orders.get(1).getStatus(), findResult.getStatus());
    }

    // 6) Unhappy path: Use findById() with an ID that was never used
    @Test
    void testFindByIdIfIdNotFound() {
        // Insert all test Orders
        for (Order o : orders) {
            orderRepository.save(o);
        }

        // This ID does not exist
        Order findResult = orderRepository.findById("zzcc");
        assertNull(findResult);
    }

    // 7) Happy path: Use findAllByAuthor() to find Orders by a valid author (case‐sensitive)
    @Test
    void testFindAllByAuthorIfAuthorCorrect() {
        for (Order o : orders) {
            orderRepository.save(o);
        }

        // Looking up by the second Order’s author, "Safira Sudrajat"
        List<Order> orderList = orderRepository.findAllByAuthor(orders.get(1).getAuthor());
        assertEquals(2, orderList.size());
    }

    // 8) Unhappy path: Use findAllByAuthor() with an all-lowercase version of the author
    @Test
    void testFindAllByAuthorIfAllLowercase() {
        orderRepository.save(orders.get(1));

        // "safira sudrajat" != "Safira Sudrajat" (case sensitive)
        List<Order> orderList = orderRepository.findAllByAuthor(orders.get(1).getAuthor().toLowerCase());
        assertTrue(orderList.isEmpty());
    }
}
