package com.app.retrofittutorial;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.retrofittutorial.categories.AllCategories;
import com.app.retrofittutorial.categories.Category;
import com.app.retrofittutorial.register.RegisterResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ServiceApi serviceApi;
    Button getAllCategories, login, register;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) { // There are no request codes
                Intent data = result.getData();
                Log.e("data", data.getDataString() + "");
                File file = null;
                try {
                    file = FileUtil.from(MainActivity.this, data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                register(file);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceApi = Creator.getClient().create(ServiceApi.class);
        getAllCategories = findViewById(R.id.get_all_categories);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        getAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllCategories();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                someActivityResultLauncher.launch(intent);
            }
        });


    }

    private void getAllCategories() {
        Call<AllCategories> call = serviceApi.getAllCategories(
                "Bearer " + "48|CJhzpszvq8MR3GEKJbqBntDk2SLn9JTQ9fKE0PT4fsfsdfdsfsdfsff");
        call.enqueue(new Callback<AllCategories>() {
            @Override
            public void onResponse(Call<AllCategories> call, Response<AllCategories> response) {
                Log.d("response code", response.code() + "");
                if (response.isSuccessful()) {
                    Log.d("Success", new Gson().toJson(response.body()));
                    AllCategories getAllCategories = response.body();
                    List<Category> data = getAllCategories.getData().getCategory();
                } else {
                    String errorMessage = parseError(response);
                    Log.e("errorMessage", errorMessage + "");
                }
            }

            @Override
            public void onFailure(Call<AllCategories> call, Throwable t) {
                Log.d("onFailure", t.getMessage() + "");
                call.cancel();
            }
        });
    }

    public static String parseError(Response<?> response) {
        String errorMsg = null;
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            errorMsg = jObjError.getString("message");
            return errorMsg;
        } catch (Exception e) {
        }
        return errorMsg;
    }


    private void login() {
        SendLogin sendLogin = new SendLogin();
        sendLogin.setEmail("test15555@gmail.com");
        sendLogin.setPassword("12345678"); /** GET List Resources **/
        Call<RegisterResponse> call = serviceApi.login("application/json", sendLogin);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("response code", response.code() + "");
                if (response.isSuccessful()) {
                    Log.d("Success", new Gson().toJson(response.body()));
                } else {
                    String errorMessage = parseError2(response);
                    Log.e("errorMessage", errorMessage + "");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage() + "");
                call.cancel();
            }
        });
    }

    public static String parseError2(Response<?> response) {
        String errorMsg = null;
        try {
            JSONObject jsonObject = new JSONObject(response.errorBody().string());
            JSONObject jsonObject2 = jsonObject.getJSONObject("errors");
            JSONArray jsonArray = jsonObject2.getJSONArray("email");
            String s = jsonArray.getString(0);
            return s;
        } catch (Exception e) {
        }
        return errorMsg;
    }

    private void register(File resourceFile) {
        MultipartBody.Part body = null;
        if (resourceFile != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), resourceFile);
            body = MultipartBody.Part.createFormData("img", resourceFile.getName(), requestFile);
        }
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), "name");
        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), "test5@gmail.com");
        RequestBody phoneNumber = RequestBody.create(MediaType.parse("multipart/form-data"), "12345678959");
        RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), "address");
        RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), "password");
        RequestBody passwordConfirmation = RequestBody.create(MediaType.parse("multipart/form-data"), "password");
        Call<RegisterResponse> call = serviceApi.register("application/json"
                , name
                , email
                , phoneNumber
                , address
                , password
                , passwordConfirmation
                , body);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("response code", response.code() + "");
                if (response.isSuccessful() || response.code() == 200) {
                    Log.d("response_inside", response.code() + "");
                    Log.d("Success", new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage() + "");
                call.cancel();
            }
        });
    }
}