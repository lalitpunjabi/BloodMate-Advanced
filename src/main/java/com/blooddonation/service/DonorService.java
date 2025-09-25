package com.blooddonation.service;

import com.blooddonation.entity.Donor;
import com.blooddonation.entity.Rewards;
import com.blooddonation.repository.DonorRepository;
import com.blooddonation.repository.RewardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DonorService {
    
    @Autowired
    private DonorRepository donorRepository;
    
    @Autowired
    private RewardsRepository rewardsRepository;
    
    @Autowired
    private EligibilityChecker eligibilityChecker;
    
    public Donor registerDonor(Donor donor) {
        // Check if donor already exists
        if (donorRepository.findByEmail(donor.getEmail()).isPresent()) {
            throw new RuntimeException("Donor with this email already exists");
        }
        
        if (donorRepository.findByPhone(donor.getPhone()).isPresent()) {
            throw new RuntimeException("Donor with this phone number already exists");
        }
        
        // Check eligibility
        boolean isEligible = eligibilityChecker.checkEligibility(donor);
        donor.setIsEligible(isEligible);
        
        // Save donor
        Donor savedDonor = donorRepository.save(donor);
        
        // Create rewards record
        Rewards rewards = new Rewards(savedDonor);
        rewardsRepository.save(rewards);
        
        return savedDonor;
    }
    
    public Optional<Donor> getDonorById(Long id) {
        return donorRepository.findById(id);
    }
    
    public Optional<Donor> getDonorByEmail(String email) {
        return donorRepository.findByEmail(email);
    }
    
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }
    
    public List<Donor> getDonorsByBloodGroup(String bloodGroup) {
        return donorRepository.findByBloodGroup(bloodGroup);
    }
    
    public List<Donor> getDonorsByLocation(String city, String state) {
        return donorRepository.findByCityAndState(city, state);
    }
    
    public List<Donor> getEligibleDonorsByBloodGroupAndLocation(String bloodGroup, String city, String state) {
        if (city != null && state != null) {
            return donorRepository.findEligibleDonorsByBloodGroupAndCity(bloodGroup, city);
        } else if (state != null) {
            return donorRepository.findEligibleDonorsByBloodGroupAndState(bloodGroup, state);
        } else {
            return donorRepository.findByBloodGroupAndIsEligibleTrue(bloodGroup);
        }
    }
    
    public Donor updateDonor(Long id, Donor donorDetails) {
        Optional<Donor> optionalDonor = donorRepository.findById(id);
        if (optionalDonor.isPresent()) {
            Donor existingDonor = optionalDonor.get();
            
            // Update fields
            existingDonor.setName(donorDetails.getName());
            existingDonor.setAge(donorDetails.getAge());
            existingDonor.setGender(donorDetails.getGender());
            existingDonor.setPhone(donorDetails.getPhone());
            existingDonor.setAddress(donorDetails.getAddress());
            existingDonor.setCity(donorDetails.getCity());
            existingDonor.setState(donorDetails.getState());
            existingDonor.setBloodGroup(donorDetails.getBloodGroup());
            
            // Recheck eligibility
            boolean isEligible = eligibilityChecker.checkEligibility(existingDonor);
            existingDonor.setIsEligible(isEligible);
            
            return donorRepository.save(existingDonor);
        }
        throw new RuntimeException("Donor not found with id: " + id);
    }
    
    public void deleteDonor(Long id) {
        if (donorRepository.existsById(id)) {
            donorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Donor not found with id: " + id);
        }
    }
    
    public void recordDonation(Long donorId) {
        Optional<Donor> optionalDonor = donorRepository.findById(donorId);
        if (optionalDonor.isPresent()) {
            Donor donor = optionalDonor.get();
            
            // Update last donation date
            donor.setLastDonationDate(LocalDate.now());
            
            // Add reward points based on blood group rarity
            int pointsEarned = calculateDonationPoints(donor.getBloodGroup());
            donor.setRewardPoints(donor.getRewardPoints() + pointsEarned);
            
            // Update rewards record
            Optional<Rewards> optionalRewards = rewardsRepository.findByDonorId(donorId);
            if (optionalRewards.isPresent()) {
                Rewards rewards = optionalRewards.get();
                rewards.addPoints(pointsEarned);
                rewards.recordDonation();
                rewardsRepository.save(rewards);
            }
            
            donorRepository.save(donor);
        }
    }
    
    private int calculateDonationPoints(String bloodGroup) {
        // Base points for donation
        int basePoints = 50;
        
        // Bonus points for rare blood groups
        switch (bloodGroup) {
            case "AB-":
                return basePoints + 100; // Rarest
            case "B-":
                return basePoints + 75;
            case "A-":
                return basePoints + 50;
            case "O-":
                return basePoints + 25;
            case "AB+":
                return basePoints + 20;
            case "B+":
                return basePoints + 15;
            case "A+":
                return basePoints + 10;
            case "O+":
                return basePoints + 5; // Most common
            default:
                return basePoints;
        }
    }
    
    public List<Donor> getTopDonors(int limit) {
        return donorRepository.findTopDonorsByPoints(0).stream()
                .limit(limit)
                .toList();
    }
    
    public List<Donor> getDonorsNeedingDonation() {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        return donorRepository.findByLastDonationDateBefore(threeMonthsAgo);
    }
}
