package com.example.honeytips

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val FAVORITE_DATABASE_NAME = "Favorite.db"
val FAVORITE_TABLE_NAME = "Favorite"
val FAVORITE_COL_CATEGORY = "Favorite_Category"
val FAVORITE_COL_SUBJECT = "Favorite_Subject"
val FAVORITE_COL_CONTENTS = "Favorite_Contents"

class DBHelperFavorite(var context: Context) : SQLiteOpenHelper(context, FAVORITE_DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable =
            "create table " + FAVORITE_TABLE_NAME + " (" + FAVORITE_COL_CATEGORY +
                    " varchar2(30)," + FAVORITE_COL_SUBJECT + " VARCHAR2(30)," + FAVORITE_COL_CONTENTS + " VARCHAR2(500));"
        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun Favorite_WriteTips(honeyTips: HoneyTips) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(FAVORITE_COL_CATEGORY, honeyTips.Category)
        cv.put(FAVORITE_COL_SUBJECT, honeyTips.Subject)
        cv.put(FAVORITE_COL_CONTENTS, honeyTips.Contents)

        var result = db.insert(FAVORITE_TABLE_NAME, null, cv) //rowID
        if (result == -1.toLong()) {
            Toast.makeText(context, "즐겨찾기 등록 실패", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "즐겨찾기 등록 완료", Toast.LENGTH_SHORT).show()

        }
    }
    fun Favorite_Delete(string: String) {
        val db = this.writableDatabase
        var delete = "Delete From " + FAVORITE_TABLE_NAME + " where (" +
                FAVORITE_COL_CONTENTS + " = '" + string + "');"
        db!!.execSQL(delete)
        db!!.close()
    }
}
