package com.example.fleetifytest.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.example.fleetifytest.core.repository.MainDataSource;
import com.example.fleetifytest.core.source.response.ComplaintResponse;
import com.example.fleetifytest.core.source.response.ListAllComplaintResponse;
import com.example.fleetifytest.core.source.response.ListVehicleResponse;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.BackpressureStrategy;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final MainDataSource mainDataSource;

    @Inject
    public MainViewModel(MainDataSource mainDataSource) {
        this.mainDataSource = mainDataSource;
    }

    public LiveData<List<ListVehicleResponse.Vehicle>> getListVehicle() {
        return LiveDataReactiveStreams.fromPublisher(mainDataSource.getListVehicle().toObservable().toFlowable(BackpressureStrategy.BUFFER));
    }


    public LiveData<List<ListAllComplaintResponse.Complaint>> getAllComplaint(String userId) {
        return LiveDataReactiveStreams.fromPublisher(mainDataSource.getAllComplaint(userId).toObservable().toFlowable(BackpressureStrategy.BUFFER));
    }

    public LiveData<ComplaintResponse> createComplaint(String vehicleId, String note, String userId, File photo) {
        return LiveDataReactiveStreams.fromPublisher(mainDataSource.createComplaint(vehicleId, note, userId, photo).toObservable().toFlowable(BackpressureStrategy.BUFFER));
    }
}
