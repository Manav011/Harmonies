package com.example.musicapp.sqllitedb

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "favorite_songs.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "favorites"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DURATION = "duration"
        const val COLUMN_ALBUM_COVER = "album_cover"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_DURATION INTEGER,
                $COLUMN_ALBUM_COVER TEXT
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addFavoriteSong(title: String, duration: Int, albumCover: String): Boolean {
        val db = writableDatabase

        // Query to check if the song with the same title already exists in the favorites
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TITLE = ?"
        val cursor = db.rawQuery(query, arrayOf(title))

        // If song already exists, do not add it
        if (cursor.moveToFirst()) {
            cursor.close()
            db.close()
            return false
        } else {
            // Song doesn't exist, so insert it
            val insertQuery = """
            INSERT INTO $TABLE_NAME ($COLUMN_TITLE, $COLUMN_DURATION, $COLUMN_ALBUM_COVER)
            VALUES (?, ?, ?)
        """
            val statement = db.compileStatement(insertQuery)
            statement.bindString(1, title)
            statement.bindLong(2, duration.toLong())
            statement.bindString(3, albumCover)

            statement.executeInsert()
        }

        cursor.close()
        db.close()
        return true
    }


    @SuppressLint("Range")
    fun getAllFavorites(): List<Song> {
        val songList = mutableListOf<Song>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val duration = cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION))
                val albumCover = cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM_COVER))

                val song = Song(id, title, duration, albumCover)
                songList.add(song)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return songList
    }

    fun removeSong(songTitle: String) {
        val db = writableDatabase
        val query = "DELETE FROM $TABLE_NAME WHERE $COLUMN_TITLE = ?"
        val statement = db.compileStatement(query)
        statement.bindString(1, songTitle) // Bind the title parameter
        statement.executeUpdateDelete()
        db.close()
    }

}
