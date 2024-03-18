package com.gridnine.testing.services;

import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FilterFlights {

    public static List<Flight> excludeFlightsDepartedAfterNow(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                .toList();
    }

    public static List<Flight> excludeFlightsWithArrivalBeforeDeparture(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .toList();
    }

    public static List<Flight> excludeFlightsExceedingTwoHoursGroundTime(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> Duration.ofMinutes((calculateTotalGroundTime(flight)))
                        .compareTo(Duration.ofMinutes(120)) < 0)
                .toList();
    }

    private static long calculateTotalGroundTime(Flight flight) {

        List<Segment> segments = flight.getSegments();
        Duration totalGroundTime = Duration.ZERO;

        for (int i = 0; i < segments.size() - 1; i++) {
            Segment currentSegment = segments.get(i);
            Segment nextSegment = segments.get(i + 1);

            Duration groundTime = Duration.between(currentSegment.getArrivalDate(), nextSegment.getDepartureDate());
            if (groundTime.isNegative()) groundTime = Duration.ZERO;

            totalGroundTime = totalGroundTime.plus(groundTime);
        }

        return totalGroundTime.toMinutes();
    }
}
