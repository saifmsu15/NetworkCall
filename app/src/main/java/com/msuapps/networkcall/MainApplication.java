package com.msuapps.networkcall;

import android.app.Application;

import com.msuapps.networkcall.Helper.RestClient;

public class MainApplication extends Application {
    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();

        if (restClient != null) {
            restClient = new RestClient();
        }
    }

    public static RestClient getRestClient() {

        return restClient;
    }

}
