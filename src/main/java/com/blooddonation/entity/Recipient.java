package com.blooddonation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipients")
public class Recipient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Hospital name is required")
    private String hospital;
    
    @NotBlank(message = "Contact person is required")
    private String contactPerson;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number should be valid")
    private String phone;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "State is required")
    private String state;
    
    @NotBlank(message = "Required blood group is required")
    @Pattern(regexp = "^(A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-)$", message = "Invalid blood group")
    private String requiredBloodGroup;
    
    @NotNull(message = "Units required is mandatory")
    @Min(value = 1, message = "At least 1 unit is required")
    @Max(value = 100, message = "Cannot request more than 100 units")
    private Integer unitsRequired;
    
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus = RequestStatus.PENDING;
    
    @Enumerated(EnumType.STRING)
    private RequestPriority priority = RequestPriority.NORMAL;
    
    @Column(name = "request_date")
    private LocalDateTime requestDate;
    
    @Column(name = "required_by_date")
    private LocalDateTime requiredByDate;
    
    @Column(name = "notes", length = 1000)
    private String notes;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum RequestStatus {
        PENDING, APPROVED, FULFILLED, CANCELLED, EXPIRED
    }
    
    public enum RequestPriority {
        LOW, NORMAL, HIGH, EMERGENCY
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (requestDate == null) {
            requestDate = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Recipient() {}
    
    public Recipient(String name, String hospital, String contactPerson, String phone, 
                     String email, String address, String city, String state, 
                     String requiredBloodGroup, Integer unitsRequired) {
        this.name = name;
        this.hospital = hospital;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.requiredBloodGroup = requiredBloodGroup;
        this.unitsRequired = unitsRequired;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getHospital() { return hospital; }
    public void setHospital(String hospital) { this.hospital = hospital; }
    
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getRequiredBloodGroup() { return requiredBloodGroup; }
    public void setRequiredBloodGroup(String requiredBloodGroup) { this.requiredBloodGroup = requiredBloodGroup; }
    
    public Integer getUnitsRequired() { return unitsRequired; }
    public void setUnitsRequired(Integer unitsRequired) { this.unitsRequired = unitsRequired; }
    
    public RequestStatus getRequestStatus() { return requestStatus; }
    public void setRequestStatus(RequestStatus requestStatus) { this.requestStatus = requestStatus; }
    
    public RequestPriority getPriority() { return priority; }
    public void setPriority(RequestPriority priority) { this.priority = priority; }
    
    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
    
    public LocalDateTime getRequiredByDate() { return requiredByDate; }
    public void setRequiredByDate(LocalDateTime requiredByDate) { this.requiredByDate = requiredByDate; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
