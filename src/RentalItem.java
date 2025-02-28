public class RentalItem {
    private Integer rentalItemId;
    private Integer rentalId;
    private Integer equipmentId;
    private Double rate;
    private Double totalCost;
    
    public RentalItem(Integer rentalItemId, Integer rentalId, Integer equipmentId, Double rate, Double totalCost) {
        this.rentalItemId = rentalItemId;
        this.rentalId = rentalId;
        this.equipmentId = equipmentId;
        this.rate = rate;
        this.totalCost = totalCost;
    }
    
    // Getters and setters
    public Integer getRentalItemId() { return rentalItemId; }
    public Integer getRentalId() { return rentalId; }
    public Integer getEquipmentId() { return equipmentId; }
    public Double getRate() { return rate; }
    public Double getTotalCost() { return totalCost; }
}