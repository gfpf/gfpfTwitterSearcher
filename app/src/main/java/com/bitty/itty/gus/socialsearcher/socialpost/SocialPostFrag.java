package com.bitty.itty.gus.socialsearcher.socialpost;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.bitty.itty.gus.socialsearcher.Injection;
import com.bitty.itty.gus.socialsearcher.R;
import com.bitty.itty.gus.socialsearcher.data.TwitterPost;
import com.bitty.itty.gus.socialsearcher.data.TwitterSearchResult;
import com.bitty.itty.gus.socialsearcher.util.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gus on 24/8/17.
 */

public class SocialPostFrag extends Fragment implements SocialPostContract.View, View.OnClickListener {

    private SocialPostAdapter mListAdapter;

    private SocialPostContract.UserActionsListener mActionsListener;

    @Bind(R.id.search_view)
    SearchView searchView;

    @Bind(R.id.btn_filter)
    ImageView btnFilter;

    @Bind(R.id.lay_filters)
    View layFilters;

    @Bind(R.id.spn_lang)
    Spinner spnLang;

    @Bind(R.id.spn_result_type)
    Spinner spnResultType;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private GridLayoutManager layoutManager;

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

        searchView.setOnQueryTextListener(mQueryListener);
        btnFilter.setOnClickListener(this);

        mListAdapter = new SocialPostAdapter(new ArrayList<TwitterPost>(0), mItemListener);
        recyclerView.setAdapter(mListAdapter);
        recyclerView.setHasFixedSize(true);

        int numColumns = getResources().getInteger(R.integer.num_post_columns);
        layoutManager = new GridLayoutManager(getActivity(), numColumns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(mScrollListener);

        //Pull-to-refresh
        swipeRefresh.setColorSchemeColors(App.get().getSwipeRefreshColorScheme(getActivity()));
        swipeRefresh.setOnRefreshListener(mSwipeRefreshListener);

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
                App.hideKeyboard(getActivity());
            }
        });
    }

    @Override
    public void showErrorMessage(String message) {
        isLoading = false;
        App.showToast(getActivity(), message);
    }

    private TwitterSearchResult mSearchResult;

    @Override
    public void showSocialPostsUI(TwitterSearchResult searchResult) {
        isLoading = false;

        orderResult(searchResult);

        if (isOnScrollResults) {
            mListAdapter.appendData(searchResult.getStatuses());
        } else {
            mListAdapter.replaceData(searchResult.getStatuses());
        }
        mSearchResult = searchResult;
    }

    private void orderResult(TwitterSearchResult searchResult) {
        //ArrayList<TwitterPost> results = new ArrayList<>(searchResult.getStatuses());

        Collections.sort(searchResult.getStatuses(), new Comparator<TwitterPost>() {
            public int compare(TwitterPost obj1, TwitterPost obj2) {
                // ## Ascending order
                return Long.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare long values
                // return Integer.valueOf(obj1.empId).compareTo(obj2.empId); // To compare integer values

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
            }
        });

    }

    @Override
    public void showAddSocialPostUI() {
    }

    @Override
    public void showSocialPostDetailUI(long postId) {
    }


    /**
     * Listener for pull-to-refresh on swipe refresh view.
     */
    SwipeRefreshLayout.OnRefreshListener mSwipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            isOnScrollResults = false;
            mActionsListener.searchSocialPosts(searchView.getQuery().toString(), spnLang.getSelectedItem().toString(), spnResultType.getSelectedItem().toString(), 0, 0, true);
        }
    };

    private boolean isLoading = false;
    private boolean isOnScrollResults = false;


    private long biggestId = 0;
    /**
     * Listener for scroll on recycler view.
     */
    RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //check for scroll down
            if (dy > 0) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                        if (mSearchResult.getStatuses() != null && mSearchResult.getStatuses().size() > 0) {
                            isLoading = true;

                            int lastPostIndex = mSearchResult.getStatuses().size() - 1;
                            TwitterPost lastPost = mSearchResult.getStatuses().get(lastPostIndex);

                            TwitterPost firstPost = mSearchResult.getStatuses().get(0);
                            isOnScrollResults = true;

                            //Applications which use both the max_id and since_id parameters correctly minimize
                            // the amount of redundant data they fetch and process, while retaining the ability
                            // to iterate over the entire available contents of a timeline.
                            mActionsListener.searchSocialPosts(searchView.getQuery().toString(), spnLang.getSelectedItem().toString(), spnResultType.getSelectedItem().toString(), 0, firstPost.getId() - 1, true);
                        }
                    }

                }
            }
        }
    };

    /**
     * Listener for click on search button in the keyboard.
     */
    SearchView.OnQueryTextListener mQueryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            isOnScrollResults = false;
            mActionsListener.searchSocialPosts(query, spnLang.getSelectedItem().toString(), spnResultType.getSelectedItem().toString(), 0, 0, true);
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
            showErrorMessage("Under construction...");
            mActionsListener.openSocialPostDetail(clickedPost);
        }

    };

    /**
     * Listener for clicks on views in the fragment.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filter:
                if (layFilters.getVisibility() == View.GONE) {
                    layFilters.setVisibility(View.VISIBLE);
                } else {
                    layFilters.setVisibility(View.GONE);
                }
                break;
        }
    }
}