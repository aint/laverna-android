package com.github.android.lvrn.lvrnproject.dagger;

import com.github.android.lvrn.lvrnproject.dagger.components.DaggerRepositoryComponent;
import com.github.android.lvrn.lvrnproject.dagger.components.RepositoryComponent;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class DaggerComponentsContainer {

    private static RepositoryComponent repositoryComponent;

    public static void initComponents() {
        initRepositoryComponent();
    }

    private static void initRepositoryComponent() {
        repositoryComponent = DaggerRepositoryComponent
                .builder()
                .build();
    }

    public static RepositoryComponent getRepositoryComponent() {
        return repositoryComponent;
    }



}
