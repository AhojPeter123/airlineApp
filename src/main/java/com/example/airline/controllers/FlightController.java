package com.example.airline.controllers;

import com.example.airline.app.FlightFilter;
import com.example.airline.model.BoardingTicket;
import com.example.airline.model.Flight;
import com.example.airline.service.FlightManager;
import com.example.airline.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class FlightController {

    @Autowired
    private FlightManager flightManager;

    @Autowired
    private UserManager userManager;

    @PostMapping("/find-flight")
    public String findFlights(@RequestParam(required = false) String fromCity,
                              @RequestParam(required = false) String toCity,
                              @RequestParam(required = false) LocalDate departureDate,
                              Model model) {

        if ((fromCity != null) && fromCity.isBlank()) {
            fromCity = null;
        }

        if ((toCity != null) && toCity.isBlank()) {
            toCity = null;
        }

        FlightFilter filter = new FlightFilter();
        filter.setFromCity(fromCity);
        filter.setToCity(toCity);
        filter.setDepartureDateSince(departureDate);

        List<Flight> flights = this.flightManager.findFlights(filter);

        model.addAttribute("flights", flights);

        return "results";
    }

    @PostMapping("/book-flight")
    public String bookFlight(@RequestParam("flightCode") Long flightCode,
                             @RequestParam("seatNumber") int seatNumber,
                             Model model) {
        BoardingTicket ticket = this.flightManager.bookFlight(flightCode, seatNumber);
        model.addAttribute(ticket);
        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String showMenu() {
        if(this.userManager.getCurrentuser().isAdmin()) return "admin-menu";
        return "menu";
    }

    @GetMapping("/booked-flights")
    public String showBookedFlights(Model model) {
        model.addAttribute("tickets", this.flightManager.getUserTickets());
        return "flights";
    }

    @PostMapping("/corresponding-flights")
    public String findCorrespondingFlights(@RequestParam("arrivalDate") LocalDate arrivalDate,
                                           @RequestParam("toCity") String fromCity,
                                           Model model) {
        FlightFilter filter = new FlightFilter();
        filter.setDepartureDateSince(arrivalDate);
        filter.setFromCity(fromCity);

        model.addAttribute("flights", this.flightManager.findFlights(filter));
        return "results";
    }

    @PostMapping("/remove-flight")
    public String removeFlight(@RequestParam("flightCode") Long flightCode,
                               @RequestParam("seatNumber") int seatNumber,
                               Model model) {
        this.flightManager.returnFlightTicket(flightCode, seatNumber);
        return "redirect:/menu";
    }

    @PostMapping("/choose-seat")
    public String chooseSeat(@RequestParam("flightCode") Long flightCode,
                             Model model) {
        FlightFilter filter = new FlightFilter();
        filter.setCode(flightCode);

        Flight flight = flightManager.findFlights(filter).get(0);

        if (flight != null) {
           model.addAttribute("flight", flight);
        }

        return "seat-selection";
    }

    @GetMapping("/delete-flight")
    public String getDeleteFlight(Model model) {
        FlightFilter filter = new FlightFilter();
        List<Flight> flights = flightManager.findFlights(filter);
        model.addAttribute("flights", flights);
        return "delete-flight";
    }

    @PostMapping("/delete-flight")
    public String postDeleteFlight(@RequestParam("flightCode") Long flightCode,
                               Model model) {
        this.flightManager.deleteFlight(flightCode);
        return "redirect:/menu";

    }

    @GetMapping("/add-flight")
    public String getAddFlight() {
        return "add-flight";
    }

    @PostMapping("/add-flight")
    public String postAddFlight(@RequestParam("departureTime") LocalTime departureTime,
                                @RequestParam("arrivalTime") LocalTime arrivalTime,
                                @RequestParam("departureDate") LocalDate departureDate,
                                @RequestParam("arrivalDate") LocalDate arrivalDate,
                                @RequestParam("fromAirport") String fromAirport,
                                @RequestParam("toAirport") String toAirport,
                                @RequestParam("planeSize") int planeSize) {
        this.flightManager.addFlight(departureTime, arrivalTime, departureDate, arrivalDate, fromAirport, toAirport, planeSize);
        return "redirect:/menu";
    }


}
