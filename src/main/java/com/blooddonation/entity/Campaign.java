package com.blooddonation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "campaigns")
public class Campaign {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Campaign title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;
    
    @NotBlank(message = "Campaign description is required")
    @Size(min = 20, max = 1000, message = "Description must be between 20 and 1000 characters")
    private String description;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "State is required")
    private String state;
    
    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;
    
    @NotBlank(message = "Organizer name is required")
    private String organizer;
    
    @NotBlank(message = "Organizer contact is required")
    private String organizerContact;
    
    @NotBlank(message = "Organizer email is required")
    @Email(message = "Organizer email should be valid")
    private String organizerEmail;
    
    @Column(name = "target_donors")
    @Min(value = 1, message = "Target donors must be at least 1")
    private Integer targetDonors;
    
    @Column(name = "current_donors")
    private Integer currentDonors = 0;
    
    @Column(name = "target_blood_groups")
    private String targetBloodGroups;
    
    @Enumerated(EnumType.STRING)
    private CampaignStatus status = CampaignStatus.UPCOMING;
    
    @Column(name = "campaign_type")
    @Enumerated(EnumType.STRING)
    private CampaignType campaignType = CampaignType.GENERAL;
    
    @Column(name = "special_requirements", length = 500)
    private String specialRequirements;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum CampaignStatus {
        UPCOMING, ACTIVE, COMPLETED, CANCELLED
    }
    
    public enum CampaignType {
        GENERAL, EMERGENCY, CORPORATE, SCHOOL, COMMUNITY
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Business methods
    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return status == CampaignStatus.ACTIVE && 
               !today.isBefore(startDate) && 
               !today.isAfter(endDate);
    }
    
    public boolean isUpcoming() {
        LocalDate today = LocalDate.now();
        return status == CampaignStatus.UPCOMING && 
               today.isBefore(startDate);
    }
    
    public boolean isCompleted() {
        LocalDate today = LocalDate.now();
        return status == CampaignStatus.COMPLETED || 
               today.isAfter(endDate);
    }
    
    public double getCompletionPercentage() {
        if (targetDonors == null || targetDonors == 0) return 0.0;
        return Math.min(100.0, (currentDonors * 100.0) / targetDonors);
    }
    
    // Constructors
    public Campaign() {}
    
    public Campaign(String title, String description, String location, String city, 
                   String state, LocalDate startDate, LocalDate endDate, 
                   String organizer, String organizerContact, String organizerEmail, 
                   Integer targetDonors) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.city = city;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
        this.organizerContact = organizerContact;
        this.organizerEmail = organizerEmail;
        this.targetDonors = targetDonors;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }
    
    public String getOrganizerContact() { return organizerContact; }
    public void setOrganizerContact(String organizerContact) { this.organizerContact = organizerContact; }
    
    public String getOrganizerEmail() { return organizerEmail; }
    public void setOrganizerEmail(String organizerEmail) { this.organizerEmail = organizerEmail; }
    
    public Integer getTargetDonors() { return targetDonors; }
    public void setTargetDonors(Integer targetDonors) { this.targetDonors = targetDonors; }
    
    public Integer getCurrentDonors() { return currentDonors; }
    public void setCurrentDonors(Integer currentDonors) { this.currentDonors = currentDonors; }
    
    public String getTargetBloodGroups() { return targetBloodGroups; }
    public void setTargetBloodGroups(String targetBloodGroups) { this.targetBloodGroups = targetBloodGroups; }
    
    public CampaignStatus getStatus() { return status; }
    public void setStatus(CampaignStatus status) { this.status = status; }
    
    public CampaignType getCampaignType() { return campaignType; }
    public void setCampaignType(CampaignType campaignType) { this.campaignType = campaignType; }
    
    public String getSpecialRequirements() { return specialRequirements; }
    public void setSpecialRequirements(String specialRequirements) { this.specialRequirements = specialRequirements; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
