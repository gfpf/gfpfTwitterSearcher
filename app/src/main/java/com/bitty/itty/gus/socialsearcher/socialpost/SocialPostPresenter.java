package com.bitty.itty.gus.socialsearcher.socialpost;

import android.support.annotation.NonNull;

import com.bitty.itty.gus.socialsearcher.R;
import com.bitty.itty.gus.socialsearcher.data.SocialPostRepository;
import com.bitty.itty.gus.socialsearcher.data.TwitterPost;
import com.bitty.itty.gus.socialsearcher.data.TwitterSearchResult;
import com.bitty.itty.gus.socialsearcher.util.App;
import com.bitty.itty.gus.socialsearcher.util.EspressoIdlingResource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Gus on 24/8/17.
 */

public class SocialPostPresenter implements SocialPostContract.UserActionsListener {

    private final SocialPostRepository mSocialPostRepository;
    private final SocialPostContract.View mSocialPostView;

    public SocialPostPresenter(@NonNull SocialPostRepository socialPostRepository, @NonNull SocialPostContract.View socialPostView) {
        mSocialPostRepository = checkNotNull(socialPostRepository, "socialPostRepository cannot be null");
        mSocialPostView = checkNotNull(socialPostView, "socialPostView cannot be null!");
    }

    @Override
    public void searchSocialPosts(String searchTerm, String language, String resultType, long sinceId, long maxId, boolean forceUpdate) {
        mSocialPostView.setProgressIndicator(true);

        if (forceUpdate) {
            mSocialPostRepository.refreshData();
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        //Get all socialPost items
        mSocialPostRepository.loadSocialPosts(searchTerm, language, resultType, sinceId, maxId, new SocialPostRepository.LoadSocialPostsCallback() {
            @Override
            public void onPostsLoaded(TwitterSearchResult results) {
                EspressoIdlingResource.decrement(); //Set app as idle.
                mSocialPostView.setProgressIndicator(false);
                mSocialPostView.showSocialPostsUI(results);
            }

            @Override
            public void onPostsCanceled() {
                EspressoIdlingResource.decrement(); // Set app as idle.
                mSocialPostView.setProgressIndicator(false);
                mSocialPostView.showErrorMessage(App.get().getString(R.string.connection_error));
            }
        });
    }

    /*@Override
    public void searchMoreSocialPosts(String nextResults) {
        mSocialPostView.setProgressIndicator(true);

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        //Get all socialPost items
        mSocialPostRepository.loadMoreSocialPosts(nextResults, new SocialPostRepository.LoadSocialPostsCallback() {
            @Override
            public void onPostsLoaded(TwitterSearchResult results) {
                EspressoIdlingResource.decrement(); //Set app as idle.
                mSocialPostView.setProgressIndicator(false);
                mSocialPostView.showSocialPostsUI(results);
            }

            @Override
            public void onPostsCanceled() {
                EspressoIdlingResource.decrement(); // Set app as idle.
                mSocialPostView.setProgressIndicator(false);
                mSocialPostView.showErrorMessage(App.get().getString(R.string.connection_error));
            }
        });
    }*/

    @Override
    public void addSocialPost() {
        mSocialPostView.showAddSocialPostUI();
    }

    @Override
    public void openSocialPostDetail(@NonNull TwitterPost post) {
        checkNotNull(post, "requestedFeed cannot be null!");
        mSocialPostView.showSocialPostDetailUI(post.getId());
    }
}
