package com.dfl.topicality.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

/**
 * Created by loureiro on 08-03-2018.
 */

public class MigrationHelper {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE databasearticle ADD COLUMN is_favourite INTEGER NOT NULL DEFAULT 1");
            database.execSQL("ALTER TABLE databasearticle ADD COLUMN is_viewed INTEGER NOT NULL DEFAULT 1");
        }
    };
}
