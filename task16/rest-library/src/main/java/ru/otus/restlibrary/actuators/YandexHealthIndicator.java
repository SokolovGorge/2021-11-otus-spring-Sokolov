package ru.otus.restlibrary.actuators;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class YandexHealthIndicator implements HealthIndicator {

    @Value("${services.yandex.path}")
    private String yandexPath;

    @Override
    public Health health() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(yandexPath).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return Health.up().
                        withDetail("message", "Yandex is available")
                        .build();
            } else {
                return Health.down()
                        .status(Status.DOWN)
                        .withDetail("message", "Yandex is ill")
                        .build();
            }
        } catch (Exception ex) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Error: " + ex.getLocalizedMessage())
                    .build();
        }

    }
}
