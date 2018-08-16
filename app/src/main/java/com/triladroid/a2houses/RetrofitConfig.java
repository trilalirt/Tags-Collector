package com.triladroid.a2houses;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitConfig {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.instagram.com/explore/tags/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    InstaServiceInterface service = retrofit.create(InstaServiceInterface.class);

    public InstaServiceInterface getInstaService() {
        return service;
    }
}
