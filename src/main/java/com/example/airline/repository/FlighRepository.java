package com.example.airline.repository;

import com.example.airline.model.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlighRepository  extends CrudRepository<Flight, Long> {

}
