package com.example.airline.model;

import jakarta.persistence.*;
@Entity
@Table
public class BoardingTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Flight flight;
    private int seatNumber;
    @ManyToOne
    private User user;

    protected BoardingTicket() {}

    public BoardingTicket(Flight flight, int seatNumber) {
        this.flight = flight;
        this.seatNumber = seatNumber;
    }

    public long getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void deleteUser(User user) {
        this.user = null;
    }

    public void removeUser() {
        this.user = null;
    }
}
