package ru.otus.integration.service;

import ru.otus.integration.domain.Food;
import ru.otus.integration.domain.OrderItem;

public interface KitchenService {

    Food cook(OrderItem orderItem) throws Exception;
}
