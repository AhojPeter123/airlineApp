package com.example.airline.app;

import com.example.airline.model.*;
import com.example.airline.repository.AirportRepository;
import com.example.airline.repository.CityRepository;
import com.example.airline.repository.CountryRepository;
import com.example.airline.repository.FlighRepository;
import com.example.airline.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
@EnableAutoConfiguration
@EntityScan("com.example.airline")
@EnableTransactionManagement
@EnableJpaRepositories("com.example.airline")
@ComponentScan("com.example.airline")
public class AppConfig {
    @Autowired
    private UserManager userManager;

    @Bean
    public CommandLineRunner demo(FlighRepository flightRepository, AirportRepository airportRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        return (args) -> {
            User admin = new User();
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setEmail("admin");
            admin.setPassword("admin");
            admin.setConfirmPassword("admin");
            admin.setRole(User.UserRole.ADMIN);

            this.userManager.registerNewAccount(admin);

            User user = new User();
            user.setFirstName("user");
            user.setLastName("user");
            user.setEmail("user");
            user.setPassword("user");
            user.setConfirmPassword("user");
            user.setRole(User.UserRole.USER);

            this.userManager.registerNewAccount(user);

            Country slovakia = new Country("Slovakia");

            Country greece = new Country("Greece");

            Country italy = new Country("Italy");

            Country germany = new Country("Germany");

            Country unitedKingdom = new Country("United Kingdom");

            Country czechRepublic = new Country("Czech Republic");



            countryRepository.save(slovakia);

            countryRepository.save(greece);

            countryRepository.save(italy);

            countryRepository.save(germany);

            countryRepository.save(unitedKingdom);

            countryRepository.save(czechRepublic);



            City bratislava = new City("Bratislava", slovakia);

            City athens = new City("Athens", greece);

            City rome = new City("Rome", italy);

            City london = new City("London", unitedKingdom);

            City berlin = new City("Berlin", germany);

            City prague = new City("Prague", czechRepublic);



            cityRepository.save(bratislava);

            cityRepository.save(athens);

            cityRepository.save(rome);

            cityRepository.save(london);

            cityRepository.save(berlin);

            cityRepository.save(prague);



            Airport bratislavaAirport = new Airport("Bratislava airport 1", bratislava);

            Airport bratislavaAirport2 = new Airport("Bratislava airport 2", bratislava);

            Airport athensAirport = new Airport("Athens airport 1", athens);

            Airport romeAirport = new Airport("Rome airport 1", rome);

            Airport berlinAirport = new Airport("Berlin airport 1", berlin);

            Airport londonAirport = new Airport("London airport 1", london);

            Airport pragueAirport = new Airport("Prague airport 1", prague);



            airportRepository.save(bratislavaAirport);

            airportRepository.save(bratislavaAirport2);

            airportRepository.save(athensAirport);

            airportRepository.save(romeAirport);

            airportRepository.save(berlinAirport);

            airportRepository.save(londonAirport);

            airportRepository.save(pragueAirport);



            flightRepository.save(new Flight(LocalTime.of(8, 30), LocalTime.of(11, 15), LocalDate.of(2023, 7, 7),

                    LocalDate.of(2023, 7, 7), bratislavaAirport, athensAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(11, 30), LocalTime.of(12, 15), LocalDate.of(2023, 7, 7),

                    LocalDate.of(2023, 7, 7), athensAirport, romeAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(11, 35), LocalTime.of(13, 45), LocalDate.of(2023, 7, 7),

                    LocalDate.of(2023, 7, 7), athensAirport, berlinAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(14, 00), LocalTime.of(15, 00), LocalDate.of(2023, 7, 7),

                    LocalDate.of(2023, 7, 7), berlinAirport, londonAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(14, 45), LocalTime.of(16, 00), LocalDate.of(2023, 7, 7),

                    LocalDate.of(2023, 7, 7), berlinAirport, bratislavaAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(15, 20), LocalTime.of(17, 00), LocalDate.of(2023, 7, 7),

                    LocalDate.of(2023, 7, 7), romeAirport, pragueAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(16, 30), LocalTime.of(19, 00), LocalDate.of(2023, 7, 7),

                    LocalDate.of(2023, 7, 7), bratislavaAirport2, londonAirport, 20));



            flightRepository.save(new Flight(LocalTime.of(9, 30), LocalTime.of(12, 15), LocalDate.of(2023, 8, 6),

                    LocalDate.of(2023, 8, 6), bratislavaAirport, athensAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(12, 30), LocalTime.of(13, 15), LocalDate.of(2023, 8, 6),

                    LocalDate.of(2023, 8, 6), athensAirport, romeAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(12, 35), LocalTime.of(14, 45), LocalDate.of(2023, 8, 6),

                    LocalDate.of(2023, 8, 6), athensAirport, berlinAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(14, 50), LocalTime.of(16, 00), LocalDate.of(2023, 8, 6),

                    LocalDate.of(2023, 8, 6), berlinAirport, londonAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(14, 55), LocalTime.of(17, 00), LocalDate.of(2023, 8, 6),

                    LocalDate.of(2023, 8, 6), berlinAirport, bratislavaAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(16, 20), LocalTime.of(18, 00), LocalDate.of(2023, 8, 6),

                    LocalDate.of(2023, 8, 6), romeAirport, pragueAirport, 20));

            flightRepository.save(new Flight(LocalTime.of(17, 30), LocalTime.of(20, 00), LocalDate.of(2023, 8, 6),

                    LocalDate.of(2023, 8, 6), bratislavaAirport2, londonAirport, 20));
        };
    }
}
