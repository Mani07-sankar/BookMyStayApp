// Version 9.0

import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    InvalidBookingException(String msg) {
        super(msg);
    }
}

// Reservation
class Reservation {
    String guest;
    String roomType;

    Reservation(String g, String r) {
        guest = g;
        roomType = r;
    }
}

// Inventory
class RoomInventory {
    private HashMap<String, Integer> map = new HashMap<>();

    RoomInventory() {
        map.put("Single Room", 2);
        map.put("Double Room", 1);
        map.put("Suite Room", 1);
    }

    int get(String type) {
        return map.getOrDefault(type, -1);
    }

    void reduce(String type) throws InvalidBookingException {
        int count = map.get(type);

        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }

        map.put(type, count - 1);
    }
}

// Validator
class BookingValidator {

    static void validate(Reservation r, RoomInventory inv)
            throws InvalidBookingException {

        if (r.guest == null || r.guest.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (inv.get(r.roomType) == -1) {
            throw new InvalidBookingException("Invalid room type: " + r.roomType);
        }

        if (inv.get(r.roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.roomType);
        }
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inv = new RoomInventory();

        // Test cases (valid + invalid)
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("", "Double Room"); // invalid name
        Reservation r3 = new Reservation("Bob", "Luxury Room"); // invalid type
        Reservation r4 = new Reservation("Charlie", "Suite Room");

        Reservation[] list = {r1, r2, r3, r4};

        for (Reservation r : list) {
            try {
                BookingValidator.validate(r, inv);
                inv.reduce(r.roomType);

                System.out.println("Booking Confirmed: " + r.guest +
                        " | " + r.roomType);

            } catch (InvalidBookingException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}