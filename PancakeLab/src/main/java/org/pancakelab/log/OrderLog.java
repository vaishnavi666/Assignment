package org.pancakelab.log;

import org.pancakelab.model.order.Order;
import org.pancakelab.model.pancakes.Pancake;

public class OrderLog {

    private static final StringBuffer logBuffer = new StringBuffer();

    private static int getTotalPancakes(Order order) {
        return order.getPancakes().values().stream().mapToInt(Integer::intValue).sum();
    }

    private static void log(String message) {
        logBuffer.append(message).append(System.lineSeparator());
    }

    public static void logAddPancake(Order order, Pancake pancake, int countAdded) {
        log(String.format(
                "Added %d pancake(s) with description '%s' to order %s now containing %d pancake(s), for %s.",
                countAdded, pancake.getDescription(), order.getId(), getTotalPancakes(order), order.getAddress()
        ));
    }

    public static void logRemovePancakes(Order order, Pancake pancake, int countRemoved) {
        log(String.format(
                "Removed %d pancake(s) with description '%s' from order %s now containing %d pancake(s), for %s.",
                countRemoved, pancake.getDescription(), order.getId(), getTotalPancakes(order), order.getAddress()
        ));
    }

    public static void logCancelOrder(Order order) {
        log(String.format(
                "Cancelled order %s with %d pancake(s) for %s.",
                order.getId(), getTotalPancakes(order), order.getAddress()
        ));
    }

    public static void logDeliverOrder(Order order) {
        log(String.format(
                "Order %s with %d pancake(s) for %s out for delivery.",
                order.getId(), getTotalPancakes(order), order.getAddress()
        ));
    }

    public static void logPrepareOrder(Order order) {
        log(String.format(
                "Order %s with %d pancake(s) is getting prepared.",
                order.getId(), getTotalPancakes(order)
        ));
    }
}
