import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking System
class ConcurrentBookingProcessor {

    private Map<String, Integer> inventory = new HashMap<>();
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();
    private Map<String, Integer> roomCounters = new HashMap<>();

    public ConcurrentBookingProcessor() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);

        roomCounters.put("Single", 0);
        roomCounters.put("Double", 0);
        roomCounters.put("Suite", 0);
    }

    // Add booking request (synchronized)
    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
    }

    // Process booking (critical section)
    public void processBookings() {
        while (true) {
            BookingRequest request;

            synchronized (this) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                request = bookingQueue.poll();
            }

            allocateRoom(request);
        }
    }

    // Critical section for allocation
    private void allocateRoom(BookingRequest request) {
        synchronized (this) {
            String type = request.roomType;

            int available = inventory.getOrDefault(type, 0);

            if (available > 0) {
                inventory.put(type, available - 1);

                int count = roomCounters.get(type) + 1;
                roomCounters.put(type, count);

                String roomId = type + "-" + count;

                System.out.println("Booking confirmed for Guest: "
                        + request.guestName + ", Room ID: " + roomId);
            } else {
                System.out.println("Booking failed for Guest: "
                        + request.guestName + " (No " + type + " rooms available)");
            }
        }
    }

    public void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms: " + inventory.get(type));
        }
    }
}

// Worker Thread
class BookingWorker extends Thread {

    private ConcurrentBookingProcessor processor;

    public BookingWorker(ConcurrentBookingProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void run() {
        processor.processBookings();
    }
}

// Main Class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        // Simulated concurrent requests
        processor.addRequest(new BookingRequest("Abhi", "Single"));
        processor.addRequest(new BookingRequest("Amnathi", "Double"));
        processor.addRequest(new BookingRequest("Kural", "Suite"));
        processor.addRequest(new BookingRequest("Subha", "Single"));

        // Create multiple threads
        Thread t1 = new BookingWorker(processor);
        Thread t2 = new BookingWorker(processor);
        Thread t3 = new BookingWorker(processor);

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory
        processor.displayInventory();
    }
}



