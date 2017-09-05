package com.bitty.itty.gus.socialsearcher.data;

/**
 * Created by Gus on 22/8/17.
 */

/**
 * Defines an interface to the service API that is used by this application. All data request should
 * be piped through this interface.
 */
public interface SocialPostServiceApi {

    interface SocialPostServiceCallback<T> {
        void onServiceLoaded(T post);

        void onServiceCanceled();
    }

    void loadSocialPosts(String searchTerm, String language, String resultType, long sinceId, long maxId, SocialPostServiceCallback<TwitterSearchResult> callback);

    //void loadMoreSocialPosts(String nextResults, SocialPostServiceCallback<TwitterSearchResult> callback);

    void loadSocialPost(String postId, SocialPostServiceCallback<TwitterPost> callback);

    void saveSocialPost(TwitterPost post);

}