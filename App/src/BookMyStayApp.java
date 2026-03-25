// Version 6.0

import java.util.*;

// Reservation
class Reservation {
    String guestName;
    String roomType;

    Reservation(String g, String r) {
        guestName = g;
        roomType = r;
    }
}

// Inventory Service
class RoomInventory {
    private HashMap<String, Integer> map;

    RoomInventory() {
        map = new HashMap<>();
        map.put("Single Room", 2);
        map.put("Double Room", 1);
        map.put("Suite Room", 1);
    }

    int getAvailability(String type) {
        return map.getOrDefault(type, 0);
    }

    void reduce(String type) {
        map.put(type, map.get(type) - 1);
    }
}

// Booking Service
class BookingService {
    private Queue<Reservation> queue;
    private RoomInventory inv;

    private Set<String> usedIds; // prevent duplicates
    private HashMap<String, Set<String>> allocated; // type -> IDs

    BookingService(Queue<Reservation> q, RoomInventory i) {
        queue = q;
        inv = i;
        usedIds = new HashSet<>();
        allocated = new HashMap<>();
    }

    void process() {
        System.out.println("=== Processing Bookings ===\n");

        while (!queue.isEmpty()) {
            Reservation r = queue.poll();

            if (inv.getAvailability(r.roomType) > 0) {

                // generate unique ID
                String id;
                do {
                    id = r.roomType.substring(0, 2).toUpperCase()
                            + (int)(Math.random() * 1000);
                } while (usedIds.contains(id));

                usedIds.add(id);

                // store allocation
                allocated.putIfAbsent(r.roomType, new HashSet<>());
                allocated.get(r.roomType).add(id);

                // update inventory
                inv.reduce(r.roomType);

                System.out.println("Confirmed: " + r.guestName +
                        " | Room: " + r.roomType +
                        " | ID: " + id);
            } else {
                System.out.println("Rejected: " + r.guestName +
                        " | No " + r.roomType + " available");
            }
        }
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        // Queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();

        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Single Room"));
        queue.add(new Reservation("Charlie", "Single Room")); // should fail
        queue.add(new Reservation("David", "Double Room"));

        // Inventory
        RoomInventory inv = new RoomInventory();

        // Booking Service
        BookingService bs = new BookingService(queue, inv);

        // Process bookings
        bs.process();
    }
}