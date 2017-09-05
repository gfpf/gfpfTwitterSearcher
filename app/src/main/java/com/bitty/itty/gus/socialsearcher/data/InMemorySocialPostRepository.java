package com.bitty.itty.gus.socialsearcher.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

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
    //private List<TwitterPost> mCachedPosts;
    private TwitterSearchResult mCachedResults;

    public InMemorySocialPostRepository(@NonNull SocialPostServiceApi postServiceApi) {
        mSocialPostServiceApi = checkNotNull(postServiceApi);
    }


    @Override
    public void loadSocialPosts(@NonNull String searchTerm, @NonNull String language, @NonNull String resultType, @NonNull long sinceId, @NonNull long maxId, @NonNull final LoadSocialPostsCallback callback) {
        checkNotNull(callback);

        // Load from API only if needed.
        if (mCachedResults == null) {
            mSocialPostServiceApi.loadSocialPosts(searchTerm, language, resultType, sinceId, maxId, new SocialPostServiceApi.SocialPostServiceCallback<TwitterSearchResult>() {
                @Override
                //public void onServiceLoaded(List<TwitterPost> posts) {
                public void onServiceLoaded(TwitterSearchResult searchResult) {
                    //mCachedPosts = ImmutableList.copyOf(searchResult.getStatuses());
                    mCachedResults = searchResult;
                    callback.onPostsLoaded(searchResult);
                }

                @Override
                public void onServiceCanceled() {
                    callback.onPostsCanceled();
                }
            });
        } else callback.onPostsLoaded(mCachedResults);
    }

    /*@Override
    public void loadMoreSocialPosts(@NonNull String nextResults, final @NonNull LoadSocialPostsCallback callback) {
        mSocialPostServiceApi.loadMoreSocialPosts(nextResults, new SocialPostServiceApi.SocialPostServiceCallback<TwitterSearchResult>() {
            @Override
            //public void onServiceLoaded(List<TwitterPost> posts) {
            public void onServiceLoaded(TwitterSearchResult searchResult) {
                //mCachedPosts = ImmutableList.copyOf(searchResult.getStatuses());
                mCachedResults = searchResult;
                callback.onPostsLoaded(searchResult);
            }

            @Override
            public void onServiceCanceled() {
                callback.onPostsCanceled();
            }
        });
    }*/

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
        mCachedResults = null;
    }
}
