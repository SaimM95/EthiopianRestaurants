package com.example.abc.ethiopianrestaurants.network;

import org.jetbrains.annotations.NotNull;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

class RetrofitClient {

    private static final String YELP_API_KEY
        = "T9B2NNOxAI40I3ZtPRU9w8FR4BTeEXMN7blsbXywLhWDd9uMpgMQJ9iNbX9OmOHVIT-1gLf485S"
        + "--b0g6IjPggGXVm9D5r6ozZx3e_xq_UZSsUbZual3kN5KiXb5XXYx";
    private static final String BASE_URL = "https://api.yelp.com";
    private static final RetrofitClient INSTANCE = new RetrofitClient();

    static RetrofitClient getInstance() {
        return INSTANCE;
    }

    private Retrofit retrofit;

    private RetrofitClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(new HeaderInterceptor());

        // TODO: logging interceptor should only be added for dev build
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(interceptor);

        retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build();
    }

    Retrofit getRetrofit() {
        return retrofit;
    }

    class HeaderInterceptor implements Interceptor {

        private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
        private static final String AUTHORIZATION_HEADER_VALUE = "Bearer " + YELP_API_KEY;

        @NotNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            request = request.newBuilder()
                .addHeader(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_VALUE)
                .build();

            return chain.proceed(request);
        }
    }
}
