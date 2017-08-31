package com.bitty.itty.gus.socialsearcher.data;

import android.support.annotation.VisibleForTesting;
import android.support.v4.util.ArrayMap;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Fake implementation of {@link SocialPostServiceApi} to inject a fake service in a hermetic test.
 */
public class FakeSocialPostServiceApiImpl implements SocialPostServiceApi {

    private static final ArrayMap<String, SocialPost> POST_SERVICE_DATA = FakeSocialPostServiceApiEndpoint.loadPersistedPosts();

    @Override
    public void loadSocialPosts(String searchTerm, SocialPostServiceCallback<List<SocialPost>> callback) {
        callback.onServiceLoaded(Lists.newArrayList(POST_SERVICE_DATA.values()));
    }

    @Override
    public void loadSocialPost(String postId, SocialPostServiceCallback<SocialPost> callback) {
        SocialPost post = POST_SERVICE_DATA.get(postId);
        callback.onServiceLoaded(post);
    }

    @Override
    public void saveSocialPost(SocialPost post) {
        POST_SERVICE_DATA.put(post.getUUID(), post);
    }

    @VisibleForTesting
    public static void addPosts(SocialPost... posts) {
        for (SocialPost post : posts) {
            POST_SERVICE_DATA.put(post.getUUID(), post);
        }
    }
}