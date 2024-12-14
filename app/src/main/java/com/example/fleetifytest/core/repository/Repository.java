package com.example.fleetifytest.core.repository;

import android.annotation.SuppressLint;

import com.example.fleetifytest.core.source.ApiService;
import com.example.fleetifytest.core.source.response.ComplaintResponse;
import com.example.fleetifytest.core.source.response.ListAllComplaintResponse;
import com.example.fleetifytest.core.source.response.ListVehicleResponse;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class Repository implements DataSource {
    private final ApiService apiService;

    @Inject
    public Repository(ApiService apiService) {
        this.apiService = apiService;
    }

    @SuppressLint("CheckResult")
    @Override
    public Flowable<ListVehicleResponse.Vehicle> getListVehicle() {
        PublishSubject<ListVehicleResponse.Vehicle> result = PublishSubject.create();
        apiService.getListVehicle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(response -> {
                    result.onNext((ListVehicleResponse.Vehicle) response);
                }, throwable -> {
                    result.onError(throwable);
                });
        return result.toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<ListAllComplaintResponse.Complaint> getAllComplaint() {
        return null;
    }

    @Override
    public Flowable<ComplaintResponse> createComplaint(String vehicleId, String note, String userId, File photo) {
        return null;
    }
}
