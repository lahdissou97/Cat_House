package com.zagori.cathouse.repositories;

import com.zagori.cathouse.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatRemoteService {

    public CatRemoteService() {
    }

    public static CatApi createService(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL);

        return builder.build().create(CatApi.class);
    }
}
