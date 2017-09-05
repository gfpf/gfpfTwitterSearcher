package com.bitty.itty.gus.socialsearcher.data;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bitty.itty.gus.socialsearcher.util.App;

import org.json.JSONObject;


/**
 * Created by Gus on 24/8/17.
 */

public class ServiceManager {
    public static final String REASON_NOT_FOUND = "notFound";

    final static String TWITTER_CONSUMER_KEY = "KVmPnYojyT2Or3Apo4ZUDV4pH";
    final static String TWITTER_CONSUMER_SECRET = "NafDG9ETsSAPbrE2T8CetK37jHKgGula0W7ghJR4btcTTcJH3P";

    final static String TWITTER_TOKEN_URL = "https://api.twitter.com/oauth2/token";
    final static String TWITTER_SEARCH_API_URL = "https://api.twitter.com/1.1/search/tweets.json?" +
            "q=%s&" +
            "lang=%s&" +
            "result_type=%s&" +
            "since_id=%s&" +
            "max_id=%s&" +
            "count=%d&" +
            "include_entities=1";
    //final static String TWITTER_SEARCH_API_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";

    static TwitterBearerToken TWITTER_BEARER_TOKEN;

    private static ServiceManager instance;
    private RequestQueue mRequestQueue;

    ServiceManager() {

    }

    static ServiceManager get() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // Instantiate the RequestQueue.
            mRequestQueue = Volley.newRequestQueue(App.get());
        }
        return mRequestQueue;
    }

    JsonObjectRequest getRequest(String url, Response.Listener<JSONObject> respCallback, Response.ErrorListener respErrorCallback) {
        //url = API_URL + url;
        return new JsonObjectRequest(Request.Method.GET, url, null, respCallback, respErrorCallback);
    }
}
