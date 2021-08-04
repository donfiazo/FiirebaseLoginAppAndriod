package com.eddie.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        tv_login.setOnClickListener {

            onBackPressed()

        }

        register_button.setOnClickListener {
            when {
                //check if email and password not empty
                TextUtils.isEmpty(register_email.text.toString().trim() {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity, "Please enter email.", Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(register_password.text.toString().trim() {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity, "Please enter password.",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = register_email.text.toString().trim(){it <= ' '}
                    val password : String = register_password.text.toString().trim(){it <= ' '}

                    //Create an instance and create a registered user with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( OnCompleteListener<AuthResult> {task ->
                            //if the registration was successful
                            if(task.isSuccessful) {
                                //firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@RegisterActivity, "User Registered successfully",
                                    Toast.LENGTH_SHORT).show()

                                //new user signed in and sent to main screen

                                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                //if user wasn't created successfully
                                Toast.makeText(this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                        })
                }

        }

        }

    }
}