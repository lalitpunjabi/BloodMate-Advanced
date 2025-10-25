package com.bloodmate.desktop.service;

import com.bloodmate.desktop.model.BloodInventory;
import com.bloodmate.desktop.repo.BloodInventoryDao;
import com.bloodmate.desktop.util.DatabaseManager;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

/**
 * Service for managing blood inventory operations
 */
public class BloodInventoryService {
    
    private BloodInventoryDao bloodInventoryDao;
    
    public BloodInventoryService() {
        Connection connection = DatabaseManager.getConnection();
        this.bloodInventoryDao = new BloodInventoryDao(connection);
    }
    
    public List<BloodInventory> getAllInventory() {
        return bloodInventoryDao.findAll();
    }
    
    public BloodInventory getInventoryById(String id) {
        // This would require implementing findById in BloodInventoryDao
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Inventory ID is required");
        }
        return null; // Placeholder implementation
    }
    
    public boolean addInventory(BloodInventory inventory) {
        // Validate required fields
        if (inventory.getBloodGroup() == null || inventory.getBloodGroup().isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        if (inventory.getDonationDate() == null) {
            inventory.setDonationDate(LocalDate.now());
        }
        if (inventory.getExpiryDate() == null) {
            // Default to 42 days from donation date
            inventory.setExpiryDate(inventory.getDonationDate().plusDays(42));
        }
        if (inventory.getLocation() == null || inventory.getLocation().isEmpty()) {
            inventory.setLocation("Storage-A");
        }
        
        return bloodInventoryDao.insert(inventory);
    }
    
    public boolean updateInventory(BloodInventory inventory) {
        // Validate required fields
        if (inventory.getId() == null || inventory.getId().isEmpty()) {
            throw new IllegalArgumentException("Inventory ID is required");
        }
        if (inventory.getBloodGroup() == null || inventory.getBloodGroup().isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        
        return bloodInventoryDao.update(inventory);
    }
    
    public boolean deleteInventory(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Inventory ID is required");
        }
        return bloodInventoryDao.delete(id);
    }
    
    public List<BloodInventory> getInventoryByBloodGroup(String bloodGroup) {
        if (bloodGroup == null || bloodGroup.isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        return bloodInventoryDao.findByBloodGroup(bloodGroup);
    }
    
    public List<BloodInventory> getExpiringSoon(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be greater than 0");
        }
        return bloodInventoryDao.findExpiringSoon(days);
    }
    
    public List<BloodInventory> getInventoryByLocation(String location) {
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Location is required");
        }
        return bloodInventoryDao.findByLocation(location);
    }
    
    public int getAvailableUnitsCount(String bloodGroup) {
        if (bloodGroup == null || bloodGroup.isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        return bloodInventoryDao.getAvailableUnitsCount(bloodGroup);
    }
    
    public boolean reserveBloodUnit(String id, String reservedFor) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Inventory ID is required");
        }
        if (reservedFor == null || reservedFor.isEmpty()) {
            throw new IllegalArgumentException("Reserved for is required");
        }
        return bloodInventoryDao.reserveBloodUnit(id, reservedFor);
    }
}