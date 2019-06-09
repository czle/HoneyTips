package com.example.honeytips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_update.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Update : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        var db = DBHelper(this)
        var honeyTips = HoneyTips()
        val UpdateAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, CategorySpinner)
        updateCategory.adapter = UpdateAdapter

//        var category = intent.getStringExtra(COL_CATEGORY).toString()
        var subject = intent.getStringExtra(COL_SUBJECT).toString()
        var contents = intent.getStringExtra(COL_CONTENTS).toString()

        updateSubject.setText(subject)
        updateContents.setText(contents)

        btnUpdateSave.setOnClickListener {

            var updateCategory = updateCategory.selectedItem.toString()
            var updateSubject = updateSubject.text.toString()
            var updateContents = updateContents.text.toString()

            if (updateSubject.length == 0 || updateContents.length == 0) {
                toast("빈칸을 모두 채워주세요")
            } else {
                var honeyTips =
                    HoneyTips(updateCategory, updateSubject, updateContents)
                db.updateTips(honeyTips)
                db!!.Delete(contents)
                startActivity<Menu>()
            }
        }
        btnUpdateBack.setOnClickListener {
            startActivity<ReadSelect>()
        }
    }
}
