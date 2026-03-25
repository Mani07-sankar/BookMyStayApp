// Version 11.0

import java.util.*;

// Reservation
class Reservation {
    String guest;
    String type;

    Reservation(String g, String t) {
        guest = g;
        type = t;
    }
}

// Shared Inventory (Thread-Safe)
class RoomInventory {
    private HashMap<String, Integer> map = new HashMap<>();

    RoomInventory() {
        map.put("Single Room", 2);
        map.put("Double Room", 1);
    }

    // synchronized method (critical section)
    public synchronized boolean allocate(String type) {
        int count = map.getOrDefault(type, 0);

        if (count > 0) {
            map.put(type, count - 1);
            return true;
        }
        return false;
    }

    void show() {
        System.out.println("Inventory: " + map);
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {
    private Queue<Reservation> queue;
    private RoomInventory inv;

    BookingProcessor(Queue<Reservation> q, RoomInventory i) {
        queue = q;
        inv = i;
    }

    public void run() {
        while (true) {
            Reservation r;

            // synchronized block for queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.poll();
            }

            // allocate room (thread-safe)
            boolean success = inv.allocate(r.type);

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " Confirmed: " + r.guest + " | " + r.type);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " Failed: " + r.guest + " | No " + r.type);
            }
        }
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        // Shared Queue
        Queue<Reservation> queue = new LinkedList<>();

        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Single Room"));
        queue.add(new Reservation("Charlie", "Single Room"));
        queue.add(new Reservation("David", "Double Room"));
        queue.add(new Reservation("Eve", "Double Room"));

        // Shared Inventory
        RoomInventory inv = new RoomInventory();

        // Multiple Threads
        BookingProcessor t1 = new BookingProcessor(queue, inv);
        BookingProcessor t2 = new BookingProcessor(queue, inv);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {}

        System.out.println("\nFinal ");
        inv.show();
    }
}