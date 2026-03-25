// Version 4.0

import java.util.*;

// Room (Domain Model)
class Room {
    String type;
    int beds;
    double price;

    Room(String t, int b, double p) {
        type = t;
        beds = b;
        price = p;
    }

    void show() {
        System.out.println(type + " | Beds:" + beds + " | ₹" + price);
    }
}

// Inventory (State Holder)
class RoomInventory {
    private HashMap<String, Integer> map;

    RoomInventory() {
        map = new HashMap<>();
        map.put("Single Room", 10);
        map.put("Double Room", 5);
        map.put("Suite Room", 0); // unavailable
    }

    int getAvailability(String type) {
        return map.getOrDefault(type, 0);
    }
}

// Search Service (Read-Only)
class RoomSearch {
    private RoomInventory inv;
    private List<Room> rooms;

    RoomSearch(RoomInventory i, List<Room> r) {
        inv = i;
        rooms = r;
    }

    void search() {
        System.out.println("=== Available Rooms ===\n");

        for (Room r : rooms) {
            int available = inv.getAvailability(r.type);

            if (available > 0) {   // filter unavailable
                r.show();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        // Inventory
        RoomInventory inv = new RoomInventory();

        // Room list
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Single Room", 1, 2000));
        rooms.add(new Room("Double Room", 2, 3500));
        rooms.add(new Room("Suite Room", 3, 6000));

        // Search
        RoomSearch search = new RoomSearch(inv, rooms);
        search.search();
    }
}