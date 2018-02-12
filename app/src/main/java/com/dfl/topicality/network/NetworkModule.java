package com.dfl.topicality.network;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by loureiro on 28-01-2018.
 */

class NetworkModule {

    private final static String BASE_URL = "https://newsapi.org/";
    private String apiKey;

    private NewsApi newsApi;

    NetworkModule(String apiKey) {
        this.apiKey = apiKey;
        setRetrofit();
    }

    private Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder().maxAge(2, TimeUnit.MINUTES)
                    .build();
            return response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build();
        };
    }

    private Interceptor provideApiKeyInterceptor() {
        return chain -> {
            Request request = chain.request().newBuilder().addHeader("X-Api-Key", apiKey).build();
            return chain.proceed(request);
        };
    }

    private void setRetrofit() {
        newsApi = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(getDefaultClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsApi.class);
    }

    private OkHttpClient getDefaultClient() {
        final int cacheMaxSize = 10 * 1024 * 1024;  // 10 MiB

        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addNetworkInterceptor(provideCacheInterceptor());
        okHttpClientBuilder.addInterceptor(provideApiKeyInterceptor());
        okHttpClientBuilder.readTimeout(45, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(45, TimeUnit.SECONDS);
        okHttpClientBuilder.cache(new Cache(new File("/"), cacheMaxSize));
        return okHttpClientBuilder.build();
    }

    NewsApi getNewsApi() {
        return newsApi;
    }
}
