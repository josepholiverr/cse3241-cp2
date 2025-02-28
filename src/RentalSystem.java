import java.util.Scanner;

public class RentalSystem {
    private Scanner scanner;
    private User currentUser;
    private boolean running;
    
    public RentalSystem() {
        scanner = new Scanner(System.in);
        running = true;
    }
    
    public void start() {
        System.out.println("Welcome to Equipment Rental System");
        login();
        
        while (running) {
            displayMainMenu();
            int choice = getUserChoice();
            processMainMenuChoice(choice);
        }
        
        scanner.close();
        System.out.println("Thank you for using Equipment Rental System. Goodbye!");
    }
    
    private void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        // For demonstration purposes, we'll just set a dummy user
        currentUser = Database.findUserByEmail(email);
        if (currentUser == null) {
            System.out.println("User not found. Creating a new account.");
            registerNewUser(email);
        } else {
            System.out.println("Welcome back, " + currentUser.getFirstName() + "!");
        }
    }
    
    private void registerNewUser(String email) {
        System.out.println("\n=== Create New Account ===");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        
        // Create a new user and add to database
        System.out.println("\nPlease select your nearest warehouse:");
        for (Warehouse warehouse : Database.getWarehouses()) {
            System.out.println(warehouse.getWarehouseId() + ". " + warehouse.getCity() + " - " + warehouse.getAddress());
        }
        System.out.print("Enter warehouse ID: ");
        int warehouseId = getUserChoice();

        // Create a new user and add to database
        currentUser = new User(Database.getNextUserId(), firstName, lastName, address, phone, email);
        currentUser.setWarehouseId(warehouseId);
        Database.addUser(currentUser);
        System.out.println("Account created successfully!");
    }
    
    private void displayMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Rent Equipment");
        System.out.println("2. Return Equipment");
        System.out.println("3. Equipment Delivery");
        System.out.println("4. Equipment Pickup");
        System.out.println("5. Search Records");
        System.out.println("6. Add Records");
        System.out.println("7. Edit/Delete Records");
        System.out.println("8. View Rental History");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void processMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                rentEquipment();
                break;
            case 2:
                returnEquipment();
                break;
            case 3:
                equipmentDelivery();
                break;
            case 4:
                equipmentPickup();
                break;
            case 5:
                searchRecords();
                break;
            case 6:
                addRecords();
                break;
            case 7:
                editDeleteRecords();
                break;
            case 8:
                viewRentalHistory();
                break;
            case 9:
                running = false;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void rentEquipment() {
        System.out.println("\n=== Rent Equipment ===");
        
        // Display available equipment from user's closest warehouse
        int warehouseId = currentUser.getWarehouseId();
        System.out.println("Equipment available at your warehouse (ID: " + warehouseId + "):");
        
        // Display equipment
        Database.getAvailableEquipment(warehouseId).forEach(equipment -> {
            System.out.println(equipment.getEquipmentId() + ". " + equipment.getDescription() + 
                               " - Type: " + equipment.getType() + " - Model: " + equipment.getModel() +
                               " - Daily Rate: $" + equipment.getDailyRate());
        });
        
        // Select equipment
        System.out.print("Enter equipment ID to rent (0 to cancel): ");
        int equipmentId = getUserChoice();
        if (equipmentId == 0) return;
        
        Equipment selected = Database.findEquipmentById(equipmentId);
        if (selected == null || !selected.getStatus().equals("Available")) {
            System.out.println("Invalid equipment selection or equipment not available.");
            return;
        }
        
        // Rental period
        System.out.print("Enter rental period (days): ");
        int days = getUserChoice();
        if (days <= 0) {
            System.out.println("Invalid rental period.");
            return;
        }
        
        // Create rental
        Rental rental = new Rental(
            Database.getNextRentalId(),
            currentUser.getUserId(),
            equipmentId,
            java.time.LocalDate.now().toString(),
            java.time.LocalDate.now().plusDays(days).toString(),
            null,
            "Pending",
            selected.getDailyRate() * days
        );
        
        // Create rental item
        RentalItem rentalItem = new RentalItem(
            Database.getNextRentalItemId(),
            rental.getRentalId(),
            equipmentId,
            selected.getDailyRate(),
            selected.getDailyRate() * days
        );
        
        // Update equipment status
        selected.setStatus("Rented");
        
        // Add to database
        Database.addRental(rental);
        Database.addRentalItem(rentalItem);
        
        System.out.println("Equipment rented successfully!");
        System.out.println("Rental Summary:");
        System.out.println("Equipment: " + selected.getDescription());
        System.out.println("Rental Period: " + days + " days");
        System.out.println("Total Cost: $" + rental.getRentalFee());
        
        // Delivery option
        System.out.print("Would you like drone delivery? (y/n): ");
        String deliveryChoice = scanner.nextLine().toLowerCase();
        if (deliveryChoice.equals("y")) {
            // Setup drone delivery
            Drone drone = Database.getAvailableDrone(warehouseId);
            if (drone != null) {
                DroneDelivery delivery = new DroneDelivery(
                    Database.getNextDeliveryId(),
                    rental.getRentalId(),
                    drone.getDroneId(),
                    "Delivery",
                    java.time.LocalDate.now().toString(),
                    null,
                    "Scheduled"
                );
                
                drone.setStatus("Assigned");
                Database.addDroneDelivery(delivery);
                
                System.out.println("Delivery scheduled with drone: " + drone.getName());
            } else {
                System.out.println("No drones available for delivery. Please pick up from warehouse.");
            }
        }
    }
    
    private void returnEquipment() {
        System.out.println("\n=== Return Equipment ===");
        
        // Show user's active rentals
        System.out.println("Your active rentals:");
        Database.getActiveRentals(currentUser.getUserId()).forEach(rental -> {
            Equipment equipment = Database.findEquipmentById(rental.getEquipmentId());
            System.out.println(rental.getRentalId() + ". " + equipment.getDescription() + 
                               " - Due: " + rental.getDueDate());
        });
        
        // Select rental to return
        System.out.print("Enter rental ID to return (0 to cancel): ");
        int rentalId = getUserChoice();
        if (rentalId == 0) return;
        
        Rental rental = Database.findRentalById(rentalId);
        if (rental == null || !rental.getUserId().equals(currentUser.getUserId())) {
            System.out.println("Invalid rental selection.");
            return;
        }
        
        // Update rental status
        rental.setReturnDate(java.time.LocalDate.now().toString());
        rental.setStatus("Returned");
        
        // Update equipment status
        Equipment equipment = Database.findEquipmentById(rental.getEquipmentId());
        equipment.setStatus("Available");
        
        System.out.println("Equipment returned successfully!");
        
        // Ask for review
        System.out.print("Would you like to leave a review? (y/n): ");
        String reviewChoice = scanner.nextLine().toLowerCase();
        if (reviewChoice.equals("y")) {
            System.out.print("Rating (1-5): ");
            int rating = getUserChoice();
            System.out.print("Comments: ");
            String comments = scanner.nextLine();
            
            Review review = new Review(
                Database.getNextReviewId(),
                currentUser.getUserId(),
                rental.getEquipmentId(),
                rating,
                comments,
                java.time.LocalDate.now().toString()
            );
            
            Database.addReview(review);
            System.out.println("Thank you for your review!");
        }
    }
    
    private void equipmentDelivery() {
        System.out.println("\n=== Equipment Delivery Status ===");
        
        // Show user's pending deliveries
        System.out.println("Your deliveries:");
        
        boolean hasDeliveries = false;
        for (Rental rental : Database.getActiveRentals(currentUser.getUserId())) {
            DroneDelivery delivery = Database.findDeliveryByRentalId(rental.getRentalId());
            if (delivery != null) {
                hasDeliveries = true;
                Equipment equipment = Database.findEquipmentById(rental.getEquipmentId());
                Drone drone = Database.findDroneById(delivery.getDroneId());
                
                System.out.println("Delivery ID: " + delivery.getDeliveryId());
                System.out.println("Equipment: " + equipment.getDescription());
                System.out.println("Status: " + delivery.getStatus());
                System.out.println("Drone: " + drone.getName() + " (Model: " + drone.getModel() + ")");
                System.out.println("-----");
            }
        }
        
        if (!hasDeliveries) {
            System.out.println("You have no active deliveries.");
        }
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void equipmentPickup() {
        System.out.println("\n=== Equipment Pickup ===");
        
        // Show user's active rentals ready for pickup
        System.out.println("Your equipment ready for pickup:");
        
        boolean hasPickups = false;
        for (Rental rental : Database.getActiveRentals(currentUser.getUserId())) {
            if (rental.getStatus().equals("Ready for Pickup")) {
                hasPickups = true;
                Equipment equipment = Database.findEquipmentById(rental.getEquipmentId());
                
                System.out.println("Rental ID: " + rental.getRentalId());
                System.out.println("Equipment: " + equipment.getDescription());
                System.out.println("Warehouse: " + Database.findWarehouseById(equipment.getWarehouseId()).getCity());
                System.out.println("-----");
            }
        }
        
        if (!hasPickups) {
            System.out.println("You have no equipment ready for pickup.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Select rental to pick up
        System.out.print("Enter rental ID to confirm pickup (0 to cancel): ");
        int rentalId = getUserChoice();
        if (rentalId == 0) return;
        
        Rental rental = Database.findRentalById(rentalId);
        if (rental == null || !rental.getUserId().equals(currentUser.getUserId()) || 
            !rental.getStatus().equals("Ready for Pickup")) {
            System.out.println("Invalid rental selection or not ready for pickup.");
            return;
        }
        
        // Update rental status
        rental.setStatus("Active");
        
        System.out.println("Pickup confirmed! Enjoy your equipment.");
    }
    
    private void searchRecords() {
        System.out.println("\n=== Search Records ===");
        System.out.println("1. Search Users");
        System.out.println("2. Search Equipment");
        System.out.println("3. Search Warehouses");
        System.out.print("Enter your choice: ");
        
        int choice = getUserChoice();
        switch (choice) {
            case 1:
                searchUsers();
                break;
            case 2:
                searchEquipment();
                break;
            case 3:
                searchWarehouses();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void searchUsers() {
        System.out.print("Enter name to search: ");
        String query = scanner.nextLine().toLowerCase();
        
        System.out.println("Search results:");
        Database.getUsers().stream()
            .filter(user -> user.getFirstName().toLowerCase().contains(query) || 
                           user.getLastName().toLowerCase().contains(query))
            .forEach(user -> {
                System.out.println("ID: " + user.getUserId() + ", Name: " + user.getFirstName() + " " + 
                                  user.getLastName() + ", Email: " + user.getEmail());
            });
    }
    
    private void searchEquipment() {
        System.out.print("Enter equipment description or type to search: ");
        String query = scanner.nextLine().toLowerCase();
        
        System.out.println("Search results:");
        Database.getAllEquipment().stream()
            .filter(equipment -> equipment.getDescription().toLowerCase().contains(query) || 
                               equipment.getType().toLowerCase().contains(query))
            .forEach(equipment -> {
                System.out.println("ID: " + equipment.getEquipmentId() + ", Description: " + 
                                  equipment.getDescription() + ", Type: " + equipment.getType() + 
                                  ", Status: " + equipment.getStatus());
            });
    }
    
    private void searchWarehouses() {
        System.out.print("Enter city or address to search: ");
        String query = scanner.nextLine().toLowerCase();
        
        System.out.println("Search results:");
        Database.getWarehouses().stream()
            .filter(warehouse -> warehouse.getCity().toLowerCase().contains(query) || 
                               warehouse.getAddress().toLowerCase().contains(query))
            .forEach(warehouse -> {
                System.out.println("ID: " + warehouse.getWarehouseId() + ", City: " + 
                                  warehouse.getCity() + ", Address: " + warehouse.getAddress());
            });
    }
    
    private void addRecords() {
        System.out.println("\n=== Add Records ===");
        System.out.println("1. Add User");
        System.out.println("2. Add Equipment");
        System.out.println("3. Add Warehouse");
        System.out.print("Enter your choice: ");
        
        int choice = getUserChoice();
        switch (choice) {
            case 1:
                addUser();
                break;
            case 2:
                addEquipment();
                break;
            case 3:
                addWarehouse();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void addUser() {
        System.out.println("\n=== Add User ===");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Warehouse ID: ");
        int warehouseId = getUserChoice();
        
        User user = new User(Database.getNextUserId(), firstName, lastName, address, phone, email);
        user.setWarehouseId(warehouseId);
        user.setStatus("Active");
        
        Database.addUser(user);
        System.out.println("User added successfully!");
    }
    
    private void addEquipment() {
        System.out.println("\n=== Add Equipment ===");
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Year: ");
        int year = getUserChoice();
        System.out.print("Serial Number: ");
        String serialNum = scanner.nextLine();
        System.out.print("Warehouse ID: ");
        int warehouseId = getUserChoice();
        System.out.print("Daily Rate: ");
        double rate = Double.parseDouble(scanner.nextLine());
        
        Equipment equipment = new Equipment(
            Database.getNextEquipmentId(),
            description,
            type,
            model,
            year,
            serialNum,
            "Available",
            "Warehouse",
            0.0, // weight
            warehouseId,
            java.time.LocalDate.now().plusYears(2).toString(),
            "", // dimensions
            "", // manufacturer
            rate
        );
        
        Database.addEquipment(equipment);
        System.out.println("Equipment added successfully!");
    }
    
    private void addWarehouse() {
        System.out.println("\n=== Add Warehouse ===");
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Manager Name: ");
        String managerName = scanner.nextLine();
        System.out.print("Storage Capacity: ");
        int storageCapacity = getUserChoice();
        System.out.print("Drone Capacity: ");
        int droneCapacity = getUserChoice();
        
        Warehouse warehouse = new Warehouse(
            Database.getNextWarehouseId(),
            city,
            address,
            phone,
            managerName,
            storageCapacity,
            droneCapacity,
            "Active"
        );
        
        Database.addWarehouse(warehouse);
        System.out.println("Warehouse added successfully!");
    }
    
    private void editDeleteRecords() {
        System.out.println("\n=== Edit/Delete Records ===");
        System.out.println("1. Edit/Delete User");
        System.out.println("2. Edit/Delete Equipment");
        System.out.println("3. Edit/Delete Warehouse");
        System.out.print("Enter your choice: ");
        
        int choice = getUserChoice();
        switch (choice) {
            case 1:
                editDeleteUser();
                break;
            case 2:
                editDeleteEquipment();
                break;
            case 3:
                editDeleteWarehouse();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void editDeleteUser() {
        System.out.print("Enter user ID: ");
        int userId = getUserChoice();
        
        User user = Database.findUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        
        System.out.println("User found: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("1. Edit User");
        System.out.println("2. Delete User");
        System.out.print("Enter your choice: ");
        
        int choice = getUserChoice();
        if (choice == 1) {
            // Edit user
            System.out.print("New First Name (" + user.getFirstName() + "): ");
            String firstName = scanner.nextLine();
            if (!firstName.isEmpty()) user.setFirstName(firstName);
            
            System.out.print("New Last Name (" + user.getLastName() + "): ");
            String lastName = scanner.nextLine();
            if (!lastName.isEmpty()) user.setLastName(lastName);
            
            System.out.print("New Address (" + user.getAddress() + "): ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) user.setAddress(address);
            
            System.out.print("New Phone (" + user.getPhone() + "): ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) user.setPhone(phone);
            
            System.out.print("New Email (" + user.getEmail() + "): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) user.setEmail(email);
            
            System.out.println("User updated successfully!");
        } else if (choice == 2) {
            // Delete user
            Database.removeUser(userId);
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("Invalid choice.");
        }
    }
    
    private void editDeleteEquipment() {
        System.out.print("Enter equipment ID: ");
        int equipmentId = getUserChoice();
        
        Equipment equipment = Database.findEquipmentById(equipmentId);
        if (equipment == null) {
            System.out.println("Equipment not found.");
            return;
        }
        
        System.out.println("Equipment found: " + equipment.getDescription());
        System.out.println("1. Edit Equipment");
        System.out.println("2. Delete Equipment");
        System.out.print("Enter your choice: ");
        
        int choice = getUserChoice();
        if (choice == 1) {
            // Edit equipment
            System.out.print("New Description (" + equipment.getDescription() + "): ");
            String description = scanner.nextLine();
            if (!description.isEmpty()) equipment.setDescription(description);
            
            System.out.print("New Type (" + equipment.getType() + "): ");
            String type = scanner.nextLine();
            if (!type.isEmpty()) equipment.setType(type);
            
            System.out.print("New Model (" + equipment.getModel() + "): ");
            String model = scanner.nextLine();
            if (!model.isEmpty()) equipment.setModel(model);
            
            System.out.print("New Status (" + equipment.getStatus() + "): ");
            String status = scanner.nextLine();
            if (!status.isEmpty()) equipment.setStatus(status);
            
            System.out.print("New Daily Rate (" + equipment.getDailyRate() + "): ");
            String rateStr = scanner.nextLine();
            if (!rateStr.isEmpty()) {
                try {
                    double rate = Double.parseDouble(rateStr);
                    equipment.setDailyRate(rate);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid rate format.");
                }
            }
            
            System.out.println("Equipment updated successfully!");
        } else if (choice == 2) {
            // Delete equipment
            Database.removeEquipment(equipmentId);
            System.out.println("Equipment deleted successfully!");
        } else {
            System.out.println("Invalid choice.");
        }
    }
    
    private void editDeleteWarehouse() {
        System.out.print("Enter warehouse ID: ");
        int warehouseId = getUserChoice();
        
        Warehouse warehouse = Database.findWarehouseById(warehouseId);
        if (warehouse == null) {
            System.out.println("Warehouse not found.");
            return;
        }
        
        System.out.println("Warehouse found: " + warehouse.getCity());
        System.out.println("1. Edit Warehouse");
        System.out.println("2. Delete Warehouse");
        System.out.print("Enter your choice: ");
        
        int choice = getUserChoice();
        if (choice == 1) {
            // Edit warehouse
            System.out.print("New City (" + warehouse.getCity() + "): ");
            String city = scanner.nextLine();
            if (!city.isEmpty()) warehouse.setCity(city);
            
            System.out.print("New Address (" + warehouse.getAddress() + "): ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) warehouse.setAddress(address);
            
            System.out.print("New Phone (" + warehouse.getPhone() + "): ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) warehouse.setPhone(phone);
            
            System.out.print("New Manager Name (" + warehouse.getManagerName() + "): ");
            String managerName = scanner.nextLine();
            if (!managerName.isEmpty()) warehouse.setManagerName(managerName);
            
            System.out.println("Warehouse updated successfully!");
        } else if (choice == 2) {
            // Delete warehouse
            Database.removeWarehouse(warehouseId);
            System.out.println("Warehouse deleted successfully!");
        } else {
            System.out.println("Invalid choice.");
        }
    }
    
    private void viewRentalHistory() {
        System.out.println("\n=== Rental History ===");
        
        System.out.println("Your rental history:");
        Database.getAllRentals().stream()
            .filter(rental -> rental.getUserId().equals(currentUser.getUserId()))
            .forEach(rental -> {
                Equipment equipment = Database.findEquipmentById(rental.getEquipmentId());
                System.out.println("Rental ID: " + rental.getRentalId());
                System.out.println("Equipment: " + equipment.getDescription());
                System.out.println("Checkout Date: " + rental.getCheckoutDate());
                System.out.println("Due Date: " + rental.getDueDate());
                System.out.println("Return Date: " + (rental.getReturnDate() != null ? rental.getReturnDate() : "Not returned"));
                System.out.println("Status: " + rental.getStatus());
                System.out.println("Rental Fee: $" + rental.getRentalFee());
                System.out.println("-----");
            });
    }
}