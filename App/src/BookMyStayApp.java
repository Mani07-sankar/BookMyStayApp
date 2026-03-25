// Version 5.0

import java.util.*;

// Reservation (Actor)
class Reservation {
    String guestName;
    String roomType;

    Reservation(String g, String r) {
        guestName = g;
        roomType = r;
    }

    void show() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

// Booking Queue (FIFO)
class BookingQueue {
    private Queue<Reservation> queue;

    BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request
    void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Request added: " + r.guestName);
    }

    // Display queue
    void showQueue() {
        System.out.println("\n=== Booking Queue ===");
        for (Reservation r : queue) {
            r.show();
        }
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        BookingQueue bq = new BookingQueue();

        // Add booking requests (FIFO order)
        bq.addRequest(new Reservation("Alice", "Single Room"));
        bq.addRequest(new Reservation("Bob", "Double Room"));
        bq.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queue
        bq.showQueue();
    }
}