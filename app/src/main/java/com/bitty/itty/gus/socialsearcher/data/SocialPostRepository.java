package com.bitty.itty.gus.socialsearcher.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Main entry point for accessing posts data.
 */
public interface SocialPostRepository {

    interface LoadSocialPostsCallback {

        void onPostsLoaded(List<TwitterPost> posts);

        void onPostsCanceled();
    }

    interface LoadSocialPostCallback {

        void onPostLoaded(TwitterPost post);

        void onPostCanceled();
    }


    void loadSocialPosts(@NonNull String searchTerm, @NonNull String language, @NonNull String resultType, @NonNull LoadSocialPostsCallback callback);

    void loadSocialPost(@NonNull String postId, @NonNull LoadSocialPostCallback callback);

    void saveSocialPost(@NonNull TwitterPost post);

    void refreshData();
}
