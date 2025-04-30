package org.pancakelab.interfaces;

import org.pancakelab.model.order.Order;

import java.util.Set;
import java.util.UUID;

public interface IOrderService {

    Order createOrder(int building, int roomNumber);
    boolean cancelOrder(UUID orderId);
    boolean prepareOrder(UUID orderId);
    Order deliverOrder(UUID orderId);
    Set<UUID> listCompletedOrders();
    Set<UUID> listPreparedOrders();
    }
