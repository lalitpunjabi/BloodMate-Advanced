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
        // Placeholder implementation
        return null;
    }
    
    public boolean addDonor(Donor donor) {
        return donorDao.insert(donor);
    }
    
    public boolean updateDonor(Donor donor) {
        return donorDao.insert(donor);
    }
    
    public boolean deleteDonor(String id) {
        return true; // Placeholder implementation
    }
}
