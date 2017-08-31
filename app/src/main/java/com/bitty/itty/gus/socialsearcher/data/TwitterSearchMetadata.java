package com.bitty.itty.gus.socialsearcher.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gus on 25/8/17.
 */

public class TwitterSearchMetadata {

    @SerializedName("completed_in")
    private Float completedIn;

    @SerializedName("max_id")
    private long maxId;

    @SerializedName("max_id_str")
    private String maxIdStr;

    @SerializedName("next_results")
    private String nextResults;

    @SerializedName("query")
    private String query;

    @SerializedName("refresh_url")
    private String refreshUrl;

    @SerializedName("count")
    private long count;

    @SerializedName("since_id")
    private long sinceId;

    @SerializedName("since_id_str")
    private String sinceIdStr;

    public Float getCompletedIn() {
        return completedIn;
    }

    public void setCompletedIn(Float completedIn) {
        this.completedIn = completedIn;
    }

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public String getMaxIdStr() {
        return maxIdStr;
    }

    public void setMaxIdStr(String maxIdStr) {
        this.maxIdStr = maxIdStr;
    }

    public String getNextResults() {
        return nextResults;
    }

    public void setNextResults(String nextResults) {
        this.nextResults = nextResults;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getRefreshUrl() {
        return refreshUrl;
    }

    public void setRefreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getSinceId() {
        return sinceId;
    }

    public void setSinceId(long sinceId) {
        this.sinceId = sinceId;
    }

    public String getSinceIdStr() {
        return sinceIdStr;
    }

    public void setSinceIdStr(String sinceIdStr) {
        this.sinceIdStr = sinceIdStr;
    }
}
