// Version 8.0

import java.util.*;

// Reservation
class Reservation {
    String guest;
    String roomType;
    String roomId;

    Reservation(String g, String r, String id) {
        guest = g;
        roomType = r;
        roomId = id;
    }

    void show() {
        System.out.println(guest + " | " + roomType + " | ID: " + roomId);
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> list = new ArrayList<>();

    void add(Reservation r) {
        list.add(r);
    }

    List<Reservation> getAll() {
        return list;
    }
}

// Report Service
class ReportService {
    private BookingHistory history;

    ReportService(BookingHistory h) {
        history = h;
    }

    void showAllBookings() {
        System.out.println("=== Booking History ===");
        for (Reservation r : history.getAll()) {
            r.show();
        }
    }

    void summary() {
        System.out.println("\n=== Summary Report ===");
        System.out.println("Total Bookings: " + history.getAll().size());
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        // Add confirmed bookings (simulated)
        history.add(new Reservation("Alice", "Single Room", "SI101"));
        history.add(new Reservation("Bob", "Double Room", "DO201"));
        history.add(new Reservation("Charlie", "Suite Room", "SU301"));

        // Reporting
        ReportService report = new ReportService(history);

        report.showAllBookings();
        report.summary();
    }
}