package com.example.airline.app;

import com.example.airline.model.Flight;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightFilter {

    private LocalDate departureDateSince;
    private LocalDate departureDateAfter;
    private LocalDate departureDateBefore;
    private LocalDate departureDateAtLatest;

    private LocalDate arrivalDateSince;
    private LocalDate arrivalDateAfter;
    private LocalDate arrivalDateBefore;
    private LocalDate arrivalDateAtLatest;

    private Long code;
    private String fromCity;
    private String toCity;

    public Specification<Flight> getJPAPredicate() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (this.departureDateSince != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("departureDate"), this.departureDateSince));
            }

            if (this.departureDateAfter != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("departureDate"), this.departureDateAfter));
            }

            if (this.departureDateBefore != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("departureDate"), this.departureDateBefore));
            }

            if (this.departureDateAtLatest != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("departureDate"), this.departureDateAtLatest));
            }

            if (this.arrivalDateSince != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("arrivalDate"), this.arrivalDateSince));
            }

            if (this.arrivalDateAfter != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("arrivalDate"), this.arrivalDateAfter));
            }

            if (this.arrivalDateBefore != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("arrivalDate"), this.arrivalDateBefore));
            }

            if (this.arrivalDateAtLatest != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("arrivalDate"), this.arrivalDateAtLatest));
            }

            if (this.code != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), this.code));
            }

            if (this.fromCity != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.join("fromAirport").get("city")), this.fromCity.toLowerCase()));
            }

            if (this.arrivalDateAtLatest != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.join("toAirport").get("city")), this.toCity.toLowerCase()));
            }
            return criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[0])));
        };
    }

    public void setDepartureDateSince(LocalDate departureDateSince) {
        this.departureDateSince = departureDateSince;
    }

    public void setDepartureDateAfter(LocalDate departureDateAfter) {
        this.departureDateAfter = departureDateAfter;
    }

    public void setDepartureDateBefore(LocalDate departureDateBefore) {
        this.departureDateBefore = departureDateBefore;
    }

    public void setDepartureDateAtLatest(LocalDate departureDateAtLatest) {
        this.departureDateAtLatest = departureDateAtLatest;
    }

    public void setArrivalDateSince(LocalDate arrivalDateSince) {
        this.arrivalDateSince = arrivalDateSince;
    }

    public void setArrivalDateAfter(LocalDate arrivalDateAfter) {
        this.arrivalDateAfter = arrivalDateAfter;
    }

    public void setArrivalDateBefore(LocalDate arrivalDateBefore) {
        this.arrivalDateBefore = arrivalDateBefore;
    }

    public void setArrivalDateAtLatest(LocalDate arrivalDateAtLatest) {
        this.arrivalDateAtLatest = arrivalDateAtLatest;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }
}
