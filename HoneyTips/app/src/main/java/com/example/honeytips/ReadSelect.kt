package com.example.honeytips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_read_select.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

lateinit var honeyTipListCategory: ArrayList<String> // 원래 카테고리 늦은 선언.
lateinit var honeyTipListSubject: ArrayList<String>
lateinit var honeyTipListContents: ArrayList<String>

class ReadSelect : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_select)

        var db = DBHelper(this)
        honeyTipListCategory = ArrayList() // 카테고리 초기화.
        honeyTipListSubject = ArrayList()
        honeyTipListContents = ArrayList()
        val DB = db.readableDatabase
        var cur = DB.rawQuery("select * From " + TABLE_NAME, null)
        honeyTipListSubject.clear()

        if (cur.moveToNext()) {
            do {
                honeyTipListCategory.add(cur.getString(0)) //
                honeyTipListSubject.add(cur.getString(1))
                honeyTipListContents.add(cur.getString(2))
            } while (cur.moveToNext())
            cur.close()
            DB.close()
        }

        var madapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, honeyTipListSubject)
        tipsListView.choiceMode = ListView.CHOICE_MODE_SINGLE
        tipsListView.adapter = madapter

        var categoryAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, CategorySpinner)
        readSpinner.adapter = categoryAdapter
        readSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var itemSelect = readSpinner.selectedItem.toString()

                var db = DBHelper(applicationContext)

                if (itemSelect == "선택해주세요") {
                    honeyTipListCategory = ArrayList()
                    honeyTipListSubject = ArrayList()
                    honeyTipListContents = ArrayList()
                    val DB = db.readableDatabase
                    var cur = DB.rawQuery(
                        "select * From " + TABLE_NAME + ";",
                        null
                    )
                    honeyTipListSubject.clear()

                    if (cur.moveToNext()) {
                        do {
                            honeyTipListCategory.add(cur.getString(0))
                            honeyTipListSubject.add(cur.getString(1))
                            honeyTipListContents.add(cur.getString(2))
                        } while (cur.moveToNext())
                        cur.close()
                        DB.close()
                    }

                    var selectSpinnerAdapter = ArrayAdapter(
                        applicationContext,
                        android.R.layout.simple_list_item_single_choice,
                        honeyTipListSubject
                    )
                    tipsListView.choiceMode = ListView.CHOICE_MODE_SINGLE
                    tipsListView.adapter = selectSpinnerAdapter
                    db.close()

                } else {
                    honeyTipListCategory = ArrayList()
                    honeyTipListSubject = ArrayList()
                    honeyTipListContents = ArrayList()
                    val DB = db.readableDatabase
                    var cur = DB.rawQuery(
                        "select * From " + TABLE_NAME + " where " + COL_CATEGORY + " = '" + itemSelect + "';",
                        null
                    )
                    honeyTipListSubject.clear()

                    if (cur.moveToNext()) {
                        do {
                            honeyTipListCategory.add(cur.getString(0))
                            honeyTipListSubject.add(cur.getString(1))
                            honeyTipListContents.add(cur.getString(2))
                        } while (cur.moveToNext())
                        cur.close()
                        DB.close()
                    }

                    var selectSpinnerAdapter = ArrayAdapter(
                        applicationContext,
                        android.R.layout.simple_list_item_single_choice,
                        honeyTipListSubject
                    )
                    tipsListView.choiceMode = ListView.CHOICE_MODE_SINGLE
                    tipsListView.adapter = selectSpinnerAdapter
                    db.close()
                }
            }
        }

        tipsListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var Click = tipsListView.onItemClickListener
            btnNext.setOnClickListener {
                startActivity<Read>(

                    COL_CATEGORY to honeyTipListCategory[position],
                    COL_SUBJECT to honeyTipListSubject[position],
                    COL_CONTENTS to honeyTipListContents[position]
                )
                Log.d("mew", honeyTipListCategory.get(position))

            }
            btnReadSelectDelete.setOnClickListener {
                var cnt: Int
                var checked: Int
                cnt = madapter.count
                if (cnt > 0) {
                    checked = tipsListView.checkedItemPosition

                    if (checked > -1 && checked < cnt) {
                        var deletedContents = honeyTipListContents.get(checked)
                        Log.d("mew", deletedContents)
                        db!!.Delete(deletedContents)
                        honeyTipListContents.removeAt(checked)
                        honeyTipListSubject.removeAt(checked)
                        tipsListView.clearChoices()
                        madapter.notifyDataSetChanged()
                        btnFind.performClick()
                    }
                }
            }
            btnReadUpdate.setOnClickListener {
                startActivity<Update>(

                    COL_SUBJECT to honeyTipListSubject[position],
                    COL_CONTENTS to honeyTipListContents[position]

                )
            }
        }

        btnFind.setOnClickListener {

            val DB = DBHelper(this)

            var searchSubject = readSelectSubject.text.toString()

            if (readSelectSubject.text.length == 0) {

                var db = DBHelper(this)
                honeyTipListCategory = ArrayList()
                honeyTipListSubject = ArrayList()
                honeyTipListContents = ArrayList()
                val DB = db.readableDatabase
                var cur = DB.rawQuery("select * From " + TABLE_NAME, null)
                honeyTipListSubject.clear()

                if (cur.moveToNext()) {
                    do {
                        honeyTipListCategory.add(cur.getString(0))
                        honeyTipListSubject.add(cur.getString(1))
                        honeyTipListContents.add(cur.getString(2))
                    } while (cur.moveToNext())
                    cur.close()
                    DB.close()
                }

                var madapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, honeyTipListSubject)
                tipsListView.choiceMode = ListView.CHOICE_MODE_SINGLE
                tipsListView.adapter = madapter

                Toast.makeText(this, "리스트를 초기화 합니다.", Toast.LENGTH_SHORT).show()

            } else if (readSelectSubject.text.length != 0) { // subject검색

                var db = DBHelper(this)
                honeyTipListCategory = ArrayList()
                honeyTipListSubject = ArrayList()
                honeyTipListContents = ArrayList()
                val DB = db.readableDatabase
                var cur = DB.rawQuery(
                    "select * From " + TABLE_NAME + " where " + COL_SUBJECT + " like '%" + searchSubject + "%';",
                    null
                )
                honeyTipListSubject.clear()

                if (cur.moveToNext()) {
                    do {
                        honeyTipListCategory.add(cur.getString(0))
                        honeyTipListSubject.add(cur.getString(1))
                        honeyTipListContents.add(cur.getString(2))
                    } while (cur.moveToNext())
                    cur.close()
                    DB.close()
                }

                var madapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, honeyTipListSubject)
                tipsListView.choiceMode = ListView.CHOICE_MODE_SINGLE
                tipsListView.adapter = madapter
                db.close()

                toast("검색완료")

            }
        }
        btnReadBack.setOnClickListener {
            startActivity<Menu>()
        }

    }

}