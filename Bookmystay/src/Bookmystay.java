
    // Abstract Class (Abstraction)
    abstract class Room {
        private int beds;
        private int size; // in sq ft
        private double price;

        public Room(int beds, int size, double price) {
            this.beds = beds;
            this.size = size;
            this.price = price;
        }

        public int getBeds() {
            return beds;
        }

        public int getSize() {
            return size;
        }

        public double getPrice() {
            return price;
        }

        // Abstract method
        public abstract String getRoomType();

        // Common display method (Encapsulation)
        public void displayDetails(int availability) {
            System.out.println("Room Type: " + getRoomType());
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sq ft");
            System.out.println("Price: ₹" + price);
            System.out.println("Available Rooms: " + availability);
            System.out.println("----------------------------");
        }
    }

    // Concrete Class - Single Room
    class SingleRoom extends Room {
        public SingleRoom() {
            super(1, 200, 2000);
        }

        @Override
        public String getRoomType() {
            return "Single Room";
        }
    }

    // Concrete Class - Double Room
    class DoubleRoom extends Room {
        public DoubleRoom() {
            super(2, 350, 3500);
        }

        @Override
        public String getRoomType() {
            return "Double Room";
        }
    }

    // Concrete Class - Suite Room
    class SuiteRoom extends Room {
        public SuiteRoom() {
            super(3, 600, 7000);
        }

        @Override
        public String getRoomType() {
            return "Suite Room";
        }
    }

    // Main Class
    public class Bookmystay {

        public static void main(String[] args) {

            // Static Availability (simple variables)
            int singleAvailability = 5;
            int doubleAvailability = 3;
            int suiteAvailability = 2;

            // Polymorphism (Room reference)
            Room single = new SingleRoom();
            Room doub = new DoubleRoom();
            Room suite = new SuiteRoom();

            System.out.println("---- Hotel Room Details ----");

            // Display Details
            single.displayDetails(singleAvailability);
            doub.displayDetails(doubleAvailability);
            suite.displayDetails(suiteAvailability);
        }




