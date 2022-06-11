package com.msuapps.networkcall.Interface;

import com.msuapps.networkcall.Model.CurrencyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {

    @GET("live?access_key=3b11ed83db02736b9dca66c9a6e51f8c")
    Call<List<CurrencyModel>> fetchCurrency();
}
