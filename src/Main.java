import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize data
        Database.initializeData();
        
        // Start the application
        RentalSystem rentalSystem = new RentalSystem();
        rentalSystem.start();
    }
}