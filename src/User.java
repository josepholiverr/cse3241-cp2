import static java.sql.Types.NULL;

public class User {
    private Integer userId;
    private String firstName;
    private String mInit;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String startDate;
    private Integer warehouseId;
    private Integer warehouse_dist;
    private String status;
    
    public User(Integer userId, String firstName, String mInit, String lastName, String address, String phone, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.mInit = mInit;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.warehouse_dist = NULL; // deal with this later
        this.startDate = java.time.LocalDate.now().toString();
        this.status = "Active";
    }
    
    // Getters and setters
    public Integer getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getMInit() { return mInit; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getStartDate() { return startDate; }
    public Integer getWarehouseId() { return warehouseId; }
    public String getStatus() { return status; }
    
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setmInit(String mInit) { this.mInit = mInit;}
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setWarehouseId(Integer warehouseId) { this.warehouseId = warehouseId; }
    public void setStatus(String status) { this.status = status; }
}