package com.example.fleetifytest.core.repository;

import com.example.fleetifytest.core.source.response.ComplaintResponse;
import com.example.fleetifytest.core.source.response.ListAllComplaintResponse;
import com.example.fleetifytest.core.source.response.ListVehicleResponse;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface MainDataSource {

    Flowable<List<ListVehicleResponse.Vehicle>> getListVehicle();

    Flowable<ListAllComplaintResponse.Complaint> getAllComplaint();

    Flowable<ComplaintResponse> createComplaint(String vehicleId, String note, String userId, File photo);

}
