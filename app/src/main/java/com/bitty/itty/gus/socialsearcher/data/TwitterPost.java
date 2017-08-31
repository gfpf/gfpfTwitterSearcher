package com.bitty.itty.gus.socialsearcher.data;

import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by Gus on 24/8/17.
 */

public class TwitterPost {

    /*@Nullable
    @SerializedName("mText")
    private String mTitle;
    @Nullable
    @SerializedName("description")
    private String mDescription;
    @Nullable
    @SerializedName("imageHref")
    private String mImageHref;*/

    private final String mUUID;

    @SerializedName("created_at")
    private String dateCreated;

    @SerializedName("id")
    private long id;

    @SerializedName("id_str")
    private String idStr;

    @Nullable
    @SerializedName("text")
    private String mText;

    @SerializedName("source")
    private String source;

    @SerializedName("truncated")
    private Boolean isTruncated;

    @SerializedName("in_reply_to_status_id")
    private long inReplyToStatusId;

    @SerializedName("in_reply_to_status_id_str")
    private String inReplyToStatusIdStr;

    @SerializedName("in_reply_to_user_id")
    private long inReplyToUserId;

    @SerializedName("in_reply_to_user_id_str")
    private String inReplyToUserIdStr;

    @SerializedName("in_reply_to_screen_name")
    private String inReplyToScreenName;

    @SerializedName("user")
    private TwitterUser twitterUser;

    public TwitterPost(@Nullable String text) {
        mUUID = UUID.randomUUID().toString();
        mText = text;
    }

    public String getUUID() {
        return mUUID;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getIsTruncated() {
        return isTruncated;
    }

    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public void setInReplyToStatusId(long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public String getInReplyToStatusIdStr() {
        return inReplyToStatusIdStr;
    }

    public void setInReplyToStatusIdStr(String inReplyToStatusIdStr) {
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
    }

    public long getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public String getInReplyToUserIdStr() {
        return inReplyToUserIdStr;
    }

    public void setInReplyToUserIdStr(String inReplyToUserIdStr) {
        this.inReplyToUserIdStr = inReplyToUserIdStr;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public TwitterUser getTwitterUser() {
        return twitterUser;
    }

    public void setTwitterUser(TwitterUser user) {
        twitterUser = user;
    }

    @Override
    public String  toString(){
        return getText();
    }






    public boolean isEmpty() {
        return (mText == null || "".equals(mText));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitterPost socialPost = (TwitterPost) o;
        return Objects.equal(mUUID, socialPost.mUUID) &&
                Objects.equal(mText, socialPost.mText);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mUUID, mText);
    }


    /*public String getTitle() {
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

        TwitterPost socialPost = (TwitterPost) o;
        return Objects.equal(mUUID, socialPost.mUUID) &&
                Objects.equal(mTitle, socialPost.mTitle) &&
                Objects.equal(mDescription, socialPost.mDescription) &&
                Objects.equal(mImageHref, socialPost.mImageHref);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mUUID, mTitle, mDescription, mImageHref);
    }*/

}
