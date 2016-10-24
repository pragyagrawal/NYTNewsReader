package com.binarybricks.pragya.nytnewsreader.interfaces;

import com.binarybricks.pragya.nytnewsreader.model.ArticleSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by PRAGYA on 10/22/2016.
 */

public interface ArticleSearchAPIInterface {

    @GET("articlesearch.json")
    Call<ArticleSearchResponse> searchNewsArticles(@Query("q") String query,@Query("begin_date") String beginDate, @Query("sort") String sortOrder,
                                                   @Query("fq") String fq, @Query("page") Integer page, @Query("api_key") String apiKey);
}
