package ru.voodster.otuslesson.api

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
val TAG = "migration"
val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        Log.d(TAG, "migrate(),  from 1 to 2")
        val cur = database.query("SELECT * FROM films_table")

        database.execSQL("CREATE TABLE IF NOT EXISTS `films_table1` (`rowID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,`id` NOT NULL, `img` INTEGER NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL,`fav` BOOL NOT NULL, `likes` INTEGER NOT NULL)")

        while (!cur.isClosed && cur.moveToNext()) {

            val id = cur.getInt(cur.getColumnIndex("id"))
            val name = cur.getString(cur.getColumnIndex("name"))
            val revenue = cur.getInt(cur.getColumnIndex("annual_revenue"))

            val values = ContentValues().apply {
                put("id", id)
                put("name", name)
                put("annual_revenue", revenue)
                put("description", "No  desc")
            }

            val newRowId = database.insert("Publisher1", SQLiteDatabase.CONFLICT_REPLACE, values)
            Log.d(TAG, "migrate(), values:[$values]")
        }
        cur.close()
        database.execSQL("DROP TABLE Publisher");
        database.execSQL("DROP INDEX IF EXISTS index_Publisher_name")
        database.execSQL("DROP INDEX IF EXISTS index_Publisher_annual_revenue")
        database.execSQL("ALTER TABLE Publisher1 RENAME TO Publisher");
        database.execSQL("CREATE INDEX IF NOT EXISTS `index_Publisher_annual_revenue` ON `Publisher` (`annual_revenue`)")
        database.execSQL("CREATE INDEX IF NOT EXISTS `index_Publisher_name` ON `Publisher` (`name`)")

        Log.d(TAG, "migrate(),  from 1 to 2, version ${database.version}")

    }
}
