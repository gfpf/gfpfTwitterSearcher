package com.bitty.itty.gus.socialsearcher;


import com.bitty.itty.gus.socialsearcher.data.FakeSocialPostServiceApiImpl;
import com.bitty.itty.gus.socialsearcher.data.SocialPostRepositories;
import com.bitty.itty.gus.socialsearcher.data.SocialPostRepository;

/**
 * Created by Gus on 24/8/17.
 */

/**
 * Enables injection of mock implementations for
 * {@link SocialPostRepository} at compile time. This is useful for testing, since it allows us to
 * use a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static SocialPostRepository provideSocialPostRepository() {
        return SocialPostRepositories.getInMemoryRepoInstance(new FakeSocialPostServiceApiImpl());
    }
}
