import com.gymbooking.Booking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    private static List<Booking> bookings = new ArrayList<>(); // Список для зберігання бронювань

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gym Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu operationsMenu = new JMenu("Operations");

        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem createItem = new JMenuItem("Create Booking");
        JMenuItem readItem = new JMenuItem("Read Bookings");
        JMenuItem updateItem = new JMenuItem("Update Booking");
        JMenuItem deleteItem = new JMenuItem("Delete Booking");
        JMenuItem searchItem = new JMenuItem("Search Booking");
        JMenu sortMenu = new JMenu("Sort Bookings");  // Змінено на підменю
        JMenuItem sortByTimeItem = new JMenuItem("Sort by Time");
        JMenuItem sortByPriceItem = new JMenuItem("Sort by Price");

        exitItem.addActionListener(e -> System.exit(0));

        createItem.addActionListener(e -> {
            String customerName = JOptionPane.showInputDialog(frame, "Enter customer name:");
            String trainerName = JOptionPane.showInputDialog(frame, "Enter trainer name:");
            String activity = JOptionPane.showInputDialog(frame, "Enter activity:");
            LocalDateTime bookingTime = LocalDateTime.now();
            double price = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter price:"));
            int bookingId = bookings.size() + 1;

            Booking newBooking = new Booking(bookingId, customerName, trainerName, activity, bookingTime, price);
            bookings.add(newBooking);
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
                JOptionPane.showMessageDialog(frame, "Booking with ID " + bookingId + " deleted.");
            } else {
                JOptionPane.showMessageDialog(frame, "Booking not found.");
            }
        });

        searchItem.addActionListener(e -> {
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings to search.");
                return;
            }
            String customerName = JOptionPane.showInputDialog(frame, "Enter customer name to search:");
            List<Booking> foundBookings = bookings.stream()
                    .filter(b -> b.getCustomerName().equalsIgnoreCase(customerName))
                    .toList();
            if (foundBookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings found for " + customerName);
            } else {
                StringBuilder sb = new StringBuilder("Found bookings:\n");
                for (Booking b : foundBookings) {
                    sb.append(b).append("\n");
                }
                JOptionPane.showMessageDialog(frame, "Found bookings:\n" + sb.toString());
            }
        });

        sortByTimeItem.addActionListener(e -> {
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings to sort.");
                return;
            }
            Collections.sort(bookings, Comparator.comparing(Booking::getBookingTime));
            JOptionPane.showMessageDialog(frame, "Bookings sorted by time.");
            readItem.getActionListeners()[0].actionPerformed(null);
        });

        sortByPriceItem.addActionListener(e -> {
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No bookings to sort.");
                return;
            }
            Collections.sort(bookings, Comparator.comparingDouble(Booking::getPrice));
            JOptionPane.showMessageDialog(frame, "Bookings sorted by price.");
            readItem.getActionListeners()[0].actionPerformed(null);
        });

        fileMenu.add(exitItem);
        operationsMenu.add(createItem);
        operationsMenu.add(readItem);
        operationsMenu.add(updateItem);
        operationsMenu.add(deleteItem);
        operationsMenu.add(searchItem);
        operationsMenu.add(sortMenu);
        sortMenu.add(sortByTimeItem);
        sortMenu.add(sortByPriceItem);

        menuBar.add(fileMenu);
        menuBar.add(operationsMenu);
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }
}