// Version 2.0

abstract class Room {
    String type;
    int beds;
    double price;

    Room(String t, int b, double p) {
        type = t;
        beds = b;
        price = p;
    }

    abstract void show();
}

class SingleRoom extends Room {
    SingleRoom() { super("Single Room", 1, 2000); }
    void show() { System.out.println(type+" | Beds:"+beds+" | ₹"+price); }
}

class DoubleRoom extends Room {
    DoubleRoom() { super("Double Room", 2, 3500); }
    void show() { System.out.println(type+" | Beds:"+beds+" | ₹"+price); }
}

class SuiteRoom extends Room {
    SuiteRoom() { super("Suite Room", 3, 6000); }
    void show() { System.out.println(type+" | Beds:"+beds+" | ₹"+price); }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        int s = 10, d = 5, su = 2;

        r1.show(); System.out.println("Available: " + s);
        r2.show(); System.out.println("Available: " + d);
        r3.show(); System.out.println("Available: " + su);
    }
}