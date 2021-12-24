package com.jether.monacoshop.Retrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("register.php")
    Call<Users> performRegistration(
            @Query("Name") String user_name,
            @Query("Email") String user_email,
            @Query("Mobile") String user_mobile,
            @Query("Location") String user_loc,
            @Query("Password") String user_pass,
             @Query("Profile") String user_profi
    );


    @GET("login.php")
    Call<Users> performLogin(
            @Query("Email") String user_email,
            @Query("Password") String user_pass

    );


//    @FormUrlEncoded
//    @POST("order.php")
//    Call<Users>insertOrder(
//            @Field("Cost") String cos,
//            @Field("customer") String cust,
//            @Field("location") String loc ,
//            @Field("phone") String pho,
//            @Field("status") String st,
//            @Field("userid") String uz
//
//            );


    @FormUrlEncoded
    @POST("order.php")
    Call<Users>insertOrder(
            @FieldMap Map<String, String> jay1

    );


    @FormUrlEncoded
    @POST("order.php")
    Call<Users> insertItems(
            @FieldMap Map<String, String> map

    );


//    @FormUrlEncoded
//    @POST("*****")
//    Call<model> insertChordReq(@FieldMap Map<String, String> fields);


    @GET("latest.php")
   Call<Users> haveLatest();

    @GET("all.php")
    Call<Users> haveAll();

    @GET("allHair.php")
    Call<Users> haveHair();

    @GET("allbody.php")
    Call<Users> haveBody();



}
