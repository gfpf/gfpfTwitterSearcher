package com.bitty.itty.gus.socialsearcher.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gus on 25/8/17.
 */

public class TwitterBearerToken {

    public final static String BEARER_TOKEN_TYPE = "bearer";

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("access_token")
    private String accessToken;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
