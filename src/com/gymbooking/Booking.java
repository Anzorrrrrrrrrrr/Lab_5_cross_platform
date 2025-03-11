package com.gymbooking;


import java.time.LocalDateTime;

public class Booking {
    private int bookingId;
    private String customerName;
    private String trainerName;
    private String activity;
    private LocalDateTime bookingTime;
    private double price;


    public Booking(int bookingId, String customerName, String trainerName, String activity, LocalDateTime bookingTime, double price) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.trainerName = trainerName;
        this.activity = activity;
        this.bookingTime = bookingTime;
        this.price = price;
    }


    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }
    public String getActivity() { return activity; }
    public void setActivity(String activity) { this.activity = activity; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", customerName='" + customerName + '\'' +
                ", trainerName='" + trainerName + '\'' +
                ", activity='" + activity + '\'' +
                ", bookingTime=" + bookingTime +
                ", price=" + price +
                '}';
    }
}