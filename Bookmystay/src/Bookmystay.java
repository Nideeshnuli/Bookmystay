
import java.util.*;

    // Reservation (Represents booking request)
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

        public void display() {
            System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
        }
    }

    // Booking Request Queue (FIFO)
    class BookingQueue {
        private Queue<Reservation> queue;

        public BookingQueue() {
            queue = new LinkedList<>();
        }

        // Add request (enqueue)
        public void addRequest(Reservation reservation) {
            queue.offer(reservation);
            System.out.println("Request added for " + reservation.getGuestName());
        }

        // View all requests (READ-ONLY)
        public void viewRequests() {
            if (queue.isEmpty()) {
                System.out.println("No booking requests.");
                return;
            }

            System.out.println("\n--- Booking Requests (FIFO Order) ---");
            for (Reservation r : queue) {
                r.display();
            }
        }

        // Get next request (for future processing)
        public Reservation getNextRequest() {
            return queue.peek(); // no removal (no mutation)
        }
    }

    // Main Class
    public class Bookmystay {

        public static void main(String[] args) {

            // Create Booking Queue
            BookingQueue bookingQueue = new BookingQueue();

            // Guest submits booking requests
            Reservation r1 = new Reservation("Nideesh", "Single");
            Reservation r2 = new Reservation("Arun", "Deluxe");
            Reservation r3 = new Reservation("Priya", "Suite");

            // Add to queue (FIFO)
            bookingQueue.addRequest(r1);
            bookingQueue.addRequest(r2);
            bookingQueue.addRequest(r3);

            // View requests (order preserved)
            bookingQueue.viewRequests();

            // Peek next request (no removal, no allocation)
            Reservation next = bookingQueue.getNextRequest();
            if (next != null) {
                System.out.println("\nNext request to process:");
                next.display();
            }
        }
    }





