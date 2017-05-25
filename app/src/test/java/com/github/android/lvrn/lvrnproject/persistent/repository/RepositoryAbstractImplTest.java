package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.BasicRepositoryImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RepositoryAbstractImplTest {
    @Nullable
    private BasicRepositoryImpl repository;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        repository = new BasicRepositoryImpl("test_table") {

            @Nullable
            @Override
            protected ContentValues toContentValues(Entity entity) {
                return null;
            }

            @Nullable
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
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {

            }

            @NonNull
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
