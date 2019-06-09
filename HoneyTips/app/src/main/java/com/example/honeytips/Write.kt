package com.example.honeytips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_write.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

val CategorySpinner: ArrayList<String> = arrayListOf("선택해주세요", "요리", "생활", "취미", "교통", "기타")

class Write : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        val context = this
        var db = DBHelper(this)


        val WriteAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, CategorySpinner)
        SpinnerCategory.adapter = WriteAdapter
        var selectCategory: String

        btnInitional.setOnClickListener {
            editSubject.setText("")
            editWrite.setText("")

            toast("초기화 되었습니다.")

        }
        btnSave.setOnClickListener {
            selectCategory = SpinnerCategory.selectedItem.toString()
            if (editSubject.text.length == 0 || editWrite.text.length == 0) {
                toast("빈칸을 모두 채워주세요")
            } else {
                var honeyTips =
                    HoneyTips(selectCategory, editSubject.text.toString(), editWrite.text.toString())
                Log.d("mew", selectCategory)
                Log.d("mew", "bow")
                db.writeTips(honeyTips)
                startActivity<Menu>()
            }

        }
        btnBack.setOnClickListener {
            startActivity<Menu>()
        }
    }
}
