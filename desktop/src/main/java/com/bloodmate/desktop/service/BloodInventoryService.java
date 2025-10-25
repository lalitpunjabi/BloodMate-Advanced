package com.bloodmate.desktop.service;

import com.bloodmate.desktop.model.BloodInventory;
import com.bloodmate.desktop.repo.BloodInventoryDao;
import com.bloodmate.desktop.util.DatabaseManager;

import java.sql.Connection;
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
        // Placeholder implementation
        return null;
    }
    
    public boolean addInventory(BloodInventory inventory) {
        return bloodInventoryDao.insert(inventory);
    }
    
    public boolean updateInventory(BloodInventory inventory) {
        return bloodInventoryDao.update(inventory);
    }
    
    public boolean deleteInventory(String id) {
        return bloodInventoryDao.delete(id);
    }
}