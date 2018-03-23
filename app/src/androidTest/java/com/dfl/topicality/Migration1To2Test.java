package com.dfl.topicality;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dfl.topicality.database.AppDatabase;
import com.dfl.topicality.database.MigrationHelper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by loureiro on 07-03-2018.
 */

@RunWith(AndroidJUnit4.class)
public class Migration1To2Test {
    private static final String TEST_DB = "migration-test";

    @Rule
    public final MigrationTestHelper migrationTestHelper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
            AppDatabase.class.getCanonicalName(),
            new FrameworkSQLiteOpenHelperFactory());

    @Test
    public void migrate1To2() throws IOException {
        //given
        SupportSQLiteDatabase supportSQLiteDatabase = migrationTestHelper.createDatabase(TEST_DB, 1);
        supportSQLiteDatabase.execSQL("INSERT INTO databasearticle (url) VALUES ('testurl')");
        supportSQLiteDatabase.close();

        //when
        supportSQLiteDatabase = migrationTestHelper.runMigrationsAndValidate(TEST_DB, 2, true, MigrationHelper.MIGRATION_1_2);

        //then
        Cursor cursor = supportSQLiteDatabase.query("SELECT * FROM databasearticle WHERE url = 'testurl'", null);
        if (cursor != null && cursor.moveToFirst()) {
            assertTrue(cursor.getInt(cursor.getColumnIndex("is_favourite")) == 1);
            assertTrue(cursor.getInt(cursor.getColumnIndex("is_viewed")) == 1);
        } else {
            fail();
        }
        supportSQLiteDatabase.close();
    }
}