package com.bitty.itty.gus.socialsearcher.data;

import androidx.annotation.NonNull;

/**
 * Main entry point for accessing posts data.
 */
public interface SocialPostRepository {

    interface LoadSocialPostsCallback {

        void onPostsLoaded(TwitterSearchResult searchResult);

        void onPostsCanceled();
    }

    interface LoadSocialPostCallback {

        void onPostLoaded(TwitterPost post);

        void onPostCanceled();
    }


    void loadSocialPosts(@NonNull String searchTerm, @NonNull String language, @NonNull String resultType, @NonNull long sinceId, @NonNull long maxId, @NonNull LoadSocialPostsCallback callback);

    //void loadMoreSocialPosts(@NonNull String nextResults, @NonNull LoadSocialPostsCallback callback);

    void loadSocialPost(@NonNull String postId, @NonNull LoadSocialPostCallback callback);

    void saveSocialPost(@NonNull TwitterPost post);

    void refreshData();
}
