import java.util.HashMap;
import java.util.Map;
public class Bookmystay {


    /**
     * Book My Stay Application
     * Demonstrates centralized room inventory management using HashMap.
     *
     * This program initializes a centralized inventory system
     * where room availability is stored and managed.
     *
     * @author Student
     * @version 3.1
     */


        public static void main(String[] args) {

            System.out.println("=====================================");
            System.out.println("        Book My Stay Application");
            System.out.println("        Hotel Booking System v3.1");
            System.out.println("=====================================");

            // Initialize inventory
            RoomInventory inventory = new RoomInventory();

            // Display current inventory
            System.out.println("\nCurrent Room Inventory:");
            inventory.displayInventory();

            // Example availability check
            System.out.println("\nChecking availability for Single Room:");
            System.out.println("Available: " + inventory.getAvailability("Single Room"));

            // Update availability example
            System.out.println("\nUpdating availability (booking 1 Single Room)...");
            inventory.updateAvailability("Single Room", -1);

            // Display updated inventory
            System.out.println("\nUpdated Room Inventory:");
            inventory.displayInventory();

            System.out.println("\nApplication execution completed.");
        }
    }

    /**
     * RoomInventory manages centralized room availability.
     * It acts as the single source of truth for room inventory.
     *
     * @version 3.0
     */
    class RoomInventory {

        private HashMap<String, Integer> inventory;

        /**
         * Constructor initializes room availability.
         */
        public RoomInventory() {
            inventory = new HashMap<>();

            inventory.put("Single Room", 10);
            inventory.put("Double Room", 5);
            inventory.put("Suite Room", 2);
        }

        /**
         * Retrieves current availability for a room type.
         */
        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        /**
         * Updates room availability.
         */
        public void updateAvailability(String roomType, int change) {
            int current = inventory.getOrDefault(roomType, 0);
            inventory.put(roomType, current + change);
        }

        /**
         * Displays current inventory state.
         */
        public void displayInventory() {
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
            }
        }
    }

