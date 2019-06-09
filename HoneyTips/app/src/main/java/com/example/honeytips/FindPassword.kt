package com.example.honeytips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_find_password.*
import org.jetbrains.anko.toast

class FindPassword : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)




        btnFindPassword.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var ID = find_ID_EditTxt.text.toString()

            if (ID.isNotEmpty()) {
                auth.sendPasswordResetEmail(ID)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            toast("이메일을 전송하였습니다.")
                        } else {
                            toast("잠시 후 다시 시도해주세요")
                        }
                    }
            } else {
                toast("이메일을 적어주세요")
            }


        }


    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }
}
