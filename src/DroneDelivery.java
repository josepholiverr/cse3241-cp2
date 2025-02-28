public class DroneDelivery {
    private Integer deliveryId;
    private Integer rentalId;
    private Integer droneId;
    private String type;
    private String start;
    private String end;
    private String status;
    
    public DroneDelivery(Integer deliveryId, Integer rentalId, Integer droneId, String type, 
                          String start, String end, String status) {
        this.deliveryId = deliveryId;
        this.rentalId = rentalId;
        this.droneId = droneId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.status = status;
    }
    
    // Getters and setters
    public Integer getDeliveryId() { return deliveryId; }
    public Integer getRentalId() { return rentalId; }
    public Integer getDroneId() { return droneId; }
    public String getType() { return type; }
    public String getStart() { return start; }
    public String getEnd() { return end; }
    public String getStatus() { return status; }
    
    public void setEnd(String end) { this.end = end; }
    public void setStatus(String status) { this.status = status; }
}