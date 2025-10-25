package com.bloodmate.desktop.service;

import com.bloodmate.desktop.model.VoiceCommand;
import com.bloodmate.desktop.repo.VoiceCommandDao;
import com.bloodmate.desktop.util.DatabaseManager;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing voice commands in the BloodMate system
 */
public class VoiceCommandService {
    
    private VoiceCommandDao voiceCommandDao;
    
    public VoiceCommandService() {
        Connection connection = DatabaseManager.getConnection();
        this.voiceCommandDao = new VoiceCommandDao(connection);
    }
    
    public List<VoiceCommand> getAllCommands() {
        return voiceCommandDao.findAll();
    }
    
    public VoiceCommand getCommandById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Command ID is required");
        }
        
        Optional<VoiceCommand> command = voiceCommandDao.findById(id);
        return command.orElse(null);
    }
    
    public boolean addCommand(VoiceCommand command) {
        // Validate required fields
        if (command.getCommandText() == null || command.getCommandText().isEmpty()) {
            throw new IllegalArgumentException("Command text is required");
        }
        
        return voiceCommandDao.insert(command);
    }
    
    public boolean updateCommand(VoiceCommand command) {
        // Validate required fields
        if (command.getId() == null || command.getId().isEmpty()) {
            throw new IllegalArgumentException("Command ID is required");
        }
        
        return voiceCommandDao.update(command);
    }
    
    public boolean deleteCommand(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Command ID is required");
        }
        return voiceCommandDao.delete(id);
    }
    
    public List<VoiceCommand> getCommandsByUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
        return voiceCommandDao.findByUserId(userId);
    }
    
    public List<VoiceCommand> getCommandsByStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }
        return voiceCommandDao.findByStatus(status);
    }
    
    public List<VoiceCommand> getRecentCommands(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be positive");
        }
        return voiceCommandDao.findRecentCommands(limit);
    }
    
    /**
     * Process a voice command and determine the appropriate action
     * @param commandText The recognized voice command text
     * @param userId The ID of the user issuing the command
     * @return The action to be performed
     */
    public String processVoiceCommand(String commandText, String userId) {
        if (commandText == null || commandText.isEmpty()) {
            throw new IllegalArgumentException("Command text is required");
        }
        
        // Create a voice command record
        VoiceCommand command = new VoiceCommand();
        command.setCommandText(commandText);
        command.setRecognizedText(commandText);
        command.setUserId(userId);
        
        // Determine action based on command text
        String action = determineAction(commandText);
        command.setAction(action);
        command.setStatus("PROCESSED");
        command.setResult("Action determined: " + action);
        
        // Save the command
        voiceCommandDao.insert(command);
        
        return action;
    }
    
    /**
     * Determine the appropriate action based on the command text
     * @param commandText The recognized voice command text
     * @return The action to be performed
     */
    private String determineAction(String commandText) {
        String lowerCommand = commandText.toLowerCase();
        
        // Dashboard commands
        if (lowerCommand.contains("show dashboard") || lowerCommand.contains("go to dashboard")) {
            return "SHOW_DASHBOARD";
        }
        
        // Donor management commands
        if (lowerCommand.contains("show donors") || lowerCommand.contains("go to donors") || 
            lowerCommand.contains("donor management")) {
            return "SHOW_DONORS";
        }
        
        // Recipient commands
        if (lowerCommand.contains("show recipients") || lowerCommand.contains("go to recipients") || 
            lowerCommand.contains("recipient management")) {
            return "SHOW_RECIPIENTS";
        }
        
        // Inventory commands
        if (lowerCommand.contains("show inventory") || lowerCommand.contains("go to inventory") || 
            lowerCommand.contains("blood inventory")) {
            return "SHOW_INVENTORY";
        }
        
        // Campaign commands
        if (lowerCommand.contains("show campaigns") || lowerCommand.contains("go to campaigns") || 
            lowerCommand.contains("campaign management")) {
            return "SHOW_CAMPAIGNS";
        }
        
        // Emergency commands
        if (lowerCommand.contains("show emergency") || lowerCommand.contains("go to emergency") || 
            lowerCommand.contains("emergency response")) {
            return "SHOW_EMERGENCY";
        }
        
        // Reports commands
        if (lowerCommand.contains("show reports") || lowerCommand.contains("go to reports") || 
            lowerCommand.contains("statistics")) {
            return "SHOW_REPORTS";
        }
        
        // Settings commands
        if (lowerCommand.contains("show settings") || lowerCommand.contains("go to settings")) {
            return "SHOW_SETTINGS";
        }
        
        // Find specific donor
        if (lowerCommand.contains("find donor")) {
            return "FIND_DONOR";
        }
        
        // Check emergency requests
        if (lowerCommand.contains("check emergency") || lowerCommand.contains("emergency requests")) {
            return "CHECK_EMERGENCY";
        }
        
        // Display inventory statistics
        if (lowerCommand.contains("inventory statistics") || lowerCommand.contains("blood statistics")) {
            return "DISPLAY_INVENTORY_STATS";
        }
        
        // Default action
        return "UNKNOWN_COMMAND";
    }
}