import java.io.*;
import java.util.*;

// Reservation model (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

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
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// Wrapper class to persist system state
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state from file
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading data. Starting with safe defaults.");
        }

        return null;
    }
}

// Main Class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService persistenceService = new PersistenceService();

        // Try loading previous state
        SystemState state = persistenceService.load();

        Map<String, Integer> inventory;
        List<Reservation> bookingHistory;

        if (state != null) {
            inventory = state.inventory;
            bookingHistory = state.bookingHistory;

            System.out.println("\nRecovered Inventory: " + inventory);
            System.out.println("Recovered Bookings:");
            for (Reservation r : bookingHistory) {
                System.out.println(r);
            }

        } else {
            // Initialize fresh state
            inventory = new HashMap<>();
            inventory.put("Single", 3);
            inventory.put("Double", 2);
            inventory.put("Suite", 1);

            bookingHistory = new ArrayList<>();

            System.out.println("\nInitialized new system state.");
        }

        // Simulate new booking
        Reservation newBooking = new Reservation("RES501", "David", "Single");

        bookingHistory.add(newBooking);
        inventory.put("Single", inventory.get("Single") - 1);

        System.out.println("\nNew Booking Added: " + newBooking);

        // Save updated state
        SystemState newState = new SystemState(inventory, bookingHistory);
        persistenceService.save(newState);
    }
}
