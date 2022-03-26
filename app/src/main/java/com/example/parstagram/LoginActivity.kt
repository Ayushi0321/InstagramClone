package com.example.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //check if user is logged in, If yes, take them to MainActivity
        if(ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }
        
        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val username = findViewById<EditText>(R.id.etUserName).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            loginUser(username, password)
        }

        findViewById<Button>(R.id.btnSignUp).setOnClickListener {
            val username = findViewById<EditText>(R.id.etUserName).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            signUpUser(username, password)
        }
    }
    private fun signUpUser(username: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // User has successfully logged in
                // TODO: Navigate the user to MainActivity
                Toast.makeText(this, "New Account created", Toast.LENGTH_SHORT).show()
                goToMainActivity()
                // TODO: Show a toast to indicate user successfully signed up for an account
            } else {
                //TODO: Show Toast to tell user signup was not success
                e.printStackTrace()
                Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "Successfully Logged In")
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Login Error", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "LoginAcivity"
    }
}