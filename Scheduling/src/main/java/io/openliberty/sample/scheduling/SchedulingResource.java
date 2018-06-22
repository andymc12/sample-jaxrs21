package io.openliberty.sample.scheduling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Path("/schedule")
public class SchedulingResource {

    private final static Set<String> consultants = new HashSet<>();
    private final static List<Booking> bookings = new ArrayList<>();

    @GET
    public Set<String> allConsultants() {
        return consultants;
    }

    @GET
    @Path("/bookings")
    public List<Booking> allBookings() {
        return bookings;
    }

    @GET
    @Path("/consultant/{name}")
    public List<Booking> allBookingsForConsultant(@PathParam("name") String consultantName) {
        return bookings.stream()
                       .filter(b -> b.getConsultantName().equals(consultantName))
                       .sorted(Comparator.comparing(Booking::getStartDate))
                       .collect(Collectors.toList());
    }

    @GET
    @Path("/customer/{name}")
    public List<Booking> allBookingsForCustomer(@PathParam("name") String customerName) {
        return bookings.stream()
                       .filter(b -> b.getCustomerName().equals(customerName))
                       .sorted(Comparator.comparing(Booking::getStartDate))
                       .collect(Collectors.toList());
    }

    @GET
    @Path("/available")
    public List<String> allConsultantsAvailable(@QueryParam("start") LocalDate start, 
                                                @QueryParam("end") LocalDate end) {
        System.out.println("allConsultantsAvailable " + start + "  " + end);
        return consultants.stream().filter(consultant -> {
            for (Booking b : allBookingsForConsultant(consultant)) {
                if (b.getStartDate().isBefore(end) && b.getEndDate().isAfter(start)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    @POST
    public void addNewConsultant(String consultantName) {
        consultants.add(consultantName);
    }

    @POST
    @Path("/bookings")
    public Boolean createBooking(Booking booking) {
        System.out.println("Scheduling - createBooking: " + booking);
        String consultantName = booking.getConsultantName();
        // check if consultant is already booked for the requested dates:
        if (allBookingsForConsultant(consultantName)
            .stream()
            .anyMatch(b -> b.getStartDate().isBefore(booking.getEndDate())
                        && b.getEndDate().isAfter(booking.getStartDate()))) {
            throw new WebApplicationException("Consultant " + consultantName + " is already booked for that time", 409);
        }
        boolean b = bookings.add(booking);
        System.out.println("returning " + b);
        return b;
    }
}
