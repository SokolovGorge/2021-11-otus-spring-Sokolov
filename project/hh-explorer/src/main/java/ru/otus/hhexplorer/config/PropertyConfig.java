package ru.otus.hhexplorer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "hh")
@Component
public class PropertyConfig implements ConverterConfig, VacancyConfig {

    private String serviceVacancyUrl;
    private Map<String, String> profMap;
    private Map<String, String> areaMap;

    @Override
    public String getServiceVacancyUrl() {
        return serviceVacancyUrl;
    }

    public void setServiceVacancyUrl(String serviceVacancyUrl) {
        this.serviceVacancyUrl = serviceVacancyUrl;
    }

    @Override
    public Map<String, String> getProfMap() {
        return profMap;
    }

    public void setProfMap(Map<String, String> profMap) {
        this.profMap = profMap;
    }

    @Override
    public Map<String, String> getAreaMap() {
        return areaMap;
    }

    public void setAreaMap(Map<String, String> areaMap) {
        this.areaMap = areaMap;
    }
}
