package ru.otus.hhexplorer.dto.hh;

import lombok.Data;

@Data
public class HHMetro {

    private String stationName;
    private String lineName;
    private String stationId;
    private double lat;
    private double lng;
}
