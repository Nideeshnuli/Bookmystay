import java.util.*;

    // Room Domain Model
    class Room {
        private String type;
        private double price;
        private List<String> amenities;

        public Room(String type, double price, List<String> amenities) {
            this.type = type;
            this.price = price;
            this.amenities = amenities;
        }

        public String getType() {
            return type;
        }

        public double getPrice() {
            return price;
        }

        public List<String> getAmenities() {
            return amenities;
        }

        public void displayDetails() {
            System.out.println("Room Type: " + type);
            System.out.println("Price: ₹" + price);
            System.out.println("Amenities: " + amenities);
            System.out.println("-----------------------------");
        }
    }

    // Inventory (State Holder)
    class Inventory {
        private Map<String, Integer> availabilityMap;

        public Inventory() {
            availabilityMap = new HashMap<>();
        }

        public void addRoom(String roomType, int count) {
            availabilityMap.put(roomType, count);
        }

        // READ-ONLY ACCESS
        public int getAvailability(String roomType) {
            return availabilityMap.getOrDefault(roomType, 0);
        }

        public Set<String> getAllRoomTypes() {
            return availabilityMap.keySet();
        }
    }

    // Search Service (Read-only logic)
    class SearchService {

        public void searchAvailableRooms(Inventory inventory, Map<String, Room> roomMap) {

            System.out.println("\nAvailable Rooms:\n");

            boolean found = false;

            for (String roomType : inventory.getAllRoomTypes()) {

                int availableCount = inventory.getAvailability(roomType);

                // Validation Logic (Defensive Programming)
                if (availableCount > 0) {

                    Room room = roomMap.get(roomType);

                    if (room != null) {
                        room.displayDetails();
                        System.out.println("Available Count: " + availableCount);
                        System.out.println("=============================");
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("No rooms available.");
            }
        }
    }

    // Main Class
    public class Bookmystay {


        public static void main(String[] args) {

            // Step 1: Create Room Objects (Domain Model)
            Room single = new Room("Single", 2000, Arrays.asList("WiFi", "TV"));
            Room deluxe = new Room("Deluxe", 4000, Arrays.asList("WiFi", "TV", "AC"));
            Room suite = new Room("Suite", 7000, Arrays.asList("WiFi", "TV", "AC", "Mini Bar"));

            // Step 2: Store in Map
            Map<String, Room> roomMap = new HashMap<>();
            roomMap.put("Single", single);
            roomMap.put("Deluxe", deluxe);
            roomMap.put("Suite", suite);

            // Step 3: Setup Inventory (State Holder)
            Inventory inventory = new Inventory();
            inventory.addRoom("Single", 5);
            inventory.addRoom("Deluxe", 0);   // Will be filtered out
            inventory.addRoom("Suite", 2);

            // Step 4: Search Service (Read-only)
            SearchService searchService = new SearchService();

            // Step 5: Guest initiates search
            searchService.searchAvailableRooms(inventory, roomMap);
        }
    }


