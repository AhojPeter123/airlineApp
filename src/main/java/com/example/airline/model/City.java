package com.example.airline.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class City {

    @Id
    String name;
    @ManyToOne
    Country country;

    protected City() {}

    public City(String name, Country country) {
        this.country = country;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Country getCountry() {
        return this.country;
    }
}
