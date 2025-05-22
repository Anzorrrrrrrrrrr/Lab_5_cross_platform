package com.gymbooking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class BookingApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookingServiceImpl service = new BookingServiceImpl();

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = readInt("Ваш вибір: ");

            switch (choice) {
                case 1 -> createBooking();
                case 2 -> listBookings();
                case 3 -> viewBooking();
                case 4 -> updateBooking();
                case 5 -> deleteBooking();
                case 0 -> System.out.println("До побачення!");
                default -> System.out.println("Невірний вибір!");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n=== Система бронювання тренувань ===");
        System.out.println("1. Створити бронювання");
        System.out.println("2. Переглянути всі бронювання");
        System.out.println("3. Переглянути бронювання за ID");
        System.out.println("4. Оновити бронювання");
        System.out.println("5. Видалити бронювання");
        System.out.println("0. Вийти");
    }

    private static void createBooking() {
        int id = readInt("ID: ");
        String name = readString("Ім'я клієнта: ");
        String type = readString("Тип тренування: ");
        LocalDateTime dateTime = readDateTime("Дата і час (рррр-мм-ддTгод:хв): ");
        int duration = readInt("Тривалість (хв): ");

        Booking b = new Booking(id, name, type, dateTime, duration);
        service.addBooking(b);
        System.out.println("Бронювання додано.");
    }

    private static void listBookings() {
        List<Booking> bookings = service.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("Список порожній.");
        } else {
            bookings.forEach(System.out::println);
        }
    }

    private static void viewBooking() {
        int id = readInt("Введіть ID: ");
        Booking b = service.getById(id);
        if (b != null) System.out.println(b);
        else System.out.println("Бронювання не знайдено.");
    }

    private static void updateBooking() {
        int id = readInt("Введіть ID бронювання для оновлення: ");
        Booking existing = service.getById(id);
        if (existing == null) {
            System.out.println("Бронювання не знайдено.");
            return;
        }

        String name = readString("Нове ім'я (" + existing.getCustomerName() + "): ");
        String type = readString("Новий тип (" + existing.getTrainingType() + "): ");
        LocalDateTime dt = readDateTime("Нова дата (" + existing.getDateTime() + "): ");
        int dur = readInt("Нова тривалість (" + existing.getDurationMinutes() + "): ");

        Booking updated = new Booking(id, name, type, dt, dur);
        service.updateBooking(updated);
        System.out.println("Оновлено.");
    }

    private static void deleteBooking() {
        int id = readInt("ID для видалення: ");
        service.deleteBooking(id);
        System.out.println("Бронювання видалено.");
    }

    // ----------------- Допоміжні методи -----------------
    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Введіть число: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // спожити залишок (новий рядок!)
        return value;
    }


    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }


    private static LocalDateTime readDateTime(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return LocalDateTime.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Невірний формат (рррр-мм-ддTгод:хв): ");
            }
        }
    }
}
