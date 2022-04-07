package ru.otus.integration.service;

import ru.otus.integration.domain.OrderItem;

import java.util.Collection;

public interface OrderFactory {

    Collection<OrderItem> generateOrder();
}
