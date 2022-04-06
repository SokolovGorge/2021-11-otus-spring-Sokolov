package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Drink;
import ru.otus.integration.domain.OrderItem;

@Service("barService")
public class BarServiceImpl implements BarService {

    @Override
    public Drink cook(OrderItem orderItem) throws Exception {
        System.out.println("Наливается " + orderItem.getName());
        Thread.sleep(2000);
        System.out.println("Готово " + orderItem.getName());
        return new Drink(orderItem.getName());
    }
}
