package ru.otus.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.integration.consts.OrderType;
import ru.otus.integration.domain.OrderItem;

@Configuration
public class IntegrationConfig {

    @Bean
    public QueueChannel orderChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel servingChanel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public QueueChannel barChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public QueueChannel kitchenChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel bringChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow cafeFlow() {
        return IntegrationFlows.from("orderChannel")
                .split()
                .<OrderItem, OrderType>route(OrderItem::getOrderType, mapping -> mapping
                        .channelMapping(OrderType.DRINK, barChannel())
                        .channelMapping(OrderType.FOOD, kitchenChannel())
                        ).get();
    }

    @Bean
    public IntegrationFlow barFlow() {
        return IntegrationFlows.from(barChannel())
                .handle("barService", "cook")
                .channel(bringChannel())
                .get();
    }

    @Bean
    public IntegrationFlow kitchenFlow() {
        return IntegrationFlows.from(kitchenChannel())
                .handle("kitchenService", "cook")
                .channel(bringChannel())
                .get();
    }

    @Bean
    public IntegrationFlow bringFlow() {
        return IntegrationFlows.from(bringChannel())
                .aggregate()
                .channel(servingChanel())
                .get();
    }

}
