package com.bitty.itty.gus.socialsearcher;

import com.bitty.itty.gus.socialsearcher.data.SocialPostRepositories;
import com.bitty.itty.gus.socialsearcher.data.SocialPostRepository;
import com.bitty.itty.gus.socialsearcher.data.SocialPostServiceApiImpl;

/**
 * Created by Gus on 22/8/17.
 */

public class Injection {
    public static SocialPostRepository provideSocialPostRepository() {
        return SocialPostRepositories.getInMemoryRepoInstance(new SocialPostServiceApiImpl());
    }
}
