package com.bitty.itty.gus.socialsearcher.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gus on 25/8/17.
 */

public class TwitterSearchResult {

    @SerializedName("statuses")
    //private Searches statuses;
    private List<TwitterPost> statuses;

    @SerializedName("search_metadata")
    private TwitterSearchMetadata metadata;


    public List<TwitterPost> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<TwitterPost> statuses) {
        this.statuses = statuses;
    }

    public TwitterSearchMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(TwitterSearchMetadata metadata) {
        this.metadata = metadata;
    }
}
