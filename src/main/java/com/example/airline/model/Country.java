package com.example.airline.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Country {
    @Id
    String name;

    protected Country() {}

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
