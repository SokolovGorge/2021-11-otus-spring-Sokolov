package ru.otus.vacancykeeper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "vacancy")
@Component
public class PropertyConfig implements ExplorerConfig, MessageConfig {

    private String messageServiceName;

    private Map<String, String> explorerMap;

    @Override
    public String getMessageServiceName() {
        return messageServiceName;
    }

    public void setMessageServiceName(String messageServiceName) {
        this.messageServiceName = messageServiceName;
    }

    @Override
    public Map<String, String> getExplorerMap() {
        return explorerMap;
    }

    public void setExplorerMap(Map<String, String> explorerMap) {
        this.explorerMap = explorerMap;
    }
}
