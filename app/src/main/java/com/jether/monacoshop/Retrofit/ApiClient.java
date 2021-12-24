package com.jether.monacoshop.Retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL="https://manjether.000webhostapp.com/";


    public static Retrofit retrofit= null;




    public static synchronized Retrofit getApiClient()
    {
        if(retrofit == null)
        {

            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
