package com.bitty.itty.gus.socialsearcher.socialpost;

import com.bitty.itty.gus.socialsearcher.data.TwitterPost;
import com.bitty.itty.gus.socialsearcher.data.TwitterSearchResult;

import androidx.annotation.NonNull;

/**
 * Created by Gus on 24/8/17.
 */

public interface SocialPostContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showErrorMessage(String message);

        void showSocialPostsUI(TwitterSearchResult searchResult);

        void showAddSocialPostUI();

        void showSocialPostDetailUI(long postId);
    }

    interface UserActionsListener {

        void searchSocialPosts(String searchTerm, String language, String resultType, long sinceId, long maxId, boolean forceUpdate);

        //void searchMoreSocialPosts(String nextResults);

        void addSocialPost();

        void openSocialPostDetail(@NonNull TwitterPost post);
    }

}
