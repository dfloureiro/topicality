package com.dfl.topicality.network;

import com.dfl.topicality.datamodel.ArticlesResponse;
import com.dfl.topicality.datamodel.SourcesResponse;

import io.reactivex.Flowable;

/**
 * Created by loureiro on 28-01-2018.
 */

public class RequestFactory {

    private NetworkModule networkModule;

    public RequestFactory(String apiKey) {
        networkModule = new NetworkModule(apiKey);
    }

    public Flowable<SourcesResponse> getSources(String category, String language, String country) {
        return networkModule.getNewsApi().getSources(category, language, country);
    }

    public Flowable<ArticlesResponse> getTopHeadlines(String category, String country, String sources, String q, int pagesize, int page) {
        return networkModule.getNewsApi().getTopHeadlines(category, country, sources, q, pagesize, page);
    }

}
