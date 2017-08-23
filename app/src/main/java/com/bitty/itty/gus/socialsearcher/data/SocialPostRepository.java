package com.bitty.itty.gus.socialsearcher.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Main entry point for accessing posts data.
 */
public interface SocialPostRepository {

    interface loadSocialPostsCallback {

        void onPostsLoaded(List<SocialPost> posts);

        void onPostsCanceled();
    }

    interface LoadSocialPostCallback {

        void onPostLoaded(SocialPost post);

        void onPostCanceled();
    }


    void loadSocialPosts(@NonNull loadSocialPostsCallback callback);

    void loadSocialPost(@NonNull String postId, @NonNull LoadSocialPostCallback callback);

    void saveSocialPost(@NonNull SocialPost post);

    void refreshData();
}
