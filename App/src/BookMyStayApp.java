// Version 3.0

import java.util.HashMap;

class RoomInventory {
    private HashMap<String, Integer> map;

    RoomInventory() {
        map = new HashMap<>();
        map.put("Single Room", 10);
        map.put("Double Room", 5);
        map.put("Suite Room", 2);
    }

    void show() {
        System.out.println("=== Inventory ===");
        for (String key : map.keySet()) {
            System.out.println(key + " -> " + map.get(key));
        }
    }

    void update(String type, int count) {
        map.put(type, count);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inv = new RoomInventory();

        inv.show();

        System.out.println("\nUpdating Single Room...\n");
        inv.update("Single Room", 8);

        inv.show();
    }
}