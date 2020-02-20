package com.bitty.itty.gus.socialsearcher;

import com.bitty.itty.gus.socialsearcher.data.TwitterPost;
import com.bitty.itty.gus.socialsearcher.data.SocialPostRepository;
import com.bitty.itty.gus.socialsearcher.data.TwitterSearchResult;
import com.bitty.itty.gus.socialsearcher.socialpost.SocialPostContract;
import com.bitty.itty.gus.socialsearcher.socialpost.SocialPostPresenter;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TwitterPostUnitTest {

    private static ArrayList<TwitterPost> POSTS = Lists.newArrayList(
            new TwitterPost("Title0"),
            new TwitterPost("Title1"),
            new TwitterPost("Title2"),
            new TwitterPost("Title3"),
            new TwitterPost("Title4"),
            new TwitterPost("Title5"),
            new TwitterPost("Title6"),
            new TwitterPost("Title7"),
            new TwitterPost("Title8"),
            new TwitterPost("Title9"));

    private static List<TwitterPost> EMPTY_POSTS = new ArrayList<>(0);

    @Mock
    private SocialPostRepository mSocialPostRepository;

    @Mock
    private SocialPostContract.View mPostView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<SocialPostRepository.LoadSocialPostsCallback> mLoadPostsCallbackCaptor;

    private SocialPostPresenter mSocialPostPresenter;

    @Before
    public void setupNotesPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mSocialPostPresenter = new SocialPostPresenter(mSocialPostRepository, mPostView);
    }

    @Test
    public void loadPostFromRepositoryAndLoadIntoView() {
        // Given an initialized SocialPostPresenter with initialized posts When loading of Post is requested
        mSocialPostPresenter.searchSocialPosts(
                "Metallica"
                , "en-US"
                , "Recent"
                , 0
                , 0
                , true
        );

        // Callback is captured and invoked with stubbed posts
        verify(mSocialPostRepository).loadSocialPosts(
                "Metallica"
                , "en-US"
                , "Recent"
                , 0
                , 0
                , mLoadPostsCallbackCaptor.capture()
        );

        TwitterSearchResult searchResult = new TwitterSearchResult();
        searchResult.setStatuses(POSTS);
        mLoadPostsCallbackCaptor.getValue().onPostsLoaded(searchResult);

        // Then progress indicator is hidden and posts are shown in UI
        InOrder inOrder = Mockito.inOrder(mPostView);
        inOrder.verify(mPostView).setProgressIndicator(true);
        inOrder.verify(mPostView).setProgressIndicator(false);
        verify(mPostView).showSocialPostsUI(searchResult);
    }

    @Test
    public void clickOnFab_ShowsAddPostUI() {
        // When adding a new post
        mSocialPostPresenter.addSocialPost();

        // Then add post UI is shown
        verify(mPostView).showAddSocialPostUI();
    }

    @Test
    public void clickOnPost_ShowsDetailUI() {
        // Given a stubbed post
        TwitterPost requestedPost = new TwitterPost("Details Requested");

        // When open post details is requested
        mSocialPostPresenter.openSocialPostDetail(requestedPost);

        // Then post detail UI is shown
        verify(mPostView).showSocialPostDetailUI(any(Long.class));
    }

}