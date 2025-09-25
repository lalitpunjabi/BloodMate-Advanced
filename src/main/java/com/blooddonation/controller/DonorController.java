package com.blooddonation.controller;

import com.blooddonation.entity.Donor;
import com.blooddonation.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/donors")
@CrossOrigin(origins = "*")
public class DonorController {
    
    @Autowired
    private DonorService donorService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerDonor(@Valid @RequestBody Donor donor) {
        try {
            Donor registeredDonor = donorService.registerDonor(donor);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredDonor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Long id) {
        Optional<Donor> donor = donorService.getDonorById(id);
        return donor.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<Donor>> getAllDonors() {
        List<Donor> donors = donorService.getAllDonors();
        return ResponseEntity.ok(donors);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Donor>> searchDonors(
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state) {
        
        List<Donor> donors;
        if (bloodGroup != null && (city != null || state != null)) {
            donors = donorService.getEligibleDonorsByBloodGroupAndLocation(bloodGroup, city, state);
        } else if (bloodGroup != null) {
            donors = donorService.getDonorsByBloodGroup(bloodGroup);
        } else if (city != null && state != null) {
            donors = donorService.getDonorsByLocation(city, state);
        } else {
            donors = donorService.getAllDonors();
        }
        
        return ResponseEntity.ok(donors);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDonor(@PathVariable Long id, @Valid @RequestBody Donor donorDetails) {
        try {
            Donor updatedDonor = donorService.updateDonor(id, donorDetails);
            return ResponseEntity.ok(updatedDonor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Update failed: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDonor(@PathVariable Long id) {
        try {
            donorService.deleteDonor(id);
            return ResponseEntity.ok().body("Donor deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Deletion failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/{id}/donate")
    public ResponseEntity<?> recordDonation(@PathVariable Long id) {
        try {
            donorService.recordDonation(id);
            return ResponseEntity.ok().body("Donation recorded successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to record donation: " + e.getMessage());
        }
    }
    
    @GetMapping("/leaderboard")
    public ResponseEntity<List<Donor>> getTopDonors(@RequestParam(defaultValue = "10") int limit) {
        List<Donor> topDonors = donorService.getTopDonors(limit);
        return ResponseEntity.ok(topDonors);
    }
    
    @GetMapping("/needing-donation")
    public ResponseEntity<List<Donor>> getDonorsNeedingDonation() {
        List<Donor> donors = donorService.getDonorsNeedingDonation();
        return ResponseEntity.ok(donors);
    }
}
