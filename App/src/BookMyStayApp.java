// Version 10.0

import java.util.*;

// Reservation
class Reservation {
    String guest;
    String type;
    String id;

    Reservation(String g, String t, String i) {
        guest = g;
        type = t;
        id = i;
    }
}

// Inventory
class RoomInventory {
    HashMap<String, Integer> map = new HashMap<>();

    RoomInventory() {
        map.put("Single Room", 1);
        map.put("Double Room", 1);
    }

    void increase(String type) {
        map.put(type, map.get(type) + 1);
    }

    void show() {
        System.out.println("Inventory: " + map);
    }
}

// Cancellation Service
class CancellationService {
    private HashMap<String, Reservation> bookings;
    private Stack<String> rollbackStack;
    private RoomInventory inv;

    CancellationService(HashMap<String, Reservation> b, RoomInventory i) {
        bookings = b;
        inv = i;
        rollbackStack = new Stack<>();
    }

    void cancel(String id) {

        if (!bookings.containsKey(id)) {
            System.out.println("Error: Booking not found for ID " + id);
            return;
        }

        Reservation r = bookings.get(id);

        // push to rollback stack
        rollbackStack.push(id);

        // restore inventory
        inv.increase(r.type);

        // remove booking
        bookings.remove(id);

        System.out.println("Cancelled: " + r.guest + " | " + r.type + " | ID: " + id);
    }

    void showRollback() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        // Inventory
        RoomInventory inv = new RoomInventory();

        // Confirmed bookings (simulated)
        HashMap<String, Reservation> bookings = new HashMap<>();
        bookings.put("R1", new Reservation("Alice", "Single Room", "R1"));
        bookings.put("R2", new Reservation("Bob", "Double Room", "R2"));

        // Cancellation Service
        CancellationService cs = new CancellationService(bookings, inv);

        inv.show();

        // Cancel booking
        cs.cancel("R1");

        // Try invalid cancellation
        cs.cancel("R3");

        inv.show();
        cs.showRollback();
    }
}