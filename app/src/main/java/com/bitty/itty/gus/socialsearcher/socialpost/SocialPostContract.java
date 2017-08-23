package com.bitty.itty.gus.socialsearcher.socialpost;

import android.support.annotation.NonNull;

import com.bitty.itty.gus.socialsearcher.data.SocialPost;

import java.util.List;

/**
 * Created by Gus on 24/8/17.
 */

public interface SocialPostContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showToastMessage(String message);

        void showSocialPostsUI(List<SocialPost> posts);

        void showAddSocialPostUI();

        void showSocialPostDetailUI(String postId);
    }

    interface UserActionsListener {

        void searchSocialPosts(boolean forceUpdate);

        void addSocialPost();

        void openSocialPostDetail(@NonNull SocialPost post);
    }

}
