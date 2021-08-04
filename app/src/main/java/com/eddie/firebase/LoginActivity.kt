package com.eddie.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        login_button.setOnClickListener {
            when {
                //check if email and password not empty
                TextUtils.isEmpty(login_email.text.toString().trim() {it <= ' '}) -> {
                    Toast.makeText(
                        this, "Please enter email.", Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(login_password.text.toString().trim() {it <= ' '}) -> {
                    Toast.makeText(
                        this, "Please enter password.",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = login_email.text.toString().trim(){it <= ' '}
                    val password : String = login_password.text.toString().trim(){it <= ' '}

                    //Log-in using FirebaseAuth
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( OnCompleteListener<AuthResult> { task ->
                            //if the registration was successful
                            if(task.isSuccessful) {
                                //firebase registered user

                                Toast.makeText(
                                    this, "You are logged in successfully",
                                    Toast.LENGTH_SHORT).show()

                                //new user signed in and sent to main screen

                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                //if the login is not successful then show error message
                                Toast.makeText(this,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                        })
                }

            }

        }

    }
}