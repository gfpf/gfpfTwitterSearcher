package com.bitty.itty.gus.socialsearcher.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Gus on 22/8/17.
 */

public class InMemorySocialPostRepository implements SocialPostRepository {

    private final SocialPostServiceApi mSocialPostServiceApi;

    /**
     * This method has reduced visibility for testing and is only visible to tests in the same package.
     */
    @VisibleForTesting
    private List<TwitterPost> mCachedPosts;

    public InMemorySocialPostRepository(@NonNull SocialPostServiceApi postServiceApi) {
        mSocialPostServiceApi = checkNotNull(postServiceApi);
    }


    @Override
    public void loadSocialPosts(@NonNull String searchTerm, @NonNull String language, @NonNull String resultType, @NonNull final LoadSocialPostsCallback callback) {
        checkNotNull(callback);

        // Load from API only if needed.
        if (mCachedPosts == null) {
            mSocialPostServiceApi.loadSocialPosts(searchTerm, language, resultType, new SocialPostServiceApi.SocialPostServiceCallback<List<TwitterPost>>() {
                @Override
                public void onServiceLoaded(List<TwitterPost> posts) {
                    mCachedPosts = ImmutableList.copyOf(posts);
                    callback.onPostsLoaded(mCachedPosts);
                }

                @Override
                public void onServiceCanceled() {
                    callback.onPostsCanceled();
                }
            });
        } else callback.onPostsLoaded(mCachedPosts);
    }

    @Override
    public void loadSocialPost(@NonNull String postId, @NonNull final LoadSocialPostCallback callback) {
        checkNotNull(postId);
        checkNotNull(callback);

        // Load Post item matching the id always directly from the API.
        mSocialPostServiceApi.loadSocialPost(postId, new SocialPostServiceApi.SocialPostServiceCallback<TwitterPost>() {
            @Override
            public void onServiceLoaded(TwitterPost post) {
                callback.onPostLoaded(post);
            }

            @Override
            public void onServiceCanceled() {
                callback.onPostCanceled();
            }

        });
    }

    @Override
    public void saveSocialPost(@NonNull TwitterPost post) {
        checkNotNull(post);

        mSocialPostServiceApi.saveSocialPost(post);
        refreshData();
    }

    @Override
    public void refreshData() {
        mCachedPosts = null;
    }
}
