package com.example.fleetifytest.core.source;

import com.example.fleetifytest.core.source.response.ComplaintResponse;
import com.example.fleetifytest.core.source.response.ListAllComplaintResponse;
import com.example.fleetifytest.core.source.response.ListVehicleResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @GET(Routing.GET_LIST_VEHICLE)
    Flowable<List<ListVehicleResponse.Vehicle>> getListVehicle();

    @GET(Routing.GET_LIST_VEHICLE)
    Flowable<List<ListAllComplaintResponse.Complaint>> getAllComplaint(@Path("userId") String userId);

    @Multipart
    @POST(Routing.CREATE_COMPLAINT_URL)
    Flowable<ComplaintResponse> createComplaint(
            @Part("vehicleId") RequestBody vehicleId,
            @Part("note") RequestBody note,
            @Part("userId") RequestBody userId,
            @Part MultipartBody photo
    );

}
