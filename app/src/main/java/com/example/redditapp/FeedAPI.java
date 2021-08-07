/*
 *
 *
 *
 */
package com.example.redditapp;

import com.example.redditapp.model.Feed;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedAPI {
    String REDDIT_BASE_URL = "https://www.reddit.com/r/";

    @GET("ProgrammerHumor/.rss")
    Call<Feed> getFeed();

}
