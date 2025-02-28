public class Equipment {
    private Integer equipmentId;
    private String description;
    private String type;
    private String model;
    private Integer year;
    private String serialNum;
    private String status;
    private String location;
    private Double weight;
    private Integer warehouseId;
    private String warrantyExp;
    private String dimensions;
    private String manufacturer;
    private Double dailyRate;
    
    public Equipment(Integer equipmentId, String description, String type, String model, Integer year, 
                     String serialNum, String status, String location, Double weight, Integer warehouseId, 
                     String warrantyExp, String dimensions, String manufacturer, Double dailyRate) {
        this.equipmentId = equipmentId;
        this.description = description;
        this.type = type;
        this.model = model;
        this.year = year;
        this.serialNum = serialNum;
        this.status = status;
        this.location = location;
        this.weight = weight;
        this.warehouseId = warehouseId;
        this.warrantyExp = warrantyExp;
        this.dimensions = dimensions;
        this.manufacturer = manufacturer;
        this.dailyRate = dailyRate;
    }
    
    // Getters and setters
    public Integer getEquipmentId() { return equipmentId; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getModel() { return model; }
    public Integer getYear() { return year; }
    public String getSerialNum() { return serialNum; }
    public String getStatus() { return status; }
    public String getLocation() { return location; }
    public Double getWeight() { return weight; }
    public Integer getWarehouseId() { return warehouseId; }
    public String getWarrantyExp() { return warrantyExp; }
    public String getDimensions() { return dimensions; }
    public String getManufacturer() { return manufacturer; }
    public Double getDailyRate() { return dailyRate; }
    
    public void setDescription(String description) { this.description = description; }
    public void setType(String type) { this.type = type; }
    public void setModel(String model) { this.model = model; }
    public void setStatus(String status) { this.status = status; }
    public void setLocation(String location) { this.location = location; }
    public void setWarehouseId(Integer warehouseId) { this.warehouseId = warehouseId; }
    public void setDailyRate(Double dailyRate) { this.dailyRate = dailyRate; }
}