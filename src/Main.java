import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gymbooking.Booking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    private static List<Booking> bookings = new ArrayList<>();
    private static final String FILE_PATH = "bookings.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // Налаштування ObjectMapper для роботи з LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static void main(String[] args) {
        // Завантаження збережених даних при запуску
        loadBookingsFromFile();

        JFrame frame = new JFrame("Gym Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Збереження перед закриттям
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveBookingsToFile();
                System.exit(0);
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu operationsMenu = new JMenu("Operations");

        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem createItem = new JMenuItem("Create Booking");
        JMenuItem readItem = new JMenuItem("Read Bookings");
        JMenuItem updateItem = new JMenuItem("Update Booking");
        JMenuItem deleteItem = new JMenuItem("Delete Booking");
        JMenuItem searchByCustomerItem = new JMenuItem("Search by Customer Name");
        JMenuItem searchByTrainerItem = new JMenuItem("Search by Trainer Name");
        JMenu sortMenu = new JMenu("Sort Bookings");
        JMenuItem sortByTimeItem = new JMenuItem("Sort by Time");
        JMenuItem sortByPriceItem = new JMenuItem("Sort by Price");

        exitItem.addActionListener(e -> {
            saveBookingsToFile();
            System.exit(0);
        });

        createItem.addActionListener(e -> {
            String customerName = JOptionPane.showInputDialog(frame, "Enter customer name:");
            String trainerName = JOptionPane.showInputDialog(frame, "Enter trainer name:");
            String activity = JOptionPane.showInputDialog(frame, "Enter activity:");
            LocalDateTime bookingTime = LocalDateTime.now();
            double price = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter price:"));
            int bookingId = bookings.size() + 1;

            Booking newBooking = new Booking(bookingId, customerName, trainerName, activity, bookingTime, price);
            bookings.add(newBooking);
            saveBookingsToFile();
            JOptionPane.showMessageDialog(frame, "Booking created: " + newBooking);
        });

        readItem.addActionListener(e -> {
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings available.");
            } else {
                StringBuilder sb = new StringBuilder("All bookings:\n");
                for (Booking booking : bookings) {
                    sb.append(booking).append("\n");
                }
                JOptionPane.showMessageDialog(frame, sb.toString());
            }
        });

        updateItem.addActionListener(e -> {
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings to update.");
                return;
            }
            int bookingId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter booking ID to update:"));
            Booking bookingToUpdate = bookings.stream()
                    .filter(b -> b.getBookingId() == bookingId)
                    .findFirst()
                    .orElse(null);

            if (bookingToUpdate == null) {
                JOptionPane.showMessageDialog(frame, "Booking not found.");
            } else {
                String newCustomerName = JOptionPane.showInputDialog(frame, "Enter new customer name:", bookingToUpdate.getCustomerName());
                bookingToUpdate.setCustomerName(newCustomerName);
                saveBookingsToFile();
                JOptionPane.showMessageDialog(frame, "Booking updated: " + bookingToUpdate);
            }
        });

        deleteItem.addActionListener(e -> {
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings to delete.");
                return;
            }
            int bookingId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter booking ID to delete:"));
            boolean removed = bookings.removeIf(b -> b.getBookingId() == bookingId);
            if (removed) {
                saveBookingsToFile();
                JOptionPane.showMessageDialog(frame, "Booking with ID " + bookingId + " deleted.");
            } else {
                JOptionPane.showMessageDialog(frame, "Booking not found.");
            }
        });

        searchByCustomerItem.addActionListener(e -> {
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings to search.");
                return;
            }
            String customerName = JOptionPane.showInputDialog(frame, "Enter customer name to search:");
            if (customerName != null && !customerName.isEmpty()) {
                List<Booking> foundBookings = bookings.stream()
                        .filter(b -> b.getCustomerName().equalsIgnoreCase(customerName))
                        .toList();
                if (foundBookings.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No bookings found for customer: " + customerName);
                } else {
                    StringBuilder sb = new StringBuilder("Found bookings:\n");
                    for (Booking b : foundBookings) {
                        sb.append(b).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, sb.toString());
                }
            }
        });

        searchByTrainerItem.addActionListener(e -> {
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings to search.");
                return;
            }
            String trainerName = JOptionPane.showInputDialog(frame, "Enter trainer name to search:");
            if (trainerName != null && !trainerName.isEmpty()) {
                List<Booking> foundBookings = bookings.stream()
                        .filter(b -> b.getTrainerName().equalsIgnoreCase(trainerName))
                        .toList();
                if (foundBookings.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No bookings found for trainer: " + trainerName);
                } else {
                    StringBuilder sb = new StringBuilder("Found bookings:\n");
                    for (Booking b : foundBookings) {
                        sb.append(b).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, sb.toString());
                }
            }
        });

        sortByTimeItem.addActionListener(e -> {
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings to sort.");
                return;
            }
            Collections.sort(bookings, Comparator.comparing(Booking::getBookingTime));
            saveBookingsToFile();
            JOptionPane.showMessageDialog(frame, "Bookings sorted by time.");
            readItem.getActionListeners()[0].actionPerformed(null);
        });

        sortByPriceItem.addActionListener(e -> {
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings to sort.");
                return;
            }
            Collections.sort(bookings, Comparator.comparingDouble(Booking::getPrice));
            saveBookingsToFile();
            JOptionPane.showMessageDialog(frame, "Bookings sorted by price.");
            readItem.getActionListeners()[0].actionPerformed(null);
        });

        fileMenu.add(exitItem);
        operationsMenu.add(createItem);
        operationsMenu.add(readItem);
        operationsMenu.add(updateItem);
        operationsMenu.add(deleteItem);
        operationsMenu.add(searchByCustomerItem);
        operationsMenu.add(searchByTrainerItem);
        operationsMenu.add(sortMenu);
        sortMenu.add(sortByTimeItem);
        sortMenu.add(sortByPriceItem);

        menuBar.add(fileMenu);
        menuBar.add(operationsMenu);
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    private static void saveBookingsToFile() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), bookings);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving bookings: " + e.getMessage());
        }
    }

    private static void loadBookingsFromFile() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                Booking[] loadedBookings = objectMapper.readValue(file, Booking[].class);
                bookings.clear();
                Collections.addAll(bookings, loadedBookings);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading bookings: " + e.getMessage());
            }
        }
    }
}