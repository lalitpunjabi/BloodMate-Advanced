package com.blooddonation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_inventory")
public class BloodInventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Blood group is required")
    @Pattern(regexp = "^(A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-)$", message = "Invalid blood group")
    private String bloodGroup;
    
    @NotNull(message = "Units available is required")
    @Min(value = 0, message = "Units cannot be negative")
    @Max(value = 10000, message = "Units cannot exceed 10000")
    private Integer unitsAvailable;
    
    @NotNull(message = "Units reserved is required")
    @Min(value = 0, message = "Reserved units cannot be negative")
    private Integer unitsReserved = 0;
    
    @NotNull(message = "Units expired is required")
    @Min(value = 0, message = "Expired units cannot be negative")
    private Integer unitsExpired = 0;
    
    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;
    
    @Column(name = "donation_date")
    private LocalDate donationDate;
    
    @Column(name = "source_type")
    @Enumerated(EnumType.STRING)
    private SourceType sourceType = SourceType.DONOR;
    
    @Column(name = "storage_location")
    private String storageLocation;
    
    @Column(name = "temperature")
    private Double temperature;
    
    @Column(name = "notes", length = 500)
    private String notes;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum SourceType {
        DONOR, CAMPAIGN, EXTERNAL
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (donationDate == null) {
            donationDate = LocalDate.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public BloodInventory() {}
    
    public BloodInventory(String bloodGroup, Integer unitsAvailable, LocalDate expiryDate) {
        this.bloodGroup = bloodGroup;
        this.unitsAvailable = unitsAvailable;
        this.expiryDate = expiryDate;
    }
    
    // Business methods
    public Integer getTotalUnits() {
        return unitsAvailable + unitsReserved;
    }
    
    public Integer getAvailableForDonation() {
        return unitsAvailable;
    }
    
    public boolean isLowStock() {
        return unitsAvailable <= 10;
    }
    
    public boolean isExpiringSoon() {
        LocalDate warningDate = LocalDate.now().plusDays(7);
        return expiryDate.isBefore(warningDate);
    }
    
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    
    public Integer getUnitsAvailable() { return unitsAvailable; }
    public void setUnitsAvailable(Integer unitsAvailable) { this.unitsAvailable = unitsAvailable; }
    
    public Integer getUnitsReserved() { return unitsReserved; }
    public void setUnitsReserved(Integer unitsReserved) { this.unitsReserved = unitsReserved; }
    
    public Integer getUnitsExpired() { return unitsExpired; }
    public void setUnitsExpired(Integer unitsExpired) { this.unitsExpired = unitsExpired; }
    
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    
    public LocalDate getDonationDate() { return donationDate; }
    public void setDonationDate(LocalDate donationDate) { this.donationDate = donationDate; }
    
    public SourceType getSourceType() { return sourceType; }
    public void setSourceType(SourceType sourceType) { this.sourceType = sourceType; }
    
    public String getStorageLocation() { return storageLocation; }
    public void setStorageLocation(String storageLocation) { this.storageLocation = storageLocation; }
    
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
