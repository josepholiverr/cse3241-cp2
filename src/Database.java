import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class Database {
    private static List<User> users = new ArrayList<>();
    private static List<Equipment> equipment = new ArrayList<>();
    private static List<Rental> rentals = new ArrayList<>();
    private static List<RentalItem> rentalItems = new ArrayList<>();
    private static List<Drone> drones = new ArrayList<>();
    private static List<DroneDelivery> droneDeliveries = new ArrayList<>();
    private static List<Warehouse> warehouses = new ArrayList<>();
    private static List<Review> reviews = new ArrayList<>();
    
    // ID counters
    private static int nextUserId = 1;
    private static int nextEquipmentId = 1;
    private static int nextRentalId = 1;
    private static int nextRentalItemId = 1;
    private static int nextDroneId = 1;
    private static int nextDeliveryId = 1;
    private static int nextWarehouseId = 1;
    private static int nextReviewId = 1;
    
    public static void initializeData() {
        // Initialize warehouses
        warehouses.add(new Warehouse(nextWarehouseId++, "New York", "123 Broadway", "212-555-1234", 
                                    "John Smith", 1000, 20, "Active"));
        warehouses.add(new Warehouse(nextWarehouseId++, "Columbus", "174 N High St", "614-111-2222",
                                    "Jane Doe", 1500, 30, "Active"));
        warehouses.add(new Warehouse(nextWarehouseId++, "Chicago", "789 Michigan Ave", "312-555-9012", 
                                    "John Johnson", 1200, 25, "Active"));
        
        // Initialize users
        users.add(new User(nextUserId++, "Joseph", "Oliver", "123 N High St", "513-123-4567", "joseph@email.com"));
        users.get(0).setWarehouseId(1);
        users.add(new User(nextUserId++, "Allison", "Rudie", "123 N High St", "614-234-5678", "allison@email.com"));
        users.get(1).setWarehouseId(2);
        users.add(new User(nextUserId++, "Jia", "Hui Tang", "123 N High St", "614-345-6789", "jia@email.com"));
        users.get(2).setWarehouseId(2);
        users.add(new User(nextUserId++, "Wil", "Borchers", "123 N High St", "614-456-7890", "wil@email.com"));
        users.get(3).setWarehouseId(3);
        
        // Initialize equipment
        equipment.add(new Equipment(nextEquipmentId++, "Excavator", "Heavy Machinery", "CAT 320", 2020, 
                                   "EX12345", "Available", "Warehouse", 20000.0, 1, 
                                   LocalDate.now().plusYears(3).toString(), "8x10x12", "Caterpillar", 250.0));
                                   
        equipment.add(new Equipment(nextEquipmentId++, "Bulldozer", "Heavy Machinery", "CAT D6", 2019, 
                                   "BD67890", "Available", "Warehouse", 25000.0, 1, 
                                   LocalDate.now().plusYears(2).toString(), "10x15x8", "Caterpillar", 300.0));
                                   
        equipment.add(new Equipment(nextEquipmentId++, "Backhoe Loader", "Heavy Machinery", "JCB 3CX", 2021, 
                                   "BL54321", "Available", "Warehouse", 8000.0, 2, 
                                   LocalDate.now().plusYears(4).toString(), "7x12x9", "JCB", 200.0));
                                   
        equipment.add(new Equipment(nextEquipmentId++, "Forklift", "Material Handling", "Toyota 8FGU15", 2022, 
                                   "FL09876", "Available", "Warehouse", 3000.0, 2, 
                                   LocalDate.now().plusYears(5).toString(), "4x7x6", "Toyota", 150.0));
                                   
        equipment.add(new Equipment(nextEquipmentId++, "Concrete Mixer", "Construction", "McNeilus", 2020, 
                                   "CM24680", "Available", "Warehouse", 15000.0, 3, 
                                   LocalDate.now().plusYears(3).toString(), "8x8x12", "McNeilus", 180.0));
                                   
        equipment.add(new Equipment(nextEquipmentId++, "Crane", "Heavy Machinery", "Liebherr LTM", 2018, 
                                   "CR13579", "Available", "Warehouse", 50000.0, 3, 
                                   LocalDate.now().plusYears(2).toString(), "15x20x40", "Liebherr", 500.0));
        
        // Initialize drones
        drones.add(new Drone(nextDroneId++, 1, "Delivery-1", "DJI Matrice 300 RTK", "DM12345", 
                            "Available", "Warehouse", "DJI", 2022, 15.0, 60.0, 
                            LocalDate.now().plusYears(2).toString(), 50.0));
                            
        drones.add(new Drone(nextDroneId++, 1, "Delivery-2", "DJI Mavic 3", "DM67890", 
                            "Available", "Warehouse", "DJI", 2022, 5.0, 70.0, 
                            LocalDate.now().plusYears(2).toString(), 45.0));
                            
        drones.add(new Drone(nextDroneId++, 2, "Delivery-3", "Yuneec H520", "YH54321", 
                            "Available", "Warehouse", "Yuneec", 2021, 10.0, 65.0, 
                            LocalDate.now().plusYears(3).toString(), 40.0));
                            
        drones.add(new Drone(nextDroneId++, 2, "Delivery-4", "Autel EVO II", "AE09876", 
                            "Available", "Warehouse", "Autel", 2021, 8.0, 72.0, 
                            LocalDate.now().plusYears(3).toString(), 42.0));
                            
        drones.add(new Drone(nextDroneId++, 3, "Delivery-5", "Skydio 2", "SD24680", 
                            "Available", "Warehouse", "Skydio", 2020, 6.0, 58.0, 
                            LocalDate.now().plusYears(2).toString(), 38.0));
                            
        drones.add(new Drone(nextDroneId++, 3, "Delivery-6", "DJI Phantom 4 Pro", "DP13579", 
                            "Available", "Warehouse", "DJI", 2020, 7.0, 55.0, 
                            LocalDate.now().plusYears(1).toString(), 35.0));
        
        // Initialize some sample rentals
        Rental rental1 = new Rental(nextRentalId++, 1, 1, LocalDate.now().minusDays(10).toString(),
                              LocalDate.now().plusDays(5).toString(), null, "Active", 3750.0);
        rentals.add(rental1);
        equipment.get(0).setStatus("Rented");
        
        RentalItem rentalItem1 = new RentalItem(nextRentalItemId++, rental1.getRentalId(), 1, 250.0, 3750.0);
        rentalItems.add(rentalItem1);
        
        Rental rental2 = new Rental(nextRentalId++, 2, 3, LocalDate.now().minusDays(5).toString(),
                              LocalDate.now().plusDays(10).toString(), null, "Active", 3000.0);
        rentals.add(rental2);
        equipment.get(2).setStatus("Rented");
        
        RentalItem rentalItem2 = new RentalItem(nextRentalItemId++, rental2.getRentalId(), 3, 200.0, 3000.0);
        rentalItems.add(rentalItem2);
        
        // Initialize some completed rentals
        Rental rental3 = new Rental(nextRentalId++, 3, 5, LocalDate.now().minusDays(30).toString(),
                              LocalDate.now().minusDays(20).toString(), LocalDate.now().minusDays(20).toString(), 
                              "Returned", 1800.0);
        rentals.add(rental3);
        
        RentalItem rentalItem3 = new RentalItem(nextRentalItemId++, rental3.getRentalId(), 5, 180.0, 1800.0);
        rentalItems.add(rentalItem3);
        
        // Initialize some reviews
        reviews.add(new Review(nextReviewId++, 3, 5, 4, "Great equipment, worked perfectly for our job.", 
                             LocalDate.now().minusDays(19).toString()));
    }
    
    // Getters for next ID values
    public static int getNextUserId() { return nextUserId++; }
    public static int getNextEquipmentId() { return nextEquipmentId++; }
    public static int getNextRentalId() { return nextRentalId++; }
    public static int getNextRentalItemId() { return nextRentalItemId++; }
    public static int getNextDroneId() { return nextDroneId++; }
    public static int getNextDeliveryId() { return nextDeliveryId++; }
    public static int getNextWarehouseId() { return nextWarehouseId++; }
    public static int getNextReviewId() { return nextReviewId++; }
    
    // User methods
    public static void addUser(User user) {
        users.add(user);
    }
    
    public static User findUserById(Integer userId) {
        return users.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findFirst()
            .orElse(null);
    }
    
    public static User findUserByEmail(String email) {
        return users.stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    }
    
    public static void removeUser(Integer userId) {
        users.removeIf(user -> user.getUserId().equals(userId));
    }
    
    public static List<User> getUsers() {
        return users;
    }
    
    // Equipment methods
    public static void addEquipment(Equipment equipment) {
        Database.equipment.add(equipment);
    }
    
    public static Equipment findEquipmentById(Integer equipmentId) {
        return equipment.stream()
            .filter(equip -> equip.getEquipmentId().equals(equipmentId))
            .findFirst()
            .orElse(null);
    }
    
    public static void removeEquipment(Integer equipmentId) {
        equipment.removeIf(equip -> equip.getEquipmentId().equals(equipmentId));
    }
    
    public static List<Equipment> getAllEquipment() {
        return equipment;
    }
    
    public static List<Equipment> getAvailableEquipment(Integer warehouseId) {
        return equipment.stream()
            .filter(equip -> equip.getWarehouseId().equals(warehouseId) && 
                   equip.getStatus().equals("Available"))
            .collect(Collectors.toList());
    }
    
    // Rental methods
    public static void addRental(Rental rental) {
        rentals.add(rental);
    }
    
    public static Rental findRentalById(Integer rentalId) {
        return rentals.stream()
            .filter(rental -> rental.getRentalId().equals(rentalId))
            .findFirst()
            .orElse(null);
    }
    
    public static List<Rental> getAllRentals() {
        return rentals;
    }
    
    public static List<Rental> getActiveRentals(Integer userId) {
        return rentals.stream()
            .filter(rental -> rental.getUserId().equals(userId) && 
                  (rental.getStatus().equals("Active") || 
                   rental.getStatus().equals("Pending") || 
                   rental.getStatus().equals("Ready for Pickup")))
            .collect(Collectors.toList());
    }
    
    // RentalItem methods
    public static void addRentalItem(RentalItem rentalItem) {
        rentalItems.add(rentalItem);
    }
    
    public static List<RentalItem> findRentalItemsByRentalId(Integer rentalId) {
        return rentalItems.stream()
            .filter(item -> item.getRentalId().equals(rentalId))
            .collect(Collectors.toList());
    }
    
    // Drone methods
    public static Drone findDroneById(Integer droneId) {
        return drones.stream()
            .filter(drone -> drone.getDroneId().equals(droneId))
            .findFirst()
            .orElse(null);
    }
    
    public static Drone getAvailableDrone(Integer warehouseId) {
        return drones.stream()
            .filter(drone -> drone.getWarehouseId().equals(warehouseId) && 
                   drone.getStatus().equals("Available"))
            .findFirst()
            .orElse(null);
    }
    
    // Delivery methods
    public static void addDroneDelivery(DroneDelivery delivery) {
        droneDeliveries.add(delivery);
    }
    
    public static DroneDelivery findDeliveryByRentalId(Integer rentalId) {
        return droneDeliveries.stream()
            .filter(delivery -> delivery.getRentalId().equals(rentalId))
            .findFirst()
            .orElse(null);
    }
    
    // Warehouse methods
    public static void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }
    
    public static Warehouse findWarehouseById(Integer warehouseId) {
        return warehouses.stream()
            .filter(warehouse -> warehouse.getWarehouseId().equals(warehouseId))
            .findFirst()
            .orElse(null);
    }
    
    public static void removeWarehouse(Integer warehouseId) {
        warehouses.removeIf(warehouse -> warehouse.getWarehouseId().equals(warehouseId));
    }
    
    public static List<Warehouse> getWarehouses() {
        return warehouses;
    }
    
    // Review methods
    public static void addReview(Review review) {
        reviews.add(review);
    }
    
    public static List<Review> getReviewsByEquipmentId(Integer equipmentId) {
        return reviews.stream()
            .filter(review -> review.getEquipmentId().equals(equipmentId))
            .collect(Collectors.toList());
    }
    
    public static List<Review> getReviewsByUserId(Integer userId) {
        return reviews.stream()
            .filter(review -> review.getUserId().equals(userId))
            .collect(Collectors.toList());
    }
}