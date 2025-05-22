package com.gymbooking;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BookingServiceImpl {
    private List<Booking> bookings;
    private final ObjectMapper mapper;
    private final File file = new File("bookings.json");

    public BookingServiceImpl() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        bookings = loadFromFile();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        saveToFile();
    }

    public void deleteBooking(int id) {
        bookings.removeIf(b -> b.getId() == id);
        saveToFile();
    }

    public void updateBooking(Booking updated) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId() == updated.getId()) {
                bookings.set(i, updated);
                saveToFile();
                return;
            }
        }
    }

    public Booking getById(int id) {
        for (Booking b : bookings) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    private void saveToFile() {
        try {
            mapper.writeValue(file, bookings);
        } catch (IOException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    private List<Booking> loadFromFile() {
        if (!file.exists()) return new ArrayList<>();
        try {
            return mapper.readValue(file, new TypeReference<List<Booking>>() {});
        } catch (IOException e) {
            System.out.println("Помилка зчитування з файлу: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
