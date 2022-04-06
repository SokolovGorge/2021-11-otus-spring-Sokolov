package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Food;
import ru.otus.integration.domain.OrderItem;

@Service("kitchenService")
public class KitchenServiceImpl implements KitchenService {

    @Override
    public Food cook(OrderItem orderItem) throws Exception {
        System.out.println("Приготавливается " + orderItem.getName());
        Thread.sleep(2000);
        System.out.println("Готово " + orderItem.getName());
        return new Food(orderItem.getName());
    }
}
