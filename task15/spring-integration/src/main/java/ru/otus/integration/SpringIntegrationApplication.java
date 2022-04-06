package ru.otus.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.otus.integration.domain.OrderItem;
import ru.otus.integration.domain.Serving;
import ru.otus.integration.config.Cafe;
import ru.otus.integration.service.OrderFactory;

import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
public class SpringIntegrationApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(SpringIntegrationApplication.class, args);

		Cafe cafe = context.getBean(Cafe.class);
		OrderFactory orderFactory = context.getBean(OrderFactory.class);
		ForkJoinPool pool = ForkJoinPool.commonPool();

		while (true) {
			pool.execute(() -> {
				Collection<OrderItem> order = orderFactory.generateOrder();
				System.out.println("Новый заказ: " + order.stream().map(OrderItem::getName).collect(Collectors.joining(",")));
				ListenableFuture<Collection<Serving>> result = cafe.making(order);
				result.addCallback(new ListenableFutureCallback<Collection<Serving>>() {
					@Override
					public void onFailure(Throwable ex) {
						System.out.println("Ошибка: " + ex.getLocalizedMessage());
					}

					@Override
					public void onSuccess(Collection<Serving> result) {
						System.out.println("Сервировано:" + result.stream().map(Serving::getName).collect(Collectors.joining(",")));
					}
				});

			});
			Thread.sleep(15000);
		}

	}

}
