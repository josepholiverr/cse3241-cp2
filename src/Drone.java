public class Drone {
    private Integer droneId;
    private Integer warehouseId;
    private String name;
    private String model;
    private String serialNum;
    private String status;
    private String location;
    private String manufacturer;
    private Integer year;
    private Double maxWeight;
    private Double maxSpeed;
    private String warrantyExp;
    private Double distanceAutonomy;
    
    public Drone(Integer droneId, Integer warehouseId, String name, String model, String serialNum, 
                 String status, String location, String manufacturer, Integer year, Double maxWeight, 
                 Double maxSpeed, String warrantyExp, Double distanceAutonomy) {
        this.droneId = droneId;
        this.warehouseId = warehouseId;
        this.name = name;
        this.model = model;
        this.serialNum = serialNum;
        this.status = status;
        this.location = location;
        this.manufacturer = manufacturer;
        this.year = year;
        this.maxWeight = maxWeight;
        this.maxSpeed = maxSpeed;
        this.warrantyExp = warrantyExp;
        this.distanceAutonomy = distanceAutonomy;
    }
    
    // Getters and setters
    public Integer getDroneId() { return droneId; }
    public Integer getWarehouseId() { return warehouseId; }
    public String getName() { return name; }
    public String getModel() { return model; }
    public String getSerialNum() { return serialNum; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
}