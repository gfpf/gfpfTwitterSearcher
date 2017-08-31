package com.bitty.itty.gus.socialsearcher.data;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitty.itty.gus.socialsearcher.util.App;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Gus on 24/8/17.
 */

public class SocialPostServiceApiImpl extends ServiceManager implements SocialPostServiceApi {

    private static SocialPostServiceApiImpl instance = null;

    public static SocialPostServiceApiImpl get() {
        if (instance == null) {
            instance = new SocialPostServiceApiImpl();
        }
        return instance;
    }

    @Override
    public void loadSocialPosts(final String searchTerm, final SocialPostServiceCallback<List<TwitterPost>> callback) {
        if (App.get().hasInternetService()) {
            new AsyncTask<String, Void, Void>() {

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected Void doInBackground(String... searchTerms) {
                    if (searchTerms.length > 0) {
                        if (ServiceManager.TWITTER_BEARER_TOKEN == null) {
                            loadTwitterBearerToken(searchTerm, callback);
                        } else {
                            searchSocialPostsByTerm(searchTerms[0], callback);
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);
                }
            }.execute(searchTerm);

        } else {
            App.get().suggestEnableInternet();
            callback.onServiceCanceled();
        }
    }

    private void searchSocialPostsByTerm(String searchTerm, final SocialPostServiceCallback<List<TwitterPost>> callback) {
        try {
            String encodedSearchTerm = URLEncoder.encode(searchTerm, "UTF-8");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ServiceManager.TWITTER_SEARCH_API_URL + encodedSearchTerm, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JsonParser parser = new JsonParser();
                    JsonObject data = parser.parse(String.valueOf(response)).getAsJsonObject();

                    TwitterSearchResult results = new Gson().fromJson(data, TwitterSearchResult.class);
                    callback.onServiceLoaded(results.getStatuses());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onServiceCanceled();
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    // Build a normal HTTPS request and include an Authorization header with the value of Bearer
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + ServiceManager.TWITTER_BEARER_TOKEN.getAccessToken());
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            ServiceManager.get().getRequestQueue().add(jsonObjectRequest);

        } catch (UnsupportedEncodingException | IllegalStateException ex) {
        }
    }

    private void loadTwitterBearerToken(final String searchTerm, final SocialPostServiceCallback<List<TwitterPost>> callback) {
        try {
            // Step 1: Encode consumer key and secret
            String urlApiKey = URLEncoder.encode(ServiceManager.TWITTER_CONSUMER_KEY, "UTF-8");
            String urlApiSecret = URLEncoder.encode(ServiceManager.TWITTER_CONSUMER_SECRET, "UTF-8");

            // Concatenate the encoded consumer key, a colon character, and the encoded consumer secret
            String combined = TextUtils.concat(urlApiKey, ":", urlApiSecret).toString();
            //String combined = urlApiKey + ":" + urlApiSecret;

            // Base64 encode the string
            final String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

            // Step 2: Obtain a bearer token
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ServiceManager.TWITTER_TOKEN_URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JsonParser parser = new JsonParser();
                    JsonObject data = parser.parse(String.valueOf(response)).getAsJsonObject();

                    ServiceManager.TWITTER_BEARER_TOKEN = new Gson().fromJson(data, TwitterBearerToken.class);

                    if (ServiceManager.TWITTER_BEARER_TOKEN != null) {
                        if (ServiceManager.TWITTER_BEARER_TOKEN.getTokenType().equals(TwitterBearerToken.BEARER_TOKEN_TYPE)) {

                            // Step 3: Authenticate API request with bearer token and search by term
                            searchSocialPostsByTerm(searchTerm, callback);
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onServiceCanceled();
                }
            }) {

                //Override this method to setup the 'grant_type' or pass it as a request body when creating JsonObjectRequest
                @Override
                public byte[] getBody() {
                    return ("grant_type=client_credentials").getBytes();
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + base64Encoded);
                    headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                    return headers;
                }

            };

            ServiceManager.get().getRequestQueue().add(jsonObjectRequest);

        } catch (UnsupportedEncodingException | IllegalStateException ex) {
        }
    }

    @Override
    public void loadSocialPost(String postId, SocialPostServiceCallback<TwitterPost> callback) {

    }

    @Override
    public void saveSocialPost(TwitterPost post) {

    }
}