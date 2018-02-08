package com.dfl.topicality.network;

import com.dfl.topicality.datamodel.ArticlesResponse;
import com.dfl.topicality.datamodel.SourcesResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by loureiro on 28-01-2018.
 */

interface NewsApi {

    @GET("v2/sources")
    Flowable<SourcesResponse> getSources(@Query("category") String category, @Query("language") String language, @Query("country") String country);

    @GET("/v2/everything")
    Flowable<ArticlesResponse> getEverything(@Query("category") String category, @Query("country") String country);

    @GET("/v2/top-headlines")
    Flowable<ArticlesResponse> getTopHeadlines(@Query("category") String category, @Query("country") String country,
                                               @Query("sources") String sources, @Query("q") String q, @Query("pagesize") int pagesize,
                                               @Query("page") int page);
}
