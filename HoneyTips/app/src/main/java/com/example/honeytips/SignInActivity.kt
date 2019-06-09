package com.example.honeytips

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        btnJoin.setOnClickListener {
            var ID = signIn_ID_EditTxt.text.toString()
            var PW = signIn_PW_EditTxt.text.toString()
            var PWCheck = signIn_PWCheck_EditTxt.text.toString()

            if (ID.isNotEmpty() && PW.isNotEmpty() && PWCheck.isNotEmpty()) {
                if (PW.equals(PWCheck)) {
                    auth.createUserWithEmailAndPassword(ID, PW)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                toast("회원가입이 완료되었습니다.")
                                Handler().postDelayed({
                                    startActivity<Menu>()
                                }, 1000)
                            } else {
                                toast("잠시 후 다시 시도해주세요")
                            }
                        }
                } else {
                    toast("비밀번호가 일치하지 않습니다.")
                }

            } else {
                toast("아이디 혹은 비밀번호를 입력해주세요")
            }

        }
        btnBack.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

}
