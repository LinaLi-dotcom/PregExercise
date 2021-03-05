package com.example.pregexercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.pregexercise.databinding.ActivityMainBinding
import com.example.pregexercise.ui.WorkoutSetActivity

@Suppress("DEPRECATION")
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFullScreen()

        Handler().postDelayed(
            {
                startActivity(Intent(this@MainActivity, WorkoutSetActivity::class.java))
                finish()
            },
            2500
        )
    }
}