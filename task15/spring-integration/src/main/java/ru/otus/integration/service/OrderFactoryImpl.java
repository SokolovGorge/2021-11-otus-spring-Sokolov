package ru.otus.integration.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.integration.consts.OrderType;
import ru.otus.integration.domain.OrderItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OrderFactoryImpl implements OrderFactory {

    private static final String[] DRINK_ITEMS = {"Кола", "Нарзан", "Чай", "Кофе", "Водка", "Виски", "Пиво"};

    private static final String[] FOOD_ITEMS = {"Бифштекс", "Люля-кебаб", "Шашлык", "Жаркое", "Дичь", "Холодец", "Оливье"};

    @Override
    public Collection<OrderItem> generateOrder() {
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(3, 6); i++) {
            items.add(generateOrderItem());
        }
        return items;
    }

    private OrderItem generateOrderItem() {
        OrderType orderType = getOrderType();
        String nameItem;
        switch (orderType) {
            case DRINK:
                nameItem = DRINK_ITEMS[RandomUtils.nextInt(0, DRINK_ITEMS.length)];
                break;
            case FOOD:
                nameItem = FOOD_ITEMS[RandomUtils.nextInt(0, FOOD_ITEMS.length)];
                break;
            default:
                throw new IllegalArgumentException("Unknown order type " + orderType);
        }
        return new OrderItem(orderType, nameItem);
    }

    private OrderType getOrderType() {
        int index = RandomUtils.nextInt(1, 3);
        if (index == 1) {
            return OrderType.DRINK;
        }
        return OrderType.FOOD;
    }

}
