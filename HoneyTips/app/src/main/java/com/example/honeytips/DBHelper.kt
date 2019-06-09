package com.example.honeytips

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


val DATABASE_NAME = "HoneyTipstest1.db"
val TABLE_NAME = "HoneyTips"
val COL_CATEGORY = "Category"
val COL_SUBJECT = "Subject"
val COL_CONTENTS = "Contents"

@Suppress("UNREACHABLE_CODE")
class DBHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {


        val createTable =
            "create table " + TABLE_NAME + " (" + COL_CATEGORY +
                    " varchar2(30)," + COL_SUBJECT + " VARCHAR2(30)," + COL_CONTENTS + " VARCHAR2(500));"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val deleteTable =
            "Drop table if exists " + TABLE_NAME + ";"
        db?.execSQL(deleteTable)
        onCreate(db)
    }

    fun writeTips(honeyTips: HoneyTips) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_CATEGORY, honeyTips.Category)
        cv.put(COL_SUBJECT, honeyTips.Subject)
        cv.put(COL_CONTENTS, honeyTips.Contents)

        var result = db.insert(TABLE_NAME, null, cv) //rowID
        if (result == -1.toLong()) {
            Toast.makeText(context, "등록실패", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "등록성공", Toast.LENGTH_SHORT).show()

        }

    }

    fun updateTips(honeyTips: HoneyTips) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_CATEGORY, honeyTips.Category)
        cv.put(COL_SUBJECT, honeyTips.Subject)
        cv.put(COL_CONTENTS, honeyTips.Contents)

        var result = db.insert(TABLE_NAME, null, cv) //rowID
        if (result == -1.toLong()) {
            Toast.makeText(context, "등록실패", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "업데이트 성공", Toast.LENGTH_SHORT).show()
        }
    }

    fun DBHelper() {

    }

    fun Delete(string: String) {
        val db = this.writableDatabase
        var delete = "Delete From " + TABLE_NAME + " where (" +
                COL_CONTENTS + " = '" + string + "');"
        db!!.execSQL(delete)
        db!!.close()
    }
}
