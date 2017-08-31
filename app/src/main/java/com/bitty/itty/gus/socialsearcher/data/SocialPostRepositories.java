package com.bitty.itty.gus.socialsearcher.data;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Gus on 24/8/17.
 */

public class SocialPostRepositories {

    private SocialPostRepositories() {
        // no instance
    }

    private static SocialPostRepository repository = null;

    public synchronized static SocialPostRepository getInMemoryRepoInstance(@NonNull SocialPostServiceApi postServiceApi) {
        checkNotNull(postServiceApi);

        if (repository == null) {
            repository = new InMemorySocialPostRepository(postServiceApi);
        }
        return repository;
    }
}
