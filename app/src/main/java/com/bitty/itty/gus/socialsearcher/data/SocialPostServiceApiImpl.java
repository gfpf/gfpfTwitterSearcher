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
import java.util.Arrays;
import java.util.HashMap;
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
    public void loadSocialPosts(String searchTerm, String language, String resultType, long sinceId, long maxId, SocialPostServiceCallback<TwitterSearchResult> callback) {
        if (App.get().hasInternetService()) {
            new AsyncTask<Object, Void, Void>() {

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected Void doInBackground(Object... searchTerms) {
                    if (searchTerms.length > 0) {
                        String searchTerm = searchTerms[0].toString();
                        String language = searchTerms[1].toString();
                        String resultType = searchTerms[2].toString();
                        long sinceId = (long) searchTerms[3];
                        long maxId = (long) searchTerms[4];

                        SocialPostServiceCallback<TwitterSearchResult> callback = (SocialPostServiceCallback<TwitterSearchResult>) searchTerms[5];

                        /*if (ServiceManager.TWITTER_BEARER_TOKEN == null) {
                            loadTwitterBearerToken(searchTerm, language, resultType, sinceId, maxId, callback);
                        } else {
                            searchSocialPostsByTerm(searchTerm, language, resultType, sinceId, maxId, callback);
                        }*/

                        loadTwitterBearerToken(searchTerm, language, resultType, sinceId, maxId, callback);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);
                }
            }.execute(searchTerm, language, resultType, sinceId, maxId, callback);

        } else {
            App.get().suggestEnableInternet();
            callback.onServiceCanceled();
        }
    }

    private void searchSocialPostsByTerm(final String searchTerm, final String language, final String resultType, final long sinceId, final long maxId, final SocialPostServiceCallback<TwitterSearchResult> callback) {
        try {
            String encodedSearchTerm = URLEncoder.encode(searchTerm, "UTF-8");

            int lastPartSplit = 1;
            String languageSymbol = Arrays.asList(language.split("-")).get(lastPartSplit).trim();
            String encodedLanguageSymbol = URLEncoder.encode(languageSymbol.toLowerCase(), "UTF-8");
            String encodedResultType = URLEncoder.encode(resultType.toLowerCase(), "UTF-8");
            int count = 15;

            //TODO COMPLETE THE URL WITH MAX SINCE ID

            String serviceURL = String.format(ServiceManager.TWITTER_SEARCH_API_URL, encodedSearchTerm, encodedLanguageSymbol, encodedResultType, sinceId, maxId, count);

            //String encodedURL = Base64.encodeToString(serviceURL.getBytes(), Base64.NO_WRAP);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, serviceURL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JsonParser parser = new JsonParser();
                    JsonObject data = parser.parse(String.valueOf(response)).getAsJsonObject();

                    TwitterSearchResult results = new Gson().fromJson(data, TwitterSearchResult.class);
                    callback.onServiceLoaded(results);
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

    private void loadTwitterBearerToken(final String searchTerm, final String language, final String resultType, final long sinceId, final long maxId, final SocialPostServiceCallback<TwitterSearchResult> callback) {
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
                            searchSocialPostsByTerm(searchTerm, language, resultType, sinceId, maxId, callback);
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