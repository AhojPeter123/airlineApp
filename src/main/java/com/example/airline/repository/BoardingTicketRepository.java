package com.example.airline.repository;

import com.example.airline.model.BoardingTicket;
import com.example.airline.model.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardingTicketRepository extends CrudRepository<BoardingTicket, Long> {
    public void deleteByFlight(Flight flight);
}
