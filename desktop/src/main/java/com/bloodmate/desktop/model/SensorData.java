package com.bloodmate.desktop.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class SensorData {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty sensorId = new SimpleStringProperty();
    private final StringProperty sensorType = new SimpleStringProperty(); // TEMPERATURE, HUMIDITY, PRESSURE, GPS
    private final DoubleProperty value = new SimpleDoubleProperty();
    private final StringProperty unit = new SimpleStringProperty(); // Celsius, %, kPa, etc.
    private final ObjectProperty<LocalDateTime> timestamp = new SimpleObjectProperty<>();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty(); // ACTIVE, INACTIVE, ERROR
    private final StringProperty bloodBagId = new SimpleStringProperty();
    
    // Constructors
    public SensorData() {
        this.timestamp.set(LocalDateTime.now());
        this.status.set("ACTIVE");
    }
    
    public SensorData(String sensorId, String sensorType, double value, String unit, String location) {
        this();
        this.sensorId.set(sensorId);
        this.sensorType.set(sensorType);
        this.value.set(value);
        this.unit.set(unit);
        this.location.set(location);
    }
    
    // Getters and Setters
    public int getId() {
        return id.get();
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
    public String getSensorId() {
        return sensorId.get();
    }
    
    public StringProperty sensorIdProperty() {
        return sensorId;
    }
    
    public void setSensorId(String sensorId) {
        this.sensorId.set(sensorId);
    }
    
    public String getSensorType() {
        return sensorType.get();
    }
    
    public StringProperty sensorTypeProperty() {
        return sensorType;
    }
    
    public void setSensorType(String sensorType) {
        this.sensorType.set(sensorType);
    }
    
    public double getValue() {
        return value.get();
    }
    
    public DoubleProperty valueProperty() {
        return value;
    }
    
    public void setValue(double value) {
        this.value.set(value);
    }
    
    public String getUnit() {
        return unit.get();
    }
    
    public StringProperty unitProperty() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit.set(unit);
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
    
    public String getLocation() {
        return location.get();
    }
    
    public StringProperty locationProperty() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location.set(location);
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
    
    public String getBloodBagId() {
        return bloodBagId.get();
    }
    
    public StringProperty bloodBagIdProperty() {
        return bloodBagId;
    }
    
    public void setBloodBagId(String bloodBagId) {
        this.bloodBagId.set(bloodBagId);
    }
    
    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id.get() +
                ", sensorId='" + sensorId.get() + '\'' +
                ", sensorType='" + sensorType.get() + '\'' +
                ", value=" + value.get() +
                ", unit='" + unit.get() + '\'' +
                ", timestamp=" + timestamp.get() +
                ", location='" + location.get() + '\'' +
                ", status='" + status.get() + '\'' +
                ", bloodBagId='" + bloodBagId.get() + '\'' +
                '}';
    }
}