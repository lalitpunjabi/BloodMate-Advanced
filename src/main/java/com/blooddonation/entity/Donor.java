package com.blooddonation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "donors")
public class Donor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Donor must be at least 18 years old")
    @Max(value = 65, message = "Donor cannot be older than 65 years")
    private Integer age;
    
    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number should be valid")
    private String phone;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "State is required")
    private String state;
    
    @NotBlank(message = "Blood group is required")
    @Pattern(regexp = "^(A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-)$", message = "Invalid blood group")
    private String bloodGroup;
    
    @Column(name = "last_donation_date")
    private LocalDate lastDonationDate;
    
    @Column(name = "reward_points")
    private Integer rewardPoints = 0;
    
    @Column(name = "is_eligible")
    private Boolean isEligible = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Donor() {}
    
    public Donor(String name, Integer age, String gender, String email, String phone, 
                 String address, String city, String state, String bloodGroup) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.bloodGroup = bloodGroup;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    
    public LocalDate getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }
    
    public Integer getRewardPoints() { return rewardPoints; }
    public void setRewardPoints(Integer rewardPoints) { this.rewardPoints = rewardPoints; }
    
    public Boolean getIsEligible() { return isEligible; }
    public void setIsEligible(Boolean isEligible) { this.isEligible = isEligible; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
