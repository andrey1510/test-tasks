import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.FlightBuilder;
import com.gridnine.testing.services.FilterFlights;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();

        List<Flight> flightsExceptForDepartedAfterNow = FilterFlights.excludeFlightsDepartedAfterNow(flights);
        System.out.println("Перелёты за исключением перелётов с сегментами с вылетом до текущего момента времени:");
        flightsExceptForDepartedAfterNow.forEach(System.out::println);

        List<Flight> flightsExceptForArrivalBeforeDeparture = FilterFlights.excludeFlightsWithArrivalBeforeDeparture(flights);
        System.out.println("\nПерелёты за исключением перелётов с сегментами, где дата прилёта раньше даты вылета:");
        flightsExceptForArrivalBeforeDeparture.forEach(System.out::println);

        List<Flight> flightsExceptExceedingTwoHoursOnGround = FilterFlights.excludeFlightsExceedingTwoHoursGroundTime(flights);
        System.out.println("\nПерелёты за исключением перелётов, где общее время на земле превышает два часа:");
        flightsExceptExceedingTwoHoursOnGround.forEach(System.out::println);
    }

}
