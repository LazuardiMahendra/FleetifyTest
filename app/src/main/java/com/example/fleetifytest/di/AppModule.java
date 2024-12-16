package com.example.fleetifytest.di;


import com.example.fleetifytest.core.repository.MainDataSource;
import com.example.fleetifytest.core.repository.MainRepository;
import com.example.fleetifytest.core.source.ApiService;
import com.example.fleetifytest.core.source.Routing;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

//    @Provides
//    public Retrofit networkModule() {
//        return new Retrofit.Builder()
//                .baseUrl(Routing.BASE_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                .build();
//
//    }

//    @Provides
//    @Singleton
//    public ApiService apiService(Retrofit retrofit) {
//        return retrofit.create(ApiService.class);
//    }

    @Provides
    public static ApiService moduleApiService() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Routing.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

    @Provides
    public static MainDataSource moduleDataSource(ApiService apiService) {
        return new MainRepository(apiService);
    }
}
