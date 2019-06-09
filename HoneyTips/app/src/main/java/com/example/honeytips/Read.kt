package com.example.honeytips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_read.*
import org.jetbrains.anko.startActivity

lateinit var favoriteList: ArrayList<String>

class Read : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        var db = DBHelperFavorite(this)

        var category = intent.getStringExtra(COL_CATEGORY).toString()
        var subject = intent.getStringExtra(COL_SUBJECT).toString()
        var contents = intent.getStringExtra(COL_CONTENTS).toString()

        readSpinner.setText(category)
        readSubject.setText(subject)
        txtRead.setText(contents)

        var favoriteCategory = readSpinner.text.toString()
        var favoriteSubject = readSubject.text.toString()
        var favoriteContents = txtRead.text.toString()

        btnNullStar.setOnClickListener {

            btnNullStar.visibility = View.INVISIBLE
            btnFavorite.visibility = View.VISIBLE
            btnFavorite.playAnimation()

            var honeyTips =
                HoneyTips(favoriteCategory, favoriteSubject, favoriteContents)
            db.Favorite_WriteTips(honeyTips)

        }
        btnFavorite.setOnClickListener {
            btnNullStar.visibility = View.VISIBLE
            btnFavorite.visibility = View.INVISIBLE

        }
        btnReadBack.setOnClickListener {
            startActivity<ReadSelect>()
        }


    }
}
