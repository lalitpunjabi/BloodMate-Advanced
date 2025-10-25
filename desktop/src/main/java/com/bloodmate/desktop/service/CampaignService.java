package com.bloodmate.desktop.service;

import com.bloodmate.desktop.model.Campaign;
import com.bloodmate.desktop.repo.CampaignDao;
import com.bloodmate.desktop.util.DatabaseManager;

import java.sql.Connection;
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
        // Placeholder implementation
        return null;
    }
    
    public boolean addCampaign(Campaign campaign) {
        return campaignDao.insert(campaign);
    }
    
    public boolean updateCampaign(Campaign campaign) {
        return campaignDao.update(campaign);
    }
    
    public boolean deleteCampaign(String id) {
        return campaignDao.delete(id);
    }
}