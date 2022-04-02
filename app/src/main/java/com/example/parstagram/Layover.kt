package com.example.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.util.*

class Layover : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layover)
        val handler = Handler()
        handler.postDelayed({ goToMainActivity() }, 500)
    }

    private fun goToMainActivity() {
        val intent = Intent(this@Layover, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}