
import java.util.*;

        // Reservation (from Queue)
        class Reservation {
            private String guestName;
            private String roomType;

            public Reservation(String guestName, String roomType) {
                this.guestName = guestName;
                this.roomType = roomType;
            }

            public String getGuestName() {
                return guestName;
            }

            public String getRoomType() {
                return roomType;
            }
        }

        // Inventory Service (State Holder)
        class Inventory {
            private Map<String, Integer> availability = new HashMap<>();

            public void addRoom(String type, int count) {
                availability.put(type, count);
            }

            public int getAvailability(String type) {
                return availability.getOrDefault(type, 0);
            }

            public void decrement(String type) {
                availability.put(type, availability.get(type) - 1);
            }
        }

        // Booking Queue (FIFO)
        class BookingQueue {
            private Queue<Reservation> queue = new LinkedList<>();

            public void addRequest(Reservation r) {
                queue.offer(r);
            }

            public Reservation getNextRequest() {
                return queue.poll(); // dequeue for processing
            }

            public boolean isEmpty() {
                return queue.isEmpty();
            }
        }

        // Booking Service (Allocation Logic)
        class BookingService {

            // Map: RoomType → Set of Allocated Room IDs
            private Map<String, Set<String>> allocatedRooms = new HashMap<>();

            // Global Set to ensure uniqueness
            private Set<String> usedRoomIds = new HashSet<>();

            // Allocate rooms
            public void processBookings(BookingQueue queue, Inventory inventory) {

                while (!queue.isEmpty()) {

                    Reservation r = queue.getNextRequest();
                    String type = r.getRoomType();

                    System.out.println("\nProcessing request for: " + r.getGuestName());

                    // Check availability
                    if (inventory.getAvailability(type) > 0) {

                        // Generate unique room ID
                        String roomId = generateRoomId(type);

                        // Ensure uniqueness using Set
                        while (usedRoomIds.contains(roomId)) {
                            roomId = generateRoomId(type);
                        }

                        // Atomic Operation (Assign + Update)
                        usedRoomIds.add(roomId);

                        allocatedRooms
                                .computeIfAbsent(type, k -> new HashSet<>())
                                .add(roomId);

                        inventory.decrement(type);

                        // Confirmation
                        System.out.println("Booking Confirmed!");
                        System.out.println("Guest: " + r.getGuestName());
                        System.out.println("Room Type: " + type);
                        System.out.println("Room ID: " + roomId);

                    } else {
                        System.out.println("Booking Failed! No rooms available for " + type);
                    }
                }
            }

            // Room ID Generator
            private String generateRoomId(String type) {
                return type.substring(0, 1).toUpperCase() + (int)(Math.random() * 1000);
            }
        }

        // Main Class
        public class Bookmystay {

            public static void main(String[] args) {

                // Step 1: Setup Inventory
                Inventory inventory = new Inventory();
                inventory.addRoom("Single", 2);
                inventory.addRoom("Deluxe", 1);
                inventory.addRoom("Suite", 1);

                // Step 2: Setup Booking Queue (FIFO)
                BookingQueue queue = new BookingQueue();
                queue.addRequest(new Reservation("Nideesh", "Single"));
                queue.addRequest(new Reservation("Arun", "Single"));
                queue.addRequest(new Reservation("Priya", "Single")); // will fail
                queue.addRequest(new Reservation("Kiran", "Suite"));

                // Step 3: Process Bookings
                BookingService service = new BookingService();
                service.processBookings(queue, inventory);
            }
        }




