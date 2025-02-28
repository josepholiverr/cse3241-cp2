public class Rental {
    private Integer rentalId;
    private Integer userId;
    private Integer equipmentId;
    private String checkoutDate;
    private String dueDate;
    private String returnDate;
    private String status;
    private Double rentalFee;
    
    public Rental(Integer rentalId, Integer userId, Integer equipmentId, String checkoutDate, 
                  String dueDate, String returnDate, String status, Double rentalFee) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.equipmentId = equipmentId;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.rentalFee = rentalFee;
    }
    
    // Getters and setters
    public Integer getRentalId() { return rentalId; }
    public Integer getUserId() { return userId; }
    public Integer getEquipmentId() { return equipmentId; }
    public String getCheckoutDate() { return checkoutDate; }
    public String getDueDate() { return dueDate; }
    public String getReturnDate() { return returnDate; }
    public String getStatus() { return status; }
    public Double getRentalFee() { return rentalFee; }
    
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
    public void setStatus(String status) { this.status = status; }
}