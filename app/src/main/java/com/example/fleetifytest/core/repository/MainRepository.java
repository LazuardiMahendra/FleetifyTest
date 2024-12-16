package com.example.fleetifytest.core.repository;

import android.util.Log;

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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
        PublishSubject<ComplaintResponse> result = PublishSubject.create();
        RequestBody mainVehicleId = RequestBody.create(vehicleId, MediaType.parse("text/plain"));
        RequestBody mainNote = RequestBody.create(note, MediaType.parse("text/plain"));
        RequestBody mainUserId = RequestBody.create(userId, MediaType.parse("text/plain"));
        RequestBody photoReqBody = RequestBody.create(photo, MediaType.parse("image/jpeg"));
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("image", photo.getName(), photoReqBody);
        Flowable<ComplaintResponse> flowable = apiService.createComplaint(mainVehicleId, mainNote, mainUserId, photoPart).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).take(1).onErrorReturn(throwable -> {
            Log.e("ComplaintError", "Error creating complaint", throwable);
            return new ComplaintResponse();
        });

        flowable.subscribe(response -> result.onNext(response), throwable -> result.onError(throwable));
        return result.toFlowable(BackpressureStrategy.BUFFER);
    }
}
