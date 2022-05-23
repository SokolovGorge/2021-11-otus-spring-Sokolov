package ru.otus.vacancykeeper.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(schema = "vacancy", name = "vacancy")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scode", nullable = false)
    private String scode;

    @Column(name = "sid", nullable = false)
    private String sid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "salary_min")
    private Long salaryMin;

    @Column(name = "salary_max")
    private Long salaryMax;

    @Column(name = "currency")
    private String currency;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "address")
    private String address;

    @Column(name = "addr_lat")
    private Float addrLat;

    @Column(name = "addr_lng")
    private Float addrLng;

    @Column(name = "employer_name")
    private String employerName;

    @Column(name = "employer_url")
    private String employerUrl;

    @Column(name = "requirement")
    private String requirement;

    @Column(name = "responsibility")
    private String responsibility;

    @Column(name = "source_url")
    private String sourceUrl;

}
