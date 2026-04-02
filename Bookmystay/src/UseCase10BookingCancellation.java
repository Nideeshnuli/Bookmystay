import java.util.*;

// Reservation model
class Reservation {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking Service (handles active bookings)
class BookingService {

    private Map<String, Reservation> activeBookings = new HashMap<>();
    private Map<String, Integer> inventory = new HashMap<>();

    public BookingService() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public void addBooking(Reservation reservation) {
        activeBookings.put(reservation.getReservationId(), reservation);

        // reduce inventory
        String type = reservation.getRoomType();
        inventory.put(type, inventory.get(type) - 1);
    }

    public Reservation getReservation(String reservationId) {
        return activeBookings.get(reservationId);
    }

    public void removeBooking(String reservationId) {
        activeBookings.remove(reservationId);
    }

    public void incrementInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public int getInventory(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId, BookingService bookingService) {

        Reservation reservation = bookingService.getReservation(reservationId);

        // Validation
        if (reservation == null) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        String roomType = reservation.getRoomType();

        // Step 1: Record rollback
        rollbackStack.push(reservationId);

        // Step 2: Restore inventory
        bookingService.incrementInventory(roomType);

        // Step 3: Remove booking
        bookingService.removeBooking(reservationId);

        // Output
        System.out.println("Booking Cancellation");
        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);

        // Show rollback history
        System.out.println("\nRollback History (Most Recent First):");
        for (int i = rollbackStack.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + rollbackStack.get(i));
        }

        System.out.println("\nUpdated " + roomType + " Room Availability: "
                + bookingService.getInventory(roomType));
    }
}

// Main Class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        BookingService bookingService = new BookingService();
        CancellationService cancellationService = new CancellationService();

        // Simulate existing bookings
        Reservation r1 = new Reservation("Single-1", "Single");
        bookingService.addBooking(r1);

        // Perform cancellation
        cancellationService.cancelBooking("Single-1", bookingService);
    }
}




