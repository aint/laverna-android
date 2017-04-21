package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.content.ContentValues;
import android.database.Cursor;;
import static org.assertj.core.api.Assertions.assertThat;


import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.BasicEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RepositoryAbstractImplTest {
    private RepositoryAbstractImpl repository;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        repository = new RepositoryAbstractImpl("test_table") {

            @Override
            protected ContentValues toContentValues(BasicEntity entity) {
                return null;
            }

            @Override
            protected BasicEntity toEntity(Cursor cursor) {
                return null;
            }
        };
    }

    @Test
    public void connectionShouldBeOpenedAndClosed() {
        assertThat(repository.openDatabaseConnection()).isTrue();
        assertThat(repository.closeDatabaseConnection()).isTrue();
    }

    @Test
    public void connectionShouldNotBeOpenedAndClosed() {
        repository.openDatabaseConnection();
        assertThat(repository.openDatabaseConnection()).isFalse();
        repository.closeDatabaseConnection();
        assertThat(repository.closeDatabaseConnection()).isFalse();
    }

    @Test(expected = NullPointerException.class)
    public void repositoryShoudThrowNullPointerException() {
        repository.add(new BasicEntity() {

            @Override
            public String getId() {
                return super.getId();
            }
        });
    }

    @After
    public void finish() {
        DatabaseManager.removeInstance();
        repository = null;
    }
}
