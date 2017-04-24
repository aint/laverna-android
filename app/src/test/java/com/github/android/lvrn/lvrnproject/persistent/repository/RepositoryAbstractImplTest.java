package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.persistent.repository.abstractimp.BasicRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RepositoryAbstractImplTest {
    private BasicRepository repository;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        repository = new BasicRepository("test_table") {

            @Override
            protected ContentValues toContentValues(Entity entity) {
                return null;
            }

            @Override
            protected Entity toEntity(Cursor cursor) {
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
        repository.add(new ProfileDependedEntity() {

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
