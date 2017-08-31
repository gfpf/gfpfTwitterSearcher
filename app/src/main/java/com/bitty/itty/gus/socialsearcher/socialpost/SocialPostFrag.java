package com.bitty.itty.gus.socialsearcher.socialpost;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.bitty.itty.gus.socialsearcher.Injection;
import com.bitty.itty.gus.socialsearcher.R;
import com.bitty.itty.gus.socialsearcher.data.TwitterPost;
import com.bitty.itty.gus.socialsearcher.util.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gus on 24/8/17.
 */

public class SocialPostFrag extends Fragment implements SocialPostContract.View {

    private SocialPostAdapter mListAdapter;

    private SocialPostContract.UserActionsListener mActionsListener;

    @Bind(R.id.search_view)
    SearchView searchView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    public SocialPostFrag() {
        setHasOptionsMenu(true);
    }

    public static SocialPostFrag newInstance() {
        return new SocialPostFrag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.socialpost_frag, container, false);
        ButterKnife.bind(this, rootView);

        mActionsListener = new SocialPostPresenter(Injection.provideSocialPostRepository(), this);

        mListAdapter = new SocialPostAdapter(new ArrayList<TwitterPost>(0), mItemListener);
        recyclerView.setAdapter(mListAdapter);
        recyclerView.setHasFixedSize(true);

        int numColumns = getResources().getInteger(R.integer.num_post_columns);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numColumns));

        // Pull-to-refresh
        swipeRefresh.setColorSchemeColors(App.get().getSwipeRefreshColorScheme(getActivity()));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mActionsListener.searchSocialPosts(searchView.getQuery().toString(), true);
            }
        });

        searchView.setOnQueryTextListener(mQueryListener);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void setProgressIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }

        // Make sure setRefreshing() is called after the layout is done with everything else.
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(active);
            }
        });
    }

    @Override
    public void showToastMessage(String message) {
        App.showToast(getActivity(), message);
    }

    @Override
    public void showSocialPostsUI(List<TwitterPost> posts) {
        mListAdapter.replaceData(posts);
    }

    @Override
    public void showAddSocialPostUI() {

    }

    @Override
    public void showSocialPostDetailUI(long postId) {
    }

    SearchView.OnQueryTextListener mQueryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mActionsListener.searchSocialPosts(query, true);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    /**
     * Listener for clicks on posts in the RecyclerView.
     */
    SocialPostAdapter.SocialPostListener mItemListener = new SocialPostAdapter.SocialPostListener() {
        @Override
        public void onSocialPostClick(TwitterPost clickedPost) {
            showToastMessage("Under construction...");
            mActionsListener.openSocialPostDetail(clickedPost);
        }

    };
}