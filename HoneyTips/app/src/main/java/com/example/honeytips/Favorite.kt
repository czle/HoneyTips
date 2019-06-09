package com.example.honeytips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_favorite.*
import org.jetbrains.anko.startActivity

lateinit var favoriteHoneyTipListCategory: ArrayList<String>
lateinit var favoriteHoneyTipListSubject: ArrayList<String>
lateinit var favoriteHoneyTipListContents: ArrayList<String>

class Favorite : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        btnFavoriteBack.setOnClickListener {
            startActivity<Menu>()
        }


        var db = DBHelperFavorite(this)
        favoriteHoneyTipListCategory = ArrayList() // 카테고리 초기화.
        favoriteHoneyTipListSubject = ArrayList()
        favoriteHoneyTipListContents = ArrayList()
        val DB = db.readableDatabase

        var cursor = DB.rawQuery("select * From " + FAVORITE_TABLE_NAME, null)

        if (cursor.moveToNext()) {
            do {
                favoriteHoneyTipListCategory.add(cursor.getString(0))
                favoriteHoneyTipListSubject.add(cursor.getString(1))
                favoriteHoneyTipListContents.add(cursor.getString(2))
            } while (cursor.moveToNext())
            cursor.close()
            DB.close()
        }

        var favoriteAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, favoriteHoneyTipListSubject)
        FavoriteListView.choiceMode = ListView.CHOICE_MODE_SINGLE
        FavoriteListView.adapter = favoriteAdapter

        FavoriteListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            btnFavoriteRead.setOnClickListener {

                startActivity<Favorite_Read>(

                    FAVORITE_COL_CATEGORY to favoriteHoneyTipListCategory[position],
                    FAVORITE_COL_SUBJECT to favoriteHoneyTipListSubject[position],
                    FAVORITE_COL_CONTENTS to favoriteHoneyTipListContents[position]
                )
            }

            btnFavoriteDelete.setOnClickListener {
                var cnt: Int
                var checked: Int
                cnt = favoriteAdapter.count
                if (cnt > 0) {
                    checked = FavoriteListView.checkedItemPosition

                    if (checked > -1 && checked < cnt) {
                        var deletedContents = favoriteHoneyTipListContents.get(checked)
                        db!!.Favorite_Delete(deletedContents)
                        favoriteHoneyTipListContents.removeAt(checked)
                        favoriteHoneyTipListSubject.removeAt(checked)
                        FavoriteListView.clearChoices()
                        favoriteAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}
