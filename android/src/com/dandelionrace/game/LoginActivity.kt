package com.dandelionrace.game

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.UserProfileChangeRequest



class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance();

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.getCurrentUser()
        if(currentUser!=null) {
            updateUI(currentUser)
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun signUp(view : View){
        val et_email = findViewById(R.id.et_email) as EditText
        val et_password = findViewById(R.id.et_password) as EditText
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        if (email.isNotEmpty() and password.isNotEmpty()) {
            if (isValidEmail(email)){
                if (password.length >= 6) {
                    mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = mAuth?.getCurrentUser()
                            addUserNameToUser(user!!)
                            updateUI(user)
                        } else {
                            Toast.makeText(
                                    this@LoginActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                            ).show()
                            println("Failes: " + task.exception)
                        }
                    }
                } else {
                    Toast.makeText(
                            this@LoginActivity, "The password must be at least 6 characters long.",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(
                        this@LoginActivity, "Email doesn't have the correct format.",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            Toast.makeText(
                    this@LoginActivity, "Please enter a mail address and a password.",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun login(view: View) {
        val et_email = findViewById(R.id.et_email) as EditText
        val et_password = findViewById(R.id.et_password) as EditText
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        if (email.isNotEmpty() and password.isNotEmpty()) {
            if (isValidEmail(email)){
                if (password.length >= 6) {
                    mAuth?.signInWithEmailAndPassword(email, password)
                            ?.addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val user = mAuth?.getCurrentUser()
                                    updateUI(user)
                                } else {
                                    Toast.makeText(
                                            this@LoginActivity, "Authentication failed.",
                                            Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                } else {
                    Toast.makeText(
                            this@LoginActivity, "The password must be at least 6 characters long.",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(
                        this@LoginActivity, "Email doesn't have the correct format.",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            Toast.makeText(
                    this@LoginActivity, "Please enter a mail address and a password.",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


    private fun addUserNameToUser(user: FirebaseUser) {
        val et_username = findViewById(R.id.et_username) as EditText
        val username = et_username.text.toString()

        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(username).build()

        user.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("Success")
                    }
                }
    }
}

