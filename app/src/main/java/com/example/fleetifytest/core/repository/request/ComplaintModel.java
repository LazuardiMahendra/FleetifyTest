package com.example.fleetifytest.core.repository.request;

import java.io.File;

public class ComplaintModel {
    private String vehicleId;
    private String note;
    private String userId;
    private File photo;

    public ComplaintModel(String vehicleId, String note, String userId, File photo) {
        this.vehicleId = vehicleId;
        this.note = note;
        this.userId = userId;
        this.photo = photo;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }
}
