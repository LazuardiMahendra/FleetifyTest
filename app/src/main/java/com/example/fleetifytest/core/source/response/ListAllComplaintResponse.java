package com.example.fleetifytest.core.source.response;

import java.util.List;

public class ListAllComplaintResponse {
    private List<Complaint> complaint;

    public List<Complaint> getComplaint() {
        return complaint;
    }

    public void setComplaint(List<Complaint> complaint) {
        this.complaint = complaint;
    }

    public static class Complaint {
        String reportId;
        String vehicleId;
        String vehicleLicenseNumber;
        String vehicleName;
        String note;
        String photo;
        String createdAt;
        String createdBy;
        String reportStatus;

        public String getReportId() {
            return reportId;
        }

        public void setReportId(String reportId) {
            this.reportId = reportId;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getVehicleLicenseNumber() {
            return vehicleLicenseNumber;
        }

        public void setVehicleLicenseNumber(String vehicleLicenseNumber) {
            this.vehicleLicenseNumber = vehicleLicenseNumber;
        }

        public String getVehicleName() {
            return vehicleName;
        }

        public void setVehicleName(String vehicleName) {
            this.vehicleName = vehicleName;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getReportStatus() {
            return reportStatus;
        }

        public void setReportStatus(String reportStatus) {
            this.reportStatus = reportStatus;
        }
    }

}
