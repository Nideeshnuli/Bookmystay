import java.util.*;

// Custom Exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Model
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

// Validator Class
class InvalidBookingValidator {

    private static final List<String> VALID_ROOM_TYPES =
            Arrays.asList("Single", "Double", "Suite");

    // Validate booking input
    public static void validate(String roomType, int availableRooms)
            throws InvalidBookingException {

        // Validate room type
        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Validate availability
        if (availableRooms <= 0) {
            throw new InvalidBookingException(
                    "No rooms available for room type: " + roomType);
        }
    }
}

// Booking Service
class BookingService {

    private Map<String, Integer> inventory;

    public BookingService() {
        inventory = new HashMap<>();

        // Initial availability
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 0); // intentionally 0 to trigger validation
    }

    public Reservation bookRoom(String reservationId, String guestName, String roomType)
            throws InvalidBookingException {

        int available = inventory.getOrDefault(roomType, -1);

        // Validate before processing (Fail-Fast)
        InvalidBookingValidator.validate(roomType, available);

        // Update inventory safely
        inventory.put(roomType, available - 1);

        return new Reservation(reservationId, guestName, roomType);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// Main Class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        BookingService bookingService = new BookingService();

        // Test cases
        String[][] testBookings = {
                {"RES201", "Alice", "Single"},
                {"RES202", "Bob", "Suite"},     // should fail (0 availability)
                {"RES203", "Charlie", "Deluxe"} // should fail (invalid type)
        };

        for (String[] booking : testBookings) {
            try {
                System.out.println("\nProcessing booking for " + booking[1]);

                Reservation reservation = bookingService.bookRoom(
                        booking[0], booking[1], booking[2]);

                System.out.println("Booking Successful: " + reservation);

            } catch (InvalidBookingException e) {
                // Graceful failure handling
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        // System continues running safely
        System.out.println("\nFinal Inventory State:");
        bookingService.displayInventory();
    }
}





