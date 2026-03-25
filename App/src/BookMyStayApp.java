// Version 6.0

import java.util.*;

// Reservation
class Reservation {
    String name;
    String type;

    Reservation(String n, String t) {
        name = n;
        type = t;
    }
}

// Inventory
class RoomInventory {
    HashMap<String, Integer> map = new HashMap<>();

    RoomInventory() {
        map.put("Single Room", 2);
        map.put("Double Room", 1);
        map.put("Suite Room", 1);
    }

    int get(String t) {
        return map.getOrDefault(t, 0);
    }

    void reduce(String t) {
        map.put(t, map.get(t) - 1);
    }
}

// Booking Service
class BookingService {
    Queue<Reservation> q;
    RoomInventory inv;

    Set<String> ids = new HashSet<>();
    HashMap<String, Set<String>> allocated = new HashMap<>();

    BookingService(Queue<Reservation> q, RoomInventory i) {
        this.q = q;
        this.inv = i;
    }

    void process() {
        while (!q.isEmpty()) {
            Reservation r = q.poll();

            if (inv.get(r.type) > 0) {

                String id;
                do {
                    id = r.type.substring(0, 2).toUpperCase() + (int)(Math.random()*1000);
                } while (ids.contains(id));

                ids.add(id);

                allocated.putIfAbsent(r.type, new HashSet<>());
                allocated.get(r.type).add(id);

                inv.reduce(r.type);

                System.out.println("Confirmed: " + r.name + " | " + r.type + " | ID: " + id);
            } else {
                System.out.println("Rejected: " + r.name + " | No " + r.type);
            }
        }
    }
}

// Main
public class BookMyStayApp {
    public static void main(String[] args) {

        Queue<Reservation> q = new LinkedList<>();

        q.add(new Reservation("Alice", "Single Room"));
        q.add(new Reservation("Bob", "Single Room"));
        q.add(new Reservation("Charlie", "Single Room"));
        q.add(new Reservation("David", "Double Room"));

        RoomInventory inv = new RoomInventory();

        BookingService bs = new BookingService(q, inv);

        bs.process();
    }
}