package com.popov.calculator.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CoffeeDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE machine(
                id INTEGER PRIMARY KEY,
                water INTEGER NOT NULL,
                milk INTEGER NOT NULL,
                beans INTEGER NOT NULL,
                cups INTEGER NOT NULL,
                money INTEGER NOT NULL
            )
            """.trimIndent(),
        )
        db.execSQL(
            "INSERT INTO machine(id, water, milk, beans, cups, money) VALUES(0, 400, 540, 120, 9, 550)",
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS machine")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "coffee.db"
        private const val DB_VERSION = 1
    }
}
