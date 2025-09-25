package com.blooddonation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rewards")
public class Rewards {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;
    
    @NotNull(message = "Points are required")
    @Min(value = 0, message = "Points cannot be negative")
    private Integer points;
    
    @Enumerated(EnumType.STRING)
    private RewardTier tier;
    
    @Column(name = "points_earned")
    private Integer pointsEarned = 0;
    
    @Column(name = "points_spent")
    private Integer pointsSpent = 0;
    
    @Column(name = "total_donations")
    private Integer totalDonations = 0;
    
    @Column(name = "last_donation_date")
    private LocalDateTime lastDonationDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum RewardTier {
        BRONZE(0, "Bronze", 0),
        SILVER(100, "Silver", 5),
        GOLD(500, "Gold", 10),
        PLATINUM(1000, "Platinum", 15),
        EMERGENCY_HERO(2000, "Emergency Hero", 25);
        
        private final int minPoints;
        private final String displayName;
        private final int bonusMultiplier;
        
        RewardTier(int minPoints, String displayName, int bonusMultiplier) {
            this.minPoints = minPoints;
            this.displayName = displayName;
            this.bonusMultiplier = bonusMultiplier;
        }
        
        public int getMinPoints() { return minPoints; }
        public String getDisplayName() { return displayName; }
        public int getBonusMultiplier() { return bonusMultiplier; }
        
        public static RewardTier getTierForPoints(int points) {
            if (points >= EMERGENCY_HERO.minPoints) return EMERGENCY_HERO;
            if (points >= PLATINUM.minPoints) return PLATINUM;
            if (points >= GOLD.minPoints) return GOLD;
            if (points >= SILVER.minPoints) return SILVER;
            return BRONZE;
        }
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        updateTier();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        updateTier();
    }
    
    // Business methods
    public void addPoints(int newPoints) {
        this.points += newPoints;
        this.pointsEarned += newPoints;
        updateTier();
    }
    
    public boolean spendPoints(int pointsToSpend) {
        if (this.points >= pointsToSpend) {
            this.points -= pointsToSpend;
            this.pointsSpent += pointsToSpend;
            updateTier();
            return true;
        }
        return false;
    }
    
    public void recordDonation() {
        this.totalDonations++;
        this.lastDonationDate = LocalDateTime.now();
    }
    
    private void updateTier() {
        this.tier = RewardTier.getTierForPoints(this.points);
    }
    
    public int getBonusMultiplier() {
        return tier != null ? tier.getBonusMultiplier() : 0;
    }
    
    public String getTierDisplayName() {
        return tier != null ? tier.getDisplayName() : "Unknown";
    }
    
    // Constructors
    public Rewards() {}
    
    public Rewards(Donor donor) {
        this.donor = donor;
        this.points = 0;
        this.tier = RewardTier.BRONZE;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Donor getDonor() { return donor; }
    public void setDonor(Donor donor) { this.donor = donor; }
    
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    
    public RewardTier getTier() { return tier; }
    public void setTier(RewardTier tier) { this.tier = tier; }
    
    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }
    
    public Integer getPointsSpent() { return pointsSpent; }
    public void setPointsSpent(Integer pointsSpent) { this.pointsSpent = pointsSpent; }
    
    public Integer getTotalDonations() { return totalDonations; }
    public void setTotalDonations(Integer totalDonations) { this.totalDonations = totalDonations; }
    
    public LocalDateTime getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(LocalDateTime lastDonationDate) { this.lastDonationDate = lastDonationDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
