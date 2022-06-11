package com.msuapps.networkcall.Helper;

import com.msuapps.networkcall.BuildConfig;
import com.msuapps.networkcall.Interface.MyApi;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private final MyApi myApi;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildOkHttpClient())
                .build();

        myApi = retrofit.create(MyApi.class);
    }

    public MyApi getMyApi() {
        return myApi;
    }

    private OkHttpClient buildOkHttpClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(0, 1, TimeUnit.NANOSECONDS));

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request loginRequest = originalRequest.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Accept-Language", "en-gb")
                        .method(originalRequest.method(), originalRequest.body())
                        .build();

                return chain.proceed(loginRequest);
            }
        });

        httpClient.addInterceptor(loggingInterceptor);
        httpClient.readTimeout(1, TimeUnit.MINUTES);
        httpClient.connectTimeout(1, TimeUnit.MINUTES);

        return httpClient.build();
    }

}