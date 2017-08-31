package com.bitty.itty.gus.socialsearcher.data;

/**
 * Created by Gus on 22/8/17.
 */

import java.util.List;

/**
 * Defines an interface to the service API that is used by this application. All data request should
 * be piped through this interface.
 */
public interface SocialPostServiceApi {

    interface SocialPostServiceCallback<T> {
        void onServiceLoaded(T post);
        void onServiceCanceled();
    }

    void loadSocialPosts(String searchTerm, SocialPostServiceCallback<List<TwitterPost>> callback);

    void loadSocialPost(String postId, SocialPostServiceCallback<TwitterPost> callback);

    void saveSocialPost(TwitterPost post);

}