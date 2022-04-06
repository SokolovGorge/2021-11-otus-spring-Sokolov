package ru.otus.integration.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.util.concurrent.ListenableFuture;
import ru.otus.integration.domain.OrderItem;
import ru.otus.integration.domain.Serving;

import java.util.Collection;

@MessagingGateway
public interface Cafe {

    @Gateway(requestChannel = "orderChannel", replyChannel = "servingChanel")
    ListenableFuture<Collection<Serving>> making(Collection<OrderItem> items);
}
