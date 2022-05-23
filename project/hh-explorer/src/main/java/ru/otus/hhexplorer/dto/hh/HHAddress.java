package ru.otus.hhexplorer.dto.hh;

import lombok.Data;

@Data
public class HHAddress {
    private String id;
    private String city;
    private String street;
    private String building;
    private String description;
    private float lat;
    private float lng;
    private String raw;
    private HHMetro metro;
    private HHMetro[] metro_stations;

}
