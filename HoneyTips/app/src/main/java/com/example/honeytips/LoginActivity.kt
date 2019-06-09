package com.example.honeytips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            var ID = ID_EditTxt.text.toString()
            var PW = PW_EditTxt.text.toString()

            if (ID.isNotEmpty() && PW.isNotEmpty()) {
                auth.signInWithEmailAndPassword(ID, PW)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            startActivity<Menu>()
                            finish()
                        } else {
                            toast("아이디 혹은 비밀번호가 잘못되었습니다.")
                        }

                        // ...
                    }

            } else {
                toast("아이디 혹은 비밀번호를 입력해주세요")
            }


        }
        btnSignIn.setOnClickListener {
            finish()
            startActivity<SignInActivity>()
        }
        btnFindPW.setOnClickListener {
            finish()
            startActivity<FindPassword>()
        }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }
}
