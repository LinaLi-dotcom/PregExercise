package com.example.pregexercise

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
            Toast.makeText(this,"Here we will start the exercise", Toast.LENGTH_SHORT).show()
        }

    }
}