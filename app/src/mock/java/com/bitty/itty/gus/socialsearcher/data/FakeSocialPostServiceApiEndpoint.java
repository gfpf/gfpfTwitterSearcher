package com.bitty.itty.gus.socialsearcher.data;


import androidx.collection.ArrayMap;

/**
 * This is the fake endpoint for the data source.
 */
public class FakeSocialPostServiceApiEndpoint {

    static {
        FAKE_POST_SERVICE_DATA = new ArrayMap(2);
        addSocialPost("Fake Post1");
        addSocialPost("Fake Post2");
    }

    private final static ArrayMap FAKE_POST_SERVICE_DATA;

    private static void addSocialPost(String text) {
        TwitterPost post = new TwitterPost(text);
        FAKE_POST_SERVICE_DATA.put(post.getUUID(), post);
    }

    /**
     * @return the Posts to show when starting the app.
     */
    public static ArrayMap<String, TwitterPost> loadPersistedPosts() {
        return FAKE_POST_SERVICE_DATA;
    }
}
