package com.bitty.itty.gus.socialsearcher.data;

import android.support.v4.util.ArrayMap;

/**
 * This is the fake endpoint for the data source.
 */
public class FakeSocialPostServiceApiEndpoint {

    static {
        FAKE_POST_SERVICE_DATA = new ArrayMap(2);
        addSocialPost("Fake Post1");
        addSocialPost("Fake Post2");
    }

    private final static ArrayMap<String, SocialPost> FAKE_POST_SERVICE_DATA;

    private static void addSocialPost(String text) {
        SocialPost post = new SocialPost(text);
        FAKE_POST_SERVICE_DATA.put(post.getUUID(), post);
    }

    /**
     * @return the Posts to show when starting the app.
     */
    public static ArrayMap<String, SocialPost> loadPersistedPosts() {
        return FAKE_POST_SERVICE_DATA;
    }
}
