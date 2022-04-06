package ru.otus.integration.service;

import ru.otus.integration.domain.Drink;
import ru.otus.integration.domain.OrderItem;

public interface BarService {

    Drink cook(OrderItem orderItem) throws Exception;

}
