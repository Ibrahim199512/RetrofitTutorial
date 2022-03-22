package com.app.retrofittutorial;

import com.app.retrofittutorial.categories.AllCategories;
import com.app.retrofittutorial.register.RegisterResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceApi {
    String ENDPOINT = "http://54.210.44.4/api/";

    //////////////////////////
    ///////////GET////////////
    /////////////////////////


    @GET("getAllCategories")
    Call<AllCategories> getAllCategories(
            @Header("Authorization") String token);

    @POST("login")
    Call<RegisterResponse> login
            (@Header("Accept") String accept
                    , @Body SendLogin fields);

    @Multipart
    @POST("register")
    Call<RegisterResponse> register(@Header("Accept") String accept
            , @Part("name") RequestBody name
            , @Part("email") RequestBody email
            , @Part("phone_number") RequestBody phoneNumber
            , @Part("address") RequestBody address
            , @Part("password") RequestBody password
            , @Part("password_confirmation") RequestBody passwordConfirmation
            , @Part MultipartBody.Part image);
    //////////////////////////
    /////////// POST //////////
    /////////////////////////

}
