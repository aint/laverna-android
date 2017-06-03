package com.github.android.lvrn.lvrnproject.persistent.database;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class DatabaseManagerTest {

    @Nullable
    private DatabaseManager mDataBaseManager;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(InstrumentationRegistry.getTargetContext());
        mDataBaseManager = DatabaseManager.getInstance();
    }

    @Test
    public void databaseManagerShouldBeInitialized() {
        assertThat(mDataBaseManager).isNotNull();
    }

    @Test
    public void databaseConnectionShouldBeOpenedAndThenClosed() {
        SQLiteDatabase database = mDataBaseManager.openConnection();
        assertThat(database.isOpen()).isTrue();

        database.close();
        assertThat(database.isOpen()).isFalse();
    }

    @Test
    public void amountOfDatabaseConnectionShouldBePredicted() {
        for (int i = 0; i < 5; i++) {
            mDataBaseManager.openConnection();
        }
        assertThat(mDataBaseManager.getConnectionsCount()).isEqualTo(5);
    }

    @Test
    public void connectionShouldOpensAfterFewCloses() {
        for (int i = 0; i < 5; i++) {
            mDataBaseManager.closeConnection();
        }
        SQLiteDatabase database = mDataBaseManager.openConnection();
        assertThat(database.isOpen()).isTrue();
    }

    @After
    public void finish() {
        mDataBaseManager.closeAllConnections();
        mDataBaseManager = null;
        DatabaseManager.removeInstance();
    }
}
