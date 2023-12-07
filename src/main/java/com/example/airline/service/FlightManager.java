package com.example.airline.service;

import com.example.airline.app.FlightFilter;
import com.example.airline.model.Airport;
import com.example.airline.model.BoardingTicket;
import com.example.airline.model.Flight;
import com.example.airline.model.User;
import com.example.airline.repository.AirportRepository;
import com.example.airline.repository.BoardingTicketRepository;
import com.example.airline.repository.FlighRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Transactional
@Service
public class FlightManager {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private FlighRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private UserManager userManager;

    @Autowired
    private BoardingTicketRepository ticketRepository;

    public List<Flight> findFlights(FlightFilter filter) {

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> cq = cb.createQuery(Flight.class);

        Predicate predicate = filter.getJPAPredicate().toPredicate(cq.from(Flight.class), cq, cb);
        cq.where(predicate);

        return entityManager.createQuery(cq).getResultList();
    }

    public void addFlight(LocalTime departureTime, LocalTime arrivalTime, LocalDate departureDate, LocalDate arrivalDate, String fromAirport, String toAirport, int planeSize) {

        Airport from = this.airportRepository.findByName(fromAirport);
        Airport to = this.airportRepository.findByName(toAirport);

        Flight flight = new Flight(departureTime, arrivalTime, departureDate, arrivalDate, from, to, planeSize);

        this.flightRepository.save(flight);
    }

    public BoardingTicket bookFlight(Long id, int seatNumber) {

        Flight flight = this.flightRepository.findById(id).orElse(null);
        User user = this.userManager.getCurrentuser();

       BoardingTicket ticket  = new BoardingTicket(flight, seatNumber);
       ticket.getFlight().addTicket(ticket);
       ticket.getFlight().removeFreeSeat(seatNumber);
       ticket.setUser(user);

       user.addTicket(ticket);

       this.entityManager.persist(ticket);
       return ticket;
    }

    public void returnFlightTicket(Long id, int seatNumber) {

        Query q = this.entityManager.createQuery(
                "SELECT b FROM BoardingTicket b JOIN b.flight f WHERE f.id = :id AND b.seatNumber = :seatNumber");
        q.setParameter("id", id);
        q.setParameter("seatNumber", seatNumber);

        BoardingTicket ticket = (BoardingTicket)q.getSingleResult();
        ticket.getFlight().removeTicket(ticket);
        ticket.getFlight().addFreeSeat(seatNumber);
        ticket.getUser().remomveTicket(ticket);
        ticket.removeUser();

        this.entityManager.remove(ticket);

    }

    public void deleteFlight(Long id) {
        Flight flight = this.flightRepository.findById(id).orElse(null);

        for (BoardingTicket ticket : flight.getTickets()) {
            ticket.getUser().remomveTicket(ticket);
            ticket.removeUser();
        }

        this.ticketRepository.deleteByFlight(flight);
        this.flightRepository.delete(flight);
    }

    public List<BoardingTicket> getUserTickets() {
        User user = this.userManager.getCurrentuser();
        Query q = this.entityManager.createQuery(
                "SELECT b FROM BoardingTicket b WHERE b.user = :user");
        q.setParameter("user", user);

        return q.getResultList();
    }
}
