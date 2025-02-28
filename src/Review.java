public class Review {
    private Integer reviewId;
    private Integer userId;
    private Integer equipmentId;
    private Integer rating;
    private String comments;
    private String reviewDate;
    
    public Review(Integer reviewId, Integer userId, Integer equipmentId, Integer rating, 
                 String comments, String reviewDate) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.equipmentId = equipmentId;
        this.rating = rating;
        this.comments = comments;
        this.reviewDate = reviewDate;
    }
    
    // Getters and setters
    public Integer getReviewId() { return reviewId; }
    public Integer getUserId() { return userId; }
    public Integer getEquipmentId() { return equipmentId; }
    public Integer getRating() { return rating; }
    public String getComments() { return comments; }
    public String getReviewDate() { return reviewDate; }
}