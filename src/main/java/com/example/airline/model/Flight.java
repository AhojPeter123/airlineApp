package com.example.airline.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    @ManyToOne
    private Airport fromAirport;
    @ManyToOne
    private Airport toAirport;
    @OneToMany
    private List<BoardingTicket> tickets;
    private List<Integer> freeSeats;
    private int planeSize;

    protected Flight() {}

    public Flight(LocalTime departureTime, LocalTime arrivalTime, LocalDate departureDate, LocalDate arrivalDate, Airport fromAirport, Airport toAirport, int planeSize) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.tickets = new LinkedList<>();
        this.planeSize = planeSize;
        this.freeSeats  =this.addSeats();
    }

    public Long getId() {
        return id;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public Airport getFromAirport() {
        return fromAirport;
    }

    public Airport getToAirport() {
        return toAirport;
    }

    public List<BoardingTicket> getTickets() {
        return tickets;
    }

    public List<Integer> getFreeSeats() {
        return freeSeats;
    }

    public int getPlaneSize() {
        return planeSize;
    }

    public String getFromCity() {
        return this.fromAirport.getCity().getName();
    }

    public String getToCity() {
        return this.toAirport.getCity().getName();
    }

    public void removeFreeSeat(Integer seatNum) {
        if (seatNum != null) {
            this.freeSeats.remove(seatNum);
        }
    }
    public void addFreeSeat(Integer seatNum) {
        if (seatNum != null) {
            this.freeSeats.add(seatNum);
            this.freeSeats.sort(null);
        }
    }
    public void removeTicket(BoardingTicket ticket) {
        if (ticket != null) {
            this.tickets.remove(ticket);
        }
    }

    public void addTicket(BoardingTicket ticket) {
        if (ticket != null) {
            this.tickets.add(ticket);
        }
    }

    @Override
    public String toString() {
        return "Flight " + this.id +
                "from " + this.fromAirport +
                "to " + this.toAirport +
                "leaves at " + this.departureDate +
                " " + this.departureTime;
    }

    private List<Integer> addSeats() {
        List<Integer> seats = new LinkedList<>();
        for(int i = 1; i <= this.planeSize; i++) {
            seats.add(i);
        }
        return seats;
    }
}
