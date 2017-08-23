package com.bitty.itty.gus.socialsearcher.socialpost;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitty.itty.gus.socialsearcher.R;
import com.bitty.itty.gus.socialsearcher.data.SocialPost;
import com.bitty.itty.gus.socialsearcher.util.App;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Gus on 24/8/17.
 */

public class SocialPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SocialPost> mSocialPosts;
    private SocialPostListener mItemListener;

    SocialPostAdapter(List<SocialPost> socialPosts, SocialPostListener itemListener) {
        setList(socialPosts);
        mItemListener = itemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View socialPostView = inflater.inflate(R.layout.socialpost_item, parent, false);

        if (viewType == EMPTY_VIEW_TYPE) {
            socialPostView = inflater.inflate(R.layout.no_results_view, parent, false);

            EmptyViewHolder evh = new EmptyViewHolder(socialPostView);
            return evh;
        }

        return new ViewHolder(socialPostView, mItemListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericViewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == EMPTY_VIEW_TYPE) {

        } else {
            SocialPost socialPost = mSocialPosts.get(position);

            ViewHolder viewHolder = (ViewHolder) genericViewHolder;
            viewHolder.title.setText(socialPost.getTitle());

            if (!TextUtils.isEmpty(socialPost.getDescription())) {
                //FlowTextHelper.tryFlowText(socialPost.getDescription(), viewHolder.imageHref, viewHolder.description, App.getDisplay());
                viewHolder.description.setText(socialPost.getDescription());
            }

            if (!TextUtils.isEmpty(socialPost.getImageHref())) {
                Picasso.with(App.get())
                        .load(socialPost.getImageHref())
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(viewHolder.imageHref);
            }

        }

    }

    void replaceData(List<SocialPost> socialPosts) {
        setList(socialPosts);
        notifyDataSetChanged();
    }

    private void setList(List<SocialPost> socialPosts) {
        mSocialPosts = checkNotNull(socialPosts);
    }

    @Override
    public int getItemCount() {
        return mSocialPosts.size() > 0 ? mSocialPosts.size() : 1;
    }


    private static final int EMPTY_VIEW_TYPE = 1010;

    @Override
    public int getItemViewType(int position) {
        if (mSocialPosts.size() == 0) {
            return EMPTY_VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    private SocialPost getItem(int position) {
        return mSocialPosts.get(position);
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View emptyView) {
            super(emptyView);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.socialpost_item_title)
        TextView title;

        @Bind(R.id.socialpost_item_description)
        TextView description;

        @Bind(R.id.socialpost_item_image_href)
        ImageView imageHref;

        private SocialPostListener mItemListener;

        ViewHolder(View socialPostView, SocialPostListener listener) {
            super(socialPostView);
            ButterKnife.bind(this, socialPostView);

            mItemListener = listener;
            socialPostView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            SocialPost socialPost = getItem(position);
            mItemListener.onSocialPostClick(socialPost);
        }
    }


    interface SocialPostListener {
        void onSocialPostClick(SocialPost clickedSocialPost);
    }
}
