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
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Recipient ID is required");
        }
        // This would require implementing findById in RecipientDao
        return null;
    }
    
    public boolean addRecipient(Recipient recipient) {
        // Validate required fields
        if (recipient.getPatientName() == null || recipient.getPatientName().isEmpty()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (recipient.getBloodGroup() == null || recipient.getBloodGroup().isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        if (recipient.getMedicalFacility() == null || recipient.getMedicalFacility().isEmpty()) {
            throw new IllegalArgumentException("Medical facility is required");
        }
        if (recipient.getDoctorName() == null || recipient.getDoctorName().isEmpty()) {
            throw new IllegalArgumentException("Doctor name is required");
        }
        if (recipient.getUnitsRequired() <= 0) {
            throw new IllegalArgumentException("Units required must be greater than 0");
        }
        
        return recipientDao.insert(recipient);
    }
    
    public boolean updateRecipient(Recipient recipient) {
        // Validate required fields
        if (recipient.getId() == null || recipient.getId().isEmpty()) {
            throw new IllegalArgumentException("Recipient ID is required");
        }
        if (recipient.getPatientName() == null || recipient.getPatientName().isEmpty()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (recipient.getBloodGroup() == null || recipient.getBloodGroup().isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        if (recipient.getMedicalFacility() == null || recipient.getMedicalFacility().isEmpty()) {
            throw new IllegalArgumentException("Medical facility is required");
        }
        if (recipient.getDoctorName() == null || recipient.getDoctorName().isEmpty()) {
            throw new IllegalArgumentException("Doctor name is required");
        }
        if (recipient.getUnitsRequired() <= 0) {
            throw new IllegalArgumentException("Units required must be greater than 0");
        }
        
        return recipientDao.update(recipient);
    }
    
    public boolean deleteRecipient(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Recipient ID is required");
        }
        return recipientDao.delete(id);
    }
    
    public List<Recipient> getRecipientsByBloodGroup(String bloodGroup) {
        if (bloodGroup == null || bloodGroup.isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        return recipientDao.findByBloodGroup(bloodGroup);
    }
    
    public List<Recipient> getRecipientsByUrgencyLevel(String urgencyLevel) {
        if (urgencyLevel == null || urgencyLevel.isEmpty()) {
            throw new IllegalArgumentException("Urgency level is required");
        }
        return recipientDao.findByUrgencyLevel(urgencyLevel);
    }
}