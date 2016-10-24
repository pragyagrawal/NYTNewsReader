package com.binarybricks.pragya.nytnewsreader.network;

import com.binarybricks.pragya.nytnewsreader.interfaces.ArticleSearchAPIInterface;
import com.binarybricks.pragya.nytnewsreader.model.ArticleSearchResponse;
import com.binarybricks.pragya.nytnewsreader.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by PRAGYA on 10/22/2016.
 * ArticleSearchAPI provides implementation of the Article search API
 */

public class ArticleSearchAPI {

    public static void getArticlesResponse(Callback<ArticleSearchResponse> callback, String query,String beginDate,String sortOrder,String filterQuery, Integer page){
        ArticleSearchAPIInterface articleSearchAPIInterface = RetroClient.getRetrofit().create(ArticleSearchAPIInterface.class);
        Call<ArticleSearchResponse> call = articleSearchAPIInterface.searchNewsArticles(query,beginDate, sortOrder,filterQuery, page, Constants.API_KEY);
        call.enqueue(callback);
    }
}
