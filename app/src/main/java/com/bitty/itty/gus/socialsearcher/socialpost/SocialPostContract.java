package com.bitty.itty.gus.socialsearcher.socialpost;

import android.support.annotation.NonNull;

import com.bitty.itty.gus.socialsearcher.data.TwitterPost;

import java.util.List;

/**
 * Created by Gus on 24/8/17.
 */

public interface SocialPostContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showToastMessage(String message);

        void showSocialPostsUI(List<TwitterPost> posts);

        void showAddSocialPostUI();

        void showSocialPostDetailUI(long postId);
    }

    interface UserActionsListener {

        void searchSocialPosts(String searchTerm, boolean forceUpdate);

        void addSocialPost();

        void openSocialPostDetail(@NonNull TwitterPost post);
    }

}
