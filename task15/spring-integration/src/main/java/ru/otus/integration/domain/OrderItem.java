package ru.otus.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.integration.consts.OrderType;

@Data
@AllArgsConstructor
public class OrderItem {

    private OrderType orderType;
    private String name;

}
