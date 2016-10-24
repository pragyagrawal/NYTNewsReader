package com.binarybricks.pragya.nytnewsreader.network;

import com.binarybricks.pragya.nytnewsreader.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PRAGYA on 10/22/2016.
 */

public class RetroClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient.addInterceptor(httpLoggingInterceptor);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.ARTICLE_SEARCH_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build();
        }
        return retrofit;
    }
}
