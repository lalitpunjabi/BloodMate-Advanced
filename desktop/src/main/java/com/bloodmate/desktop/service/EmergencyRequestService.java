package com.bloodmate.desktop.service;

import com.bloodmate.desktop.model.EmergencyRequest;
import com.bloodmate.desktop.repo.EmergencyRequestDao;
import com.bloodmate.desktop.util.DatabaseManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing emergency blood requests
 */
public class EmergencyRequestService {
    
    private EmergencyRequestDao emergencyRequestDao;
    private boolean isDatabaseAvailable = true;
    
    public EmergencyRequestService() {
        Connection connection = DatabaseManager.getConnection();
        if (connection != null) {
            this.emergencyRequestDao = new EmergencyRequestDao(connection);
            this.isDatabaseAvailable = true;
        } else {
            this.emergencyRequestDao = null;
            this.isDatabaseAvailable = false;
            System.err.println("EmergencyRequestService: Database connection not available. Database functionality will be limited.");
        }
    }
    
    public List<EmergencyRequest> getAllRequests() {
        if (emergencyRequestDao == null) {
            System.err.println("getAllRequests: Database not available, returning empty list");
            return new ArrayList<>();
        }
        return emergencyRequestDao.findAll();
    }
    
    public EmergencyRequest getRequestById(String id) {
        // This would require implementing findById in EmergencyRequestDao
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Request ID is required");
        }
        return null; // Placeholder implementation
    }
    
    public boolean addRequest(EmergencyRequest request) {
        if (emergencyRequestDao == null) {
            System.err.println("addRequest: Database not available, request not saved");
            return false;
        }
        
        // Validate required fields
        if (request.getPatientName() == null || request.getPatientName().isEmpty()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (request.getBloodGroup() == null || request.getBloodGroup().isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        if (request.getHospital() == null || request.getHospital().isEmpty()) {
            throw new IllegalArgumentException("Hospital is required");
        }
        if (request.getDoctor() == null || request.getDoctor().isEmpty()) {
            throw new IllegalArgumentException("Doctor is required");
        }
        
        // Set default status if not provided
        if (request.getStatus() == null || request.getStatus().isEmpty()) {
            request.setStatus("PENDING");
        }
        
        // Set default urgency level if not provided
        if (request.getUrgencyLevel() == null || request.getUrgencyLevel().isEmpty()) {
            request.setUrgencyLevel("HIGH");
        }
        
        return emergencyRequestDao.insert(request);
    }
    
    public boolean updateRequest(EmergencyRequest request) {
        if (emergencyRequestDao == null) {
            System.err.println("updateRequest: Database not available, request not updated");
            return false;
        }
        
        // Validate required fields
        if (request.getId() == null || request.getId().isEmpty()) {
            throw new IllegalArgumentException("Request ID is required");
        }
        if (request.getPatientName() == null || request.getPatientName().isEmpty()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (request.getBloodGroup() == null || request.getBloodGroup().isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        if (request.getHospital() == null || request.getHospital().isEmpty()) {
            throw new IllegalArgumentException("Hospital is required");
        }
        if (request.getDoctor() == null || request.getDoctor().isEmpty()) {
            throw new IllegalArgumentException("Doctor is required");
        }
        
        return emergencyRequestDao.update(request);
    }
    
    public boolean deleteRequest(String id) {
        if (emergencyRequestDao == null) {
            System.err.println("deleteRequest: Database not available, request not deleted");
            return false;
        }
        
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Request ID is required");
        }
        return emergencyRequestDao.delete(id);
    }
    
    public List<EmergencyRequest> getRequestsByStatus(String status) {
        if (emergencyRequestDao == null) {
            System.err.println("getRequestsByStatus: Database not available, returning empty list");
            return new ArrayList<>();
        }
        
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }
        return emergencyRequestDao.findByStatus(status);
    }
    
    public List<EmergencyRequest> getRequestsByUrgencyLevel(String urgencyLevel) {
        if (emergencyRequestDao == null) {
            System.err.println("getRequestsByUrgencyLevel: Database not available, returning empty list");
            return new ArrayList<>();
        }
        
        if (urgencyLevel == null || urgencyLevel.isEmpty()) {
            throw new IllegalArgumentException("Urgency level is required");
        }
        return emergencyRequestDao.findByUrgencyLevel(urgencyLevel);
    }
    
    public List<EmergencyRequest> getRequestsByBloodGroup(String bloodGroup) {
        if (emergencyRequestDao == null) {
            System.err.println("getRequestsByBloodGroup: Database not available, returning empty list");
            return new ArrayList<>();
        }
        
        if (bloodGroup == null || bloodGroup.isEmpty()) {
            throw new IllegalArgumentException("Blood group is required");
        }
        return emergencyRequestDao.findByBloodGroup(bloodGroup);
    }
    
    public List<EmergencyRequest> getPendingRequests() {
        if (emergencyRequestDao == null) {
            System.err.println("getPendingRequests: Database not available, returning empty list");
            return new ArrayList<>();
        }
        return emergencyRequestDao.findPending();
    }
    
    public boolean assignUnit(String requestId, String unitId) {
        if (emergencyRequestDao == null) {
            System.err.println("assignUnit: Database not available, unit not assigned");
            return false;
        }
        
        if (requestId == null || requestId.isEmpty()) {
            throw new IllegalArgumentException("Request ID is required");
        }
        if (unitId == null || unitId.isEmpty()) {
            throw new IllegalArgumentException("Unit ID is required");
        }
        return emergencyRequestDao.assignUnit(requestId, unitId);
    }
    
    public boolean fulfillRequest(String requestId) {
        if (emergencyRequestDao == null) {
            System.err.println("fulfillRequest: Database not available, request not fulfilled");
            return false;
        }
        
        if (requestId == null || requestId.isEmpty()) {
            throw new IllegalArgumentException("Request ID is required");
        }
        return emergencyRequestDao.fulfillRequest(requestId);
    }
}