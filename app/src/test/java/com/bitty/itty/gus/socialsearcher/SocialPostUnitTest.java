package com.bitty.itty.gus.socialsearcher;

import com.bitty.itty.gus.socialsearcher.data.SocialPost;
import com.bitty.itty.gus.socialsearcher.data.SocialPostRepository;
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
public class SocialPostUnitTest {

    private static ArrayList<SocialPost> POSTS = Lists.newArrayList(
            new SocialPost("Title0", "Description0"),
            new SocialPost("Title1", "Description1"),
            new SocialPost("Title2", "Description2"),
            new SocialPost("Title3", "Description3"),
            new SocialPost("Title4", "Description4"),
            new SocialPost("Title5", "Description5"),
            new SocialPost("Title6", "Description6"),
            new SocialPost("Title7", "Description7"),
            new SocialPost("Title8", "Description8"),
            new SocialPost("Title9", "Description9"));

    private static List<SocialPost> EMPTY_POSTS = new ArrayList<>(0);

    @Mock
    private SocialPostRepository mSocialPostRepository;

    @Mock
    private SocialPostContract.View mPostView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<SocialPostRepository.loadSocialPostsCallback> mLoadPostsCallbackCaptor;

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
        mSocialPostPresenter.searchSocialPosts(true);

        // Callback is captured and invoked with stubbed posts
        verify(mSocialPostRepository).loadSocialPosts(mLoadPostsCallbackCaptor.capture());
        mLoadPostsCallbackCaptor.getValue().onPostsLoaded(POSTS);

        // Then progress indicator is hidden and posts are shown in UI
        InOrder inOrder = Mockito.inOrder(mPostView);
        inOrder.verify(mPostView).setProgressIndicator(true);
        inOrder.verify(mPostView).setProgressIndicator(false);
        verify(mPostView).showSocialPostsUI(POSTS);
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
        SocialPost requestedPost = new SocialPost("Details Requested", "For this post");

        // When open post details is requested
        mSocialPostPresenter.openSocialPostDetail(requestedPost);

        // Then post detail UI is shown
        verify(mPostView).showSocialPostDetailUI(any(String.class));
    }

}