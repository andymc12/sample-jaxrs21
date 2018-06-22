package io.openliberty.sample.scheduling;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class SchedulingApp extends Application {

    @PostConstruct
    public void setupInitialSchedule() {
        SchedulingResource sr = new SchedulingResource();
        sr.addNewConsultant("Al");
        sr.createBooking(new Booking()
                .forConsultant("Al")
                .withCustomer("Acme Air")
                .starting(LocalDate.of(2018, 7, 5))
                .ending(LocalDate.of(2018, 7, 13)));
        sr.createBooking(new Booking()
                .forConsultant("Al")
                .withCustomer("Springfield Nuclear Power Plant")
                .starting(LocalDate.of(2018, 7, 16))
                .ending(LocalDate.of(2018, 7, 27)));

        sr.addNewConsultant("Bob");
        sr.createBooking(new Booking()
                .forConsultant("Bob")
                .withCustomer("Planet Express")
                .starting(LocalDate.of(2018, 7, 9))
                .ending(LocalDate.of(2018, 7, 11)));
        sr.createBooking(new Booking()
                .forConsultant("Bob")
                .withCustomer("Central Perk")
                .starting(LocalDate.of(2018, 7, 16))
                .ending(LocalDate.of(2018, 7, 19)));

        sr.addNewConsultant("Carl");
        sr.createBooking(new Booking()
                .forConsultant("Carl")
                .withCustomer("Dunder Mifflin")
                .starting(LocalDate.of(2018, 7, 10))
                .ending(LocalDate.of(2018, 8, 3)));
        
    }
}
