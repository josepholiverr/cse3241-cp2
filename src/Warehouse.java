public class Warehouse {
    private Integer warehouseId;
    private String city;
    private String address;
    private String phone;
    private String managerName;
    private Integer storageCapacity;
    private Integer droneCapacity;
    private String status;
    
    public Warehouse(Integer warehouseId, String city, String address, String phone, 
                     String managerName, Integer storageCapacity, Integer droneCapacity, String status) {
        this.warehouseId = warehouseId;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.managerName = managerName;
        this.storageCapacity = storageCapacity;
        this.droneCapacity = droneCapacity;
        this.status = status;
    }
    
    // Getters and setters
    public Integer getWarehouseId() { return warehouseId; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getManagerName() { return managerName; }
    public Integer getStorageCapacity() { return storageCapacity; }
    public Integer getDroneCapacity() { return droneCapacity; }
    public String getStatus() { return status; }
    
    public void setCity(String city) { this.city = city; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setManagerName(String managerName) { this.managerName = managerName; }
    public void setStatus(String status) { this.status = status; }
}