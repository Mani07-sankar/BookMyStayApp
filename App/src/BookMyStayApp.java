// Version 7.0

import java.util.*;

// Service (Add-On)
class Service {
    String name;
    double cost;

    Service(String n, double c) {
        name = n;
        cost = c;
    }
}

// Add-On Manager
class AddOnManager {
    private HashMap<String, List<Service>> map = new HashMap<>();

    // Add service to reservation
    void addService(String resId, Service s) {
        map.putIfAbsent(resId, new ArrayList<>());
        map.get(resId).add(s);
        System.out.println("Added " + s.name + " to " + resId);
    }

    // Calculate total cost
    double getTotal(String resId) {
        double total = 0;
        if (map.containsKey(resId)) {
            for (Service s : map.get(resId)) {
                total += s.cost;
            }
        }
        return total;
    }

    // Show services
    void show(String resId) {
        System.out.println("\nServices for " + resId + ":");
        if (map.containsKey(resId)) {
            for (Service s : map.get(resId)) {
                System.out.println(s.name + " - ₹" + s.cost);
            }
            System.out.println("Total Add-On Cost: ₹" + getTotal(resId));
        } else {
            System.out.println("No services selected.");
        }
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        AddOnManager manager = new AddOnManager();

        String resId = "R101"; // existing reservation ID

        // Add services
        manager.addService(resId, new Service("Breakfast", 500));
        manager.addService(resId, new Service("WiFi", 200));
        manager.addService(resId, new Service("Airport Pickup", 1000));

        // Display services
        manager.show(resId);
    }
}