package com.example.airline.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Table
public class Airport {

    @Id
    private String name;
    @ManyToOne
    private City city;

    protected Airport() {}

    public Airport(String name, City city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public Country getCountry() {
        return city.getCountry();
    }

}
