package com.blooddonation.service;

import com.blooddonation.entity.Donor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class EligibilityChecker {
    
    public boolean checkEligibility(Donor donor) {
        // Check age requirement (18-65 years)
        if (donor.getAge() < 18 || donor.getAge() > 65) {
            return false;
        }
        
        // Check if donor has donated recently (must be at least 56 days since last donation)
        if (donor.getLastDonationDate() != null) {
            LocalDate lastDonation = donor.getLastDonationDate();
            LocalDate today = LocalDate.now();
            Period period = Period.between(lastDonation, today);
            
            if (period.getDays() < 56) {
                return false;
            }
        }
        
        // Additional eligibility checks can be added here:
        // - Health conditions
        // - Weight requirements
        // - Medical history
        // - Current medications
        
        return true;
    }
}
