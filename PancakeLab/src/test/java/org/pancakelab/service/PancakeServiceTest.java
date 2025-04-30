package org.pancakelab.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.pancakelab.builder.PancakeBuilder;
import org.pancakelab.model.order.Order;
import org.pancakelab.model.pancakes.Pancake;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PancakeServiceTest {

    private OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
    private Set<String> allowedIngredients = PancakeBuilder.getAllowedIngredients();
    private static final String MILK_CHOCOLATE_DARK_CHOCOLATE_PANCAKE_DESCRIPTION =
            "Delicious pancake with Milk Chocolate, Dark Chocolate!";

    @Test
    @org.junit.jupiter.api.Order(10)
    public void InvalidAddress_OrderNotCreated_Test() {
        //exercise
        assertThrows(IllegalArgumentException.class,
                () -> orderServiceImpl.createOrder(0, 1));
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    public void ValidAddress_OrderCreated_Test() {
        //exercise
        Order order = orderServiceImpl.createOrder(1, 1);

        //verify
        assertNotNull(order);
        assertNotNull(order.getId());
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    public void GivenOrderExists_WhenAddingPancakes_ThenCorrectNumberOfPancakesAdded_Test() {
        //setup
        Order order = orderServiceImpl.createOrder(1, 1);
        List<String> ingredients = List.of("Milk Chocolate", "Dark Chocolate");
        assertTrue(allowedIngredients.containsAll(ingredients));

        //exercise
        Pancake pancake = new PancakeBuilder(ingredients).build();
        Map<Pancake, Integer> pancakeMap =
                orderServiceImpl.addPancakes(order.getId(), pancake, 3);

        //verify
        assertEquals(MILK_CHOCOLATE_DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                pancake.getDescription());
        assertFalse(pancakeMap.isEmpty());
        assertEquals(1, pancakeMap.size());
        assertEquals(3, pancakeMap.get(pancake));
        String expectedName = new Pancake(ingredients).getName(ingredients);
        assertEquals(expectedName, pancake.getName(ingredients));
    }

    @Test
    @org.junit.jupiter.api.Order(40)
    public void GivenOrderHasPancakes_WhenRemovingPancakes_ThenCorrectlyUpdated_Test() {
        //setup
        Order order = orderServiceImpl.createOrder(1, 1);
        List<String> ingredients = List.of("Milk Chocolate", "Dark Chocolate");
        Pancake pancake = new PancakeBuilder(ingredients).build();
        orderServiceImpl.addPancakes(order.getId(), pancake, 5);

        //exercise
        Map<Pancake, Integer> resultMap =
                orderServiceImpl.removePancakes(order.getId(), pancake, 3);

        //verify
        assertTrue(resultMap.containsKey(pancake));
        assertEquals(2, resultMap.get(pancake));

        //exercise
        resultMap = orderServiceImpl.removePancakes(order.getId(), pancake, 2);

        //verify
        assertFalse(resultMap.containsKey(pancake));
    }

    @Test
    @org.junit.jupiter.api.Order(50)
    public void GivenValidAddress_WhenCreatingOrderAndAddingPancakes_ThenOrderTracksPancakesCorrectly_Test() {
        //setup
        Order order = orderServiceImpl.createOrder(1, 101);
        List<String> ingredients = List.of("Milk Chocolate", "Dark Chocolate");
        Pancake pancake = new PancakeBuilder(ingredients).build();

        //exercise
        Map<Pancake, Integer> pancakeMap =
                orderServiceImpl.addPancakes(order.getId(), pancake, 4);

        //verify
        assertFalse(pancakeMap.isEmpty());
        assertEquals(1, pancakeMap.size());
        assertEquals(4, pancakeMap.get(pancake));
    }

    @Test
    @org.junit.jupiter.api.Order(60)
    public void GivenOrderExists_WhenCompletingOrder_ThenOrderCompleted_Test() {
        //setup
        Order order = orderServiceImpl.createOrder(1, 101);
        List<String> ing1 = List.of("Milk Chocolate", "Dark Chocolate");
        Pancake pancake1 = new PancakeBuilder(ing1).build();
        orderServiceImpl.addPancakes(order.getId(), pancake1, 4);
        List<String> ing2 = List.of("Milk Chocolate");
        Pancake pancake2 = new PancakeBuilder(ing2).build();
        orderServiceImpl.addPancakes(order.getId(), pancake2, 4);
        assertEquals(2, order.getPancakes().size());

        //exercise
        orderServiceImpl.prepareOrder(order.getId());

        //verify
        assertTrue(orderServiceImpl.listPreparedOrders()
                .contains(order.getId()));
        assertFalse(orderServiceImpl.listCompletedOrders()
                .contains(order.getId()));
    }

    @Test
    @org.junit.jupiter.api.Order(70)
    public void GivenOrderExists_WhenDeliveringOrder_ThenCorrectOrderReturnedAndOrderRemovedFromTheDatabase_Test() {
        //setup
        Order order = orderServiceImpl.createOrder(1, 101);
        List<String> ing1 = List.of("Milk Chocolate", "Dark Chocolate");
        Pancake p1 = new PancakeBuilder(ing1).build();
        orderServiceImpl.addPancakes(order.getId(), p1, 4);
        orderServiceImpl.prepareOrder(order.getId());

        //exercise
        orderServiceImpl.deliverOrder(order.getId());

        //verify
        assertTrue(orderServiceImpl.listCompletedOrders()
                .contains(order.getId()));
        assertFalse(orderServiceImpl.listPreparedOrders()
                .contains(order.getId()));
        assertFalse(orderServiceImpl.listPreOrders()
                .contains(order));
    }

    @Test
    @org.junit.jupiter.api.Order(80)
    public void GivenOrderExists_WhenCancellingOrder_ThenOrderAndPancakesRemoved_Test() {
        //setup
        Order order = orderServiceImpl.createOrder(1, 101);
        List<String> ing1 = List.of("Milk Chocolate", "Dark Chocolate");
        Pancake p1 = new PancakeBuilder(ing1).build();

        //exercise
        orderServiceImpl.cancelOrder(order.getId());

        //verify
        assertFalse(orderServiceImpl.listCompletedOrders()
                .contains(order.getId()));
        assertFalse(orderServiceImpl.listPreparedOrders()
                .contains(order.getId()));
        assertFalse(orderServiceImpl.listPreOrders()
                .contains(order));
    }
}
