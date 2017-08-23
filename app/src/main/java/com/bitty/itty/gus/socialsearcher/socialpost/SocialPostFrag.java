package com.bitty.itty.gus.socialsearcher.socialpost;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bitty.itty.gus.socialsearcher.R;
import com.bitty.itty.gus.socialsearcher.data.SocialPost;
import com.bitty.itty.gus.socialsearcher.util.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gus on 24/8/17.
 */

public class SocialPostFrag extends Fragment implements SocialPostContract.View, ListView.OnItemClickListener {

    private SocialPostAdapter mListAdapter;

    private SocialPostContract.UserActionsListener mActionsListener;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Bind(R.id.feed_recycler_view)
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


        //TODO UNCOMMENT
        //mActionsListener = new SocialPostPresenter(Injection.provideFeedRepository(), this);

        mListAdapter = new SocialPostAdapter(new ArrayList<SocialPost>(0), mItemListener);
        recyclerView.setAdapter(mListAdapter);
        recyclerView.setHasFixedSize(true);

        int numColumns = getResources().getInteger(R.integer.num_feed_columns);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numColumns));

        // Pull-to-refresh
        swipeRefresh.setColorSchemeColors(App.get().getSwipeRefreshColorScheme(getActivity()));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mActionsListener.searchSocialPosts(true);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.searchSocialPosts(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showToastMessage(String message) {

    }

    @Override
    public void showSocialPostsUI(List<SocialPost> posts) {

    }

    @Override
    public void showAddSocialPostUI() {

    }

    @Override
    public void showSocialPostDetailUI(String postId) {

    }

    /**
     * Listener for clicks on feeds in the RecyclerView.
     */
    SocialPostAdapter.SocialPostListener mItemListener = new SocialPostAdapter.SocialPostListener() {
        @Override
        public void onSocialPostClick(SocialPost clickedPost) {
            showToastMessage("Under construction...");
            mActionsListener.openSocialPostDetail(clickedPost);
        }

    };
}