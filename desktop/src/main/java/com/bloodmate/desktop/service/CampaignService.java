package com.bloodmate.desktop.service;

import com.bloodmate.desktop.model.Campaign;
import com.bloodmate.desktop.repo.CampaignDao;
import com.bloodmate.desktop.util.DatabaseManager;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

/**
 * Service for managing blood donation campaigns
 */
public class CampaignService {
    
    private CampaignDao campaignDao;
    
    public CampaignService() {
        Connection connection = DatabaseManager.getConnection();
        this.campaignDao = new CampaignDao(connection);
    }
    
    public List<Campaign> getAllCampaigns() {
        return campaignDao.findAll();
    }
    
    public Campaign getCampaignById(String id) {
        // This would require implementing findById in CampaignDao
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Campaign ID is required");
        }
        return null; // Placeholder implementation
    }
    
    public boolean addCampaign(Campaign campaign) {
        // Validate required fields
        if (campaign.getTitle() == null || campaign.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Campaign title is required");
        }
        if (campaign.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }
        if (campaign.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required");
        }
        if (campaign.getLocation() == null || campaign.getLocation().isEmpty()) {
            throw new IllegalArgumentException("Location is required");
        }
        if (campaign.getCity() == null || campaign.getCity().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        if (campaign.getState() == null || campaign.getState().isEmpty()) {
            throw new IllegalArgumentException("State is required");
        }
        if (campaign.getOrganizer() == null || campaign.getOrganizer().isEmpty()) {
            throw new IllegalArgumentException("Organizer is required");
        }
        
        // Set default status if not provided
        if (campaign.getStatus() == null || campaign.getStatus().isEmpty()) {
            campaign.setStatus("PLANNED");
        }
        
        return campaignDao.insert(campaign);
    }
    
    public boolean updateCampaign(Campaign campaign) {
        // Validate required fields
        if (campaign.getId() == null || campaign.getId().isEmpty()) {
            throw new IllegalArgumentException("Campaign ID is required");
        }
        if (campaign.getTitle() == null || campaign.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Campaign title is required");
        }
        if (campaign.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }
        if (campaign.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required");
        }
        if (campaign.getLocation() == null || campaign.getLocation().isEmpty()) {
            throw new IllegalArgumentException("Location is required");
        }
        if (campaign.getCity() == null || campaign.getCity().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        if (campaign.getState() == null || campaign.getState().isEmpty()) {
            throw new IllegalArgumentException("State is required");
        }
        if (campaign.getOrganizer() == null || campaign.getOrganizer().isEmpty()) {
            throw new IllegalArgumentException("Organizer is required");
        }
        
        return campaignDao.update(campaign);
    }
    
    public boolean deleteCampaign(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Campaign ID is required");
        }
        return campaignDao.delete(id);
    }
    
    public List<Campaign> getCampaignsByStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }
        return campaignDao.findByStatus(status);
    }
    
    public List<Campaign> getUpcomingCampaigns() {
        return campaignDao.findUpcoming();
    }
    
    public List<Campaign> getCampaignsByCity(String city) {
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        return campaignDao.findByCity(city);
    }
    
    public boolean updateCollectedUnits(String id, int collectedUnits) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Campaign ID is required");
        }
        if (collectedUnits < 0) {
            throw new IllegalArgumentException("Collected units cannot be negative");
        }
        return campaignDao.updateCollectedUnits(id, collectedUnits);
    }
    
    public boolean updateStatus(String id, String status) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Campaign ID is required");
        }
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }
        return campaignDao.updateStatus(id, status);
    }
}