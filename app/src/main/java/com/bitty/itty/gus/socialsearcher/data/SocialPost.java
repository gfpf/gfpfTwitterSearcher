package com.bitty.itty.gus.socialsearcher.data;

import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by Gus on 24/8/17.
 */

public class SocialPost {

    private final String mId;

    @Nullable
    @SerializedName("title")
    private String mTitle;
    @Nullable
    @SerializedName("description")
    private String mDescription;
    @Nullable
    @SerializedName("imageHref")
    private String mImageHref;

    public SocialPost(@Nullable String title, @Nullable String description) {
        this(title, description, null);
    }

    public SocialPost(@Nullable String title, @Nullable String description, @Nullable String imageHref) {
        mId = UUID.randomUUID().toString();
        mTitle = title;
        mDescription = description;
        mImageHref = imageHref;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getImageHref() {
        return mImageHref;
    }

    public void setImageHref(String mImageHref) {
        this.mImageHref = mImageHref;
    }

    public boolean isEmpty() {
        return (mTitle == null || "".equals(mTitle)) &&
                (mDescription == null || "".equals(mDescription));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SocialPost socialPost = (SocialPost) o;
        return Objects.equal(mId, socialPost.mId) &&
                Objects.equal(mTitle, socialPost.mTitle) &&
                Objects.equal(mDescription, socialPost.mDescription) &&
                Objects.equal(mImageHref, socialPost.mImageHref);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription, mImageHref);
    }

}
