package com.example.honeytips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_favorite__read.*
import org.jetbrains.anko.startActivity

class Favorite_Read : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite__read)

        var favoriteCategory = intent.getStringExtra(FAVORITE_COL_CATEGORY).toString()
        var favoriteSubject = intent.getStringExtra(FAVORITE_COL_SUBJECT).toString()
        var favoriteContents = intent.getStringExtra(FAVORITE_COL_CONTENTS).toString()

        readFavoriteCategory.setText(favoriteCategory)
        readFavoriteSubject.setText(favoriteSubject)
        txtFavoriteTxt.setText(favoriteContents)

        btnFavoriteReadBack.setOnClickListener {
            startActivity<Favorite>()
        }
    }

}
