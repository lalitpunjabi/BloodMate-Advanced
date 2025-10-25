package com.bloodmate.desktop.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class VoiceCommand {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty commandText = new SimpleStringProperty();
    private final StringProperty recognizedText = new SimpleStringProperty();
    private final StringProperty action = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> timestamp = new SimpleObjectProperty<>();
    private final StringProperty userId = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty(); // PENDING, PROCESSED, FAILED
    private final StringProperty result = new SimpleStringProperty();

    public VoiceCommand() {
        this.id.set(java.util.UUID.randomUUID().toString());
        this.timestamp.set(LocalDateTime.now());
        this.status.set("PENDING");
    }

    // Getters and Setters
    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getCommandText() {
        return commandText.get();
    }

    public StringProperty commandTextProperty() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText.set(commandText);
    }

    public String getRecognizedText() {
        return recognizedText.get();
    }

    public StringProperty recognizedTextProperty() {
        return recognizedText;
    }

    public void setRecognizedText(String recognizedText) {
        this.recognizedText.set(recognizedText);
    }

    public String getAction() {
        return action.get();
    }

    public StringProperty actionProperty() {
        return action;
    }

    public void setAction(String action) {
        this.action.set(action);
    }

    public LocalDateTime getTimestamp() {
        return timestamp.get();
    }

    public ObjectProperty<LocalDateTime> timestampProperty() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp.set(timestamp);
    }

    public String getUserId() {
        return userId.get();
    }

    public StringProperty userIdProperty() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId.set(userId);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getResult() {
        return result.get();
    }

    public StringProperty resultProperty() {
        return result;
    }

    public void setResult(String result) {
        this.result.set(result);
    }

    @Override
    public String toString() {
        return "VoiceCommand{" +
                "id='" + getId() + '\'' +
                ", commandText='" + getCommandText() + '\'' +
                ", recognizedText='" + getRecognizedText() + '\'' +
                ", action='" + getAction() + '\'' +
                ", timestamp=" + getTimestamp() +
                ", userId='" + getUserId() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", result='" + getResult() + '\'' +
                '}';
    }
}