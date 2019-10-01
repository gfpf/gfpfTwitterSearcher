package com.bitty.itty.gus.socialsearcher.data;

import android.support.annotation.VisibleForTesting;
import android.support.v4.util.ArrayMap;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Fake implementation of {@link SocialPostServiceApi} to inject a fake service in a hermetic test.
 */
public class FakeSocialPostServiceApiImpl implements SocialPostServiceApi {

    private static final ArrayMap<String, TwitterPost> POST_SERVICE_DATA = FakeSocialPostServiceApiEndpoint.loadPersistedPosts();
    
    @Override
    public void loadSocialPosts(String searchTerm, String language, String resultType, long sinceId, long maxId, SocialPostServiceCallback<TwitterSearchResult> callback) {

        //Adds FakeSocialPost as a return of the fake endpoint
        TwitterSearchResult result = new TwitterSearchResult();
        result.setStatuses(Lists.newArrayList(POST_SERVICE_DATA.values()));

        callback.onServiceLoaded(result);
    }

    @Override
    public void loadSocialPost(String postId, SocialPostServiceCallback<TwitterPost> callback) {
        TwitterPost post = POST_SERVICE_DATA.get(postId);
        callback.onServiceLoaded(post);
    }

    @Override
    public void saveSocialPost(TwitterPost post) {
        POST_SERVICE_DATA.put(post.getUUID(), post);
    }

    @VisibleForTesting
    public static void addPosts(TwitterPost... posts) {
        for (TwitterPost post : posts) {
            POST_SERVICE_DATA.put(post.getUUID(), post);
        }
    }
}