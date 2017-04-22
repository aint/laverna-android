package com.github.android.lvrn.lvrnproject.persistent.database;

import android.database.sqlite.SQLiteDatabase;

import com.github.android.lvrn.lvrnproject.BuildConfig;

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
public class DatabaseManagerTest {

    private DatabaseManager mDataBaseManager;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);
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
