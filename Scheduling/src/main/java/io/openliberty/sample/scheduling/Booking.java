package io.openliberty.sample.scheduling;

import java.time.LocalDate;

public class Booking {

    private String customerName;
    private String consultantName;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getCustomerName() {
        return customerName;
    }
    public Booking withCustomer(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public Booking forConsultant(String consultantName) {
        this.consultantName = consultantName;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Booking starting(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Booking ending(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
    
    @Override
    public String toString() {
        return consultantName + " " + customerName + " " + startDate + " " + endDate;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
