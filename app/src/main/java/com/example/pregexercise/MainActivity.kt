package com.example.pregexercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var llStartBtn = findViewById<LinearLayout>(R.id.llStart)
        llStartBtn.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent!!)
        }

    }
}