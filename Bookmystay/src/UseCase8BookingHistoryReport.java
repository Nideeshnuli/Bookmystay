import java.util.*;

// Represents a confirmed reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double totalCost;

    public Reservation(String reservationId, String guestName, String roomType, double totalCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.totalCost = totalCost;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Total Cost: ₹" + totalCost;
    }
}

// Maintains booking history
class BookingHistory {

    // List preserves insertion order
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

// Generates reports
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("=== Booking History ===");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getTotalCost();
        }

        System.out.println("\n=== Booking Summary Report ===");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings (from previous use cases)
        Reservation r1 = new Reservation("RES101", "Alice", "Single Room", 2500);
        Reservation r2 = new Reservation("RES102", "Bob", "Double Room", 4000);
        Reservation r3 = new Reservation("RES103", "Charlie", "Suite Room", 7000);

        // Add to history (chronological order)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin views booking history
        reportService.displayAllBookings(history.getAllReservations());

        // Admin generates report
        reportService.generateSummary(history.getAllReservations());
    }
}





