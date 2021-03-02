package com.example.pregexercise.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class SqliteOpenHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        factory, DATABASE_VERSION
    ) {

    companion object {
        private const val DATABASE_VERSION = 1 // This DATABASE Version
        private const val DATABASE_NAME = "PregExercise.db" // Name of the DATABASE
        private const val TABLE_HISTORY = "history" // Table Name
        private const val COLUMN_ID = "_id" // Column Id
        private const val COLUMN_SET_NAME = "set_name"
        private const val COLUMN_COMPLETED_DATE = "completed_date" // Column for Completed Date
    }


    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_HISTORY_TABLE = ("CREATE TABLE " +
                TABLE_HISTORY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_SET_NAME + " TEXT," +
                COLUMN_COMPLETED_DATE + " TEXT"
                + ")") // Create History Table Query.
        db.execSQL(CREATE_HISTORY_TABLE) // Executing the create table query.

    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)
        onCreate(db)
    }


    fun addDate(setName: String, date: String) {
        val values = ContentValues()
        values.put(COLUMN_SET_NAME,setName)
        values.put(COLUMN_COMPLETED_DATE,date)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY, null, values)
        db.close() // Database is closed after insertion.
    }


    fun getAllCompletedDatesList(): ArrayList<HistoryModel> {
        val list = ArrayList<HistoryModel>() // ArrayList is initialized
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_HISTORY" + " ORDER BY "+ COLUMN_ID+" DESC",
            null
        )


        // Move the cursor to the next row.
        while (cursor.moveToNext()) {
            // Returns the zero-based index for the given column name, or -1 if the column doesn't exist.
            list.add(
                HistoryModel(
                    cursor.getColumnIndex(COLUMN_ID),
                    cursor.getString(cursor.getColumnIndex(COLUMN_SET_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
                )
            )
        }
        cursor.close() // Cursor is closed after its used.
        return list // List is returned.
    }

}