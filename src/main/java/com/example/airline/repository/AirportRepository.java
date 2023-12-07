package com.example.airline.repository;

import com.example.airline.model.Airport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends CrudRepository<Airport, String> {
    public Airport findByName(String name);
}
