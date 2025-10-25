package com.bloodmate.desktop.service;

import com.bloodmate.desktop.model.Recipient;
import com.bloodmate.desktop.repo.RecipientDao;
import com.bloodmate.desktop.util.DatabaseManager;

import java.sql.Connection;
import java.util.List;

/**
 * Service for managing recipient operations
 */
public class RecipientService {
    
    private RecipientDao recipientDao;
    
    public RecipientService() {
        Connection connection = DatabaseManager.getConnection();
        this.recipientDao = new RecipientDao(connection);
    }
    
    public List<Recipient> getAllRecipients() {
        return recipientDao.findAll();
    }
    
    public Recipient getRecipientById(String id) {
        // Find recipient by ID implementation
        return null;
    }
    
    public boolean addRecipient(Recipient recipient) {
        return recipientDao.insert(recipient);
    }
    
    public boolean updateRecipient(Recipient recipient) {
        return recipientDao.update(recipient);
    }
    
    public boolean deleteRecipient(String id) {
        return recipientDao.delete(id);
    }
}