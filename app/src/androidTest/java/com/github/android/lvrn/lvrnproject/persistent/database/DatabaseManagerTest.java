package com.github.android.lvrn.lvrnproject.persistent.database;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DatabaseManagerTest {

    private DatabaseManager mDataBaseManager;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(InstrumentationRegistry.getTargetContext());
        mDataBaseManager = DatabaseManager.getInstance();
    }

    @Test
    public void databaseManagerShouldBeInitialized() {
        assertNotNull(mDataBaseManager);
    }

    @Test
    public void databaseConnectionShouldBeOpenedAndThenClosed() {
        SQLiteDatabase database = mDataBaseManager.openConnection();
        assertTrue(database.isOpen());
        if (database.isOpen()) {
            database.close();
        }
        assertFalse(database.isOpen());
    }

    @Test
    public void amountOfDatabaseConnectionShouldBePredicted() {
        for (int i = 0; i < 5; i++) {
            mDataBaseManager.openConnection();
        }
        assertEquals(5, mDataBaseManager.getConnectionsCount());
        assertNotEquals(6, mDataBaseManager.getConnectionsCount());
    }

    @Test
    public void connectionShouldOpensAfterFewCloses() {
        for (int i = 0; i < 5; i++) {
            mDataBaseManager.closeConnection();
        }
        SQLiteDatabase database = mDataBaseManager.openConnection();
        assertTrue("Opened connections" + mDataBaseManager.getConnectionsCount() ,database.isOpen());
    }

    @After
    public void finish() {
        mDataBaseManager.closeAllConnections();
        mDataBaseManager = null;
    }
}
