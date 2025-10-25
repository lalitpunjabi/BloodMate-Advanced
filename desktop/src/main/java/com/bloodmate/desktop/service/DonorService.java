package com.bloodmate.desktop.service;

import com.bloodmate.desktop.model.Donor;
import com.bloodmate.desktop.repo.DonorDao;
import com.bloodmate.desktop.util.DatabaseManager;

import java.sql.Connection;
import java.util.List;

/**
 * Service for managing donor operations.
 */
public class DonorService {

    private final DonorDao donorDao;

    // Default constructor (production)
    public DonorService() {
        Connection connection = DatabaseManager.getConnection();
        this.donorDao = new DonorDao(connection);
    }

    // Overloaded constructor (useful for testing/mocking)
    public DonorService(DonorDao donorDao) {
        this.donorDao = donorDao;
    }

    public List<Donor> getAllDonors() {
        return donorDao.findAll();
    }
    
    public Donor getDonorById(String id) {
        return donorDao.findById(id);
    }
    
    public boolean addDonor(Donor donor) {
        // Validate required fields
        if (donor.getName() == null || donor.getName().isEmpty()) {
            throw new IllegalArgumentException("Donor name is required");
        }
        if (donor.getBloodGroup() == null || donor.getBloodGroup().isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        
        return donorDao.insert(donor);
    }
    
    public boolean updateDonor(Donor donor) {
        // Validate required fields
        if (donor.getId() == null || donor.getId().isEmpty()) {
            throw new IllegalArgumentException("Donor ID is required");
        }
        if (donor.getName() == null || donor.getName().isEmpty()) {
            throw new IllegalArgumentException("Donor name is required");
        }
        if (donor.getBloodGroup() == null || donor.getBloodGroup().isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        
        return donorDao.update(donor);
    }
    
    public boolean deleteDonor(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Donor ID is required");
        }
        return donorDao.delete(id);
    }
    
    public List<Donor> searchDonors(String query, String bloodGroup) {
        return donorDao.search(query, bloodGroup);
    }
}
