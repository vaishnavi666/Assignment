package org.pancakelab.service;

import org.pancakelab.builder.OrderBuilder;
import org.pancakelab.interfaces.IOrderService;
import org.pancakelab.interfaces.IPancakeService;
import org.pancakelab.log.OrderLog;
import org.pancakelab.model.Address;
import org.pancakelab.model.order.NullOrder;
import org.pancakelab.model.order.Order;
import org.pancakelab.model.pancakes.Pancake;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class OrderServiceImpl implements IOrderService, IPancakeService {

    private final List<Order> orders = new CopyOnWriteArrayList<>();
    private final Set<UUID> completedOrders = ConcurrentHashMap.newKeySet();
    private final Set<UUID> preparedOrders = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized Order createOrder(int building, int roomNumber) {
        Address address = new Address(building, roomNumber);
        Order order = new OrderBuilder(address).build();
        if (!(order instanceof NullOrder)) {
            orders.add(order);
        }
        return order;
    }

    public Map<Pancake, Integer> addPancakes(UUID orderId, Pancake pancake, int count) {
        if (pancake == null || count <= 0) return Collections.emptyMap();

        Order order = findOrderById(orderId);
        if (order == null) return Collections.emptyMap();

        synchronized (order) {
            OrderLog.logAddPancake(order, pancake, count);
            return order.addPancakes(pancake, count);
        }
    }

    public Map<Pancake, Integer> removePancakes(UUID orderId, Pancake pancake, int count) {
        if (pancake == null || count <= 0) return Collections.emptyMap();

        Order order = findOrderById(orderId);
        if (order == null) return Collections.emptyMap();

        synchronized (order) {
            OrderLog.logRemovePancakes(order, pancake, count);
            return order.removePancakes(pancake, count);
        }
    }

    @Override
    public boolean cancelOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        boolean cancelStatus = orders.removeIf(o -> o.getId().equals(orderId));
        OrderLog.logCancelOrder(order);
        return cancelStatus;
    }

    @Override
    public boolean prepareOrder(UUID orderId) {
        boolean validOrder = false;
        Order order = findOrderById(orderId);
        OrderLog.logPrepareOrder(order);
        if (order != null) {
            validOrder = true;
            preparedOrders.add(orderId);
        }
        return validOrder;
    }

    @Override
    public Order deliverOrder(UUID orderId) {
        if (!preparedOrders.contains(orderId)) return null;

        preparedOrders.remove(orderId);
        completedOrders.add(orderId);
        Order order = findOrderById(orderId);
        orders.removeIf(o -> o.getId().equals(orderId));
        OrderLog.logDeliverOrder(order);
        return order;
    }

    @Override
    public Set<UUID> listCompletedOrders() {
        return new HashSet<>(completedOrders);
    }

    @Override
    public Set<UUID> listPreparedOrders() {
        return new HashSet<>(preparedOrders);
    }

    public Set<Order> listPreOrders() {
        return new HashSet<>(orders);
    }

    public Order findOrderById(UUID orderId) {
        return orders.stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}
