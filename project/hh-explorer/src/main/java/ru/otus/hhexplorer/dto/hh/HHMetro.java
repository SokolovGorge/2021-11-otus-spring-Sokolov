package ru.otus.hhexplorer.dto.hh;

import lombok.Data;

@Data
public class HHMetro {

    private String station_name;
    private String line_name;
    private String station_id;
    private double lat;
    private double lng;
}
