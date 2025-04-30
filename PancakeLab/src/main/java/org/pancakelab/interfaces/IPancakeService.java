package org.pancakelab.interfaces;

import org.pancakelab.model.pancakes.Pancake;

import java.util.Map;
import java.util.UUID;

public interface IPancakeService {

    Map<Pancake, Integer> addPancakes(UUID orderId, Pancake pancake, int count);

    Map<Pancake, Integer> removePancakes(UUID orderId, Pancake pancake, int count);
}
