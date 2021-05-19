package com.example.tmdb.Retrofit;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitLoginInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8081/";



    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor).build();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory
                            .create(new GsonBuilder()
                                    .create()))
                    .build();
        }
        return retrofit;
    }
}
