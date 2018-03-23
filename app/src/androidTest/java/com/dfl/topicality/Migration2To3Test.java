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
 * Created by loureiro on 08-03-2018.
 */

@RunWith(AndroidJUnit4.class)
public class Migration2To3Test {
    private static final String TEST_DB = "migration-test";

    @Rule
    public final MigrationTestHelper migrationTestHelper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
            AppDatabase.class.getCanonicalName(),
            new FrameworkSQLiteOpenHelperFactory());

    @Test
    public void migrate2To3() throws IOException {
        //given
        SupportSQLiteDatabase supportSQLiteDatabase = migrationTestHelper.createDatabase(TEST_DB, 2);
        supportSQLiteDatabase.execSQL("INSERT INTO databasearticle (url, is_favourite, is_viewed) VALUES ('testurl', 0, 0)");
        supportSQLiteDatabase.close();

        //when
        supportSQLiteDatabase = migrationTestHelper.runMigrationsAndValidate(TEST_DB, 3, true, MigrationHelper.MIGRATION_2_3);

        //then
        Cursor cursor = supportSQLiteDatabase.query("SELECT * FROM databasearticle WHERE url = 'testurl'", null);
        if (cursor != null && cursor.moveToFirst()) {
            assertTrue(cursor.getInt(cursor.getColumnIndex("is_clicked")) == 0);
        } else {
            fail();
        }
        supportSQLiteDatabase.close();
    }
}