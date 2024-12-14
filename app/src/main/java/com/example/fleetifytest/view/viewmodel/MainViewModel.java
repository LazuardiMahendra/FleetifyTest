package com.example.fleetifytest.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.example.fleetifytest.core.repository.DataSource;
import com.example.fleetifytest.core.source.response.ListVehicleResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.BackpressureStrategy;

public class MainViewModel extends ViewModel {
    private final DataSource dataSource;

    @Inject
    public MainViewModel(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public LiveData<ListVehicleResponse.Vehicle> getListVehicle() {
        return LiveDataReactiveStreams.fromPublisher(
                dataSource.getListVehicle().toObservable().toFlowable(BackpressureStrategy.BUFFER)
        );
    }
}
