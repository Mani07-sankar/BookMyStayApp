// Version 12.0

import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
    String guest;
    String roomType;

    Reservation(String g, String r) {
        guest = g;
        roomType = r;
    }

    void show() {
        System.out.println(guest + " | " + roomType);
    }
}

// Inventory (Serializable)
class RoomInventory implements Serializable {
    HashMap<String, Integer> map = new HashMap<>();

    RoomInventory() {
        map.put("Single Room", 2);
        map.put("Double Room", 1);
    }

    void show() {
        System.out.println("Inventory: " + map);
    }
}

// Wrapper class for saving full system state
class SystemState implements Serializable {
    RoomInventory inventory;
    List<Reservation> bookings;

    SystemState(RoomInventory i, List<Reservation> b) {
        inventory = i;
        bookings = b;
    }
}

// Persistence Service
class PersistenceService {

    static void save(SystemState state) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("data.ser"));
            out.writeObject(state);
            out.close();
            System.out.println("Data saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    static SystemState load() {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("data.ser"));
            SystemState state = (SystemState) in.readObject();
            in.close();
            System.out.println("Data loaded successfully.");
            return state;
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        SystemState state = PersistenceService.load();

        RoomInventory inv;
        List<Reservation> bookings;

        if (state == null) {
            // Fresh start
            inv = new RoomInventory();
            bookings = new ArrayList<>();

            bookings.add(new Reservation("Alice", "Single Room"));
            bookings.add(new Reservation("Bob", "Double Room"));
        } else {
            // Restore state
            inv = state.inventory;
            bookings = state.bookings;
        }

        // Display current state
        inv.show();
        System.out.println("Bookings:");
        for (Reservation r : bookings) {
            r.show();
        }

        // Save before exit
        PersistenceService.save(new SystemState(inv, bookings));
    }
}