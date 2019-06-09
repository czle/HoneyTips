package com.example.honeytips

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.email
import org.jetbrains.anko.startActivity

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnWriteNull.setOnClickListener {
            btnWriteNull.visibility = View.INVISIBLE
            btnWrite.visibility = View.VISIBLE
            btnWrite.playAnimation()
            Handler().postDelayed({
                startActivity<Write>()
            }, 2000)
        }
        btnWrite.setOnClickListener {
        }

        btnReadnull.setOnClickListener {
            btnReadnull.visibility = View.INVISIBLE
            btnRead.visibility = View.VISIBLE
            btnRead.playAnimation()
            Handler().postDelayed({
                startActivity<ReadSelect>()
            }, 2500)
        }

        btnRead.setOnClickListener {

        }
        btnFavoriteNull.setOnClickListener {
            btnFavoriteNull.visibility = View.INVISIBLE
            btnFavorite.visibility = View.VISIBLE
            btnFavorite.playAnimation()
            Handler().postDelayed({
                startActivity<Favorite>()
            }, 1500)
        }

        btnFavorite.setOnClickListener {

        }
        btnFAQNull.setOnClickListener {
            btnFAQNull.visibility = View.INVISIBLE
            btnFAQ.visibility = View.VISIBLE
            btnFAQ.playAnimation()
            Handler().postDelayed({
                email("waters22@naver.com", "어플 관련 문의드립니다.")
            }, 1500)

        }
        btnFAQ.setOnClickListener {

        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<MainActivity>()
    }

    override fun onResume() {
        super.onResume()
        btnWrite.visibility = View.INVISIBLE
        btnWriteNull.visibility = View.VISIBLE
        btnWrite.cancelAnimation()

        btnRead.visibility = View.INVISIBLE
        btnReadnull.visibility = View.VISIBLE
        btnRead.cancelAnimation()

        btnFavorite.visibility = View.INVISIBLE
        btnFavoriteNull.visibility = View.VISIBLE
        btnFavorite.cancelAnimation()

        btnFAQ.visibility = View.INVISIBLE
        btnFAQNull.visibility = View.VISIBLE
        btnFAQ.cancelAnimation()
    }
}
