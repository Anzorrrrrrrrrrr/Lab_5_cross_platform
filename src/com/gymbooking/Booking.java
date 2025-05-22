package com.gymbooking;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private String customerName;
    private String trainingType;
    private LocalDateTime dateTime;
    private int durationMinutes;

    // Конструктор
    public Booking(int id, String customerName, String trainingType, LocalDateTime dateTime, int durationMinutes) {
        this.id = id;
        this.customerName = customerName;
        this.trainingType = trainingType;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
    }

    // Порожній конструктор (важливо для десеріалізації)
    public Booking() {}

    // Геттери і сеттери
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTrainingType() {
        return trainingType;
    }
    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", trainingType='" + trainingType + '\'' +
                ", dateTime=" + dateTime +
                ", durationMinutes=" + durationMinutes +
                '}';
    }
}
