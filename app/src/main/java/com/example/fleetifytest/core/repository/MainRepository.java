package com.example.fleetifytest.core.repository;

import com.example.fleetifytest.core.source.ApiService;
import com.example.fleetifytest.core.source.response.ComplaintResponse;
import com.example.fleetifytest.core.source.response.ListAllComplaintResponse;
import com.example.fleetifytest.core.source.response.ListVehicleResponse;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class MainRepository implements MainDataSource {
    private final ApiService apiService;

    @Inject
    public MainRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Flowable<List<ListVehicleResponse.Vehicle>> getListVehicle() {
        PublishSubject<List<ListVehicleResponse.Vehicle>> result = PublishSubject.create();
        apiService.getListVehicle().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).take(1).subscribe(response -> result.onNext(response), throwable -> result.onError(throwable));
        return result.toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<ListAllComplaintResponse.Complaint>> getAllComplaint(String userId) {
        PublishSubject<List<ListAllComplaintResponse.Complaint>> result = PublishSubject.create();
        apiService.getAllComplaint(userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).take(1).subscribe(response -> result.onNext(response), throwable -> result.onError(throwable));
        return result.toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<ComplaintResponse> createComplaint(String vehicleId, String note, String userId, File photo) {
        return null;
    }
}
